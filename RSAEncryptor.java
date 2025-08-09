import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.*;
import java.util.*;

public class RSAEncryptor {
    // Encrypt and sign the message
    public static Message encrypt(Person sender, Person receiver, String message, SocialGraph graph) throws Exception {
        // Sign the message
        String signature = signMessage(message, sender.getPrivateKey());

        // Encrypt the message using receiver's public key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, receiver.getPublicKey());
        byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(encrypted);

        Message msg = new Message(sender.getId(), receiver.getId(), encoded);
        msg.setMetadata("encryption", "rsa");
        msg.setMetadata("signature", signature);

        List<Person> path = GraphRouter.findPath(sender, receiver, graph);
        if (path != null && path.size() > 1) {
            Person finalReceiver = path.get(path.size() - 1);
            finalReceiver.getInbox().add(msg);
        } else {
            receiver.getInbox().add(msg);
        }

        return msg;
    }

    // Decrypt and verify the signed message
    public static String decrypt(Message message, Person receiver, Person sender) throws Exception {
        if (!"rsa".equals(message.getMetadata("encryption"))) return message.body;

        // Decrypt the message
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, receiver.getPrivateKey());
        byte[] decoded = Base64.getDecoder().decode(message.body);
        String plaintext = new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);

        // Verify the signature
        String signature = message.getMetadata("signature");
        boolean valid = verifySignature(plaintext, signature, sender.getPublicKey());

        if (!valid) {
            throw new SecurityException("Signature verification failed!");
        }

        return plaintext;
    }

    // Signature Methods

    private static String signMessage(String message, PrivateKey privateKey) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] signature = cipher.doFinal(hash);

        return Base64.getEncoder().encodeToString(signature);
    }

    private static boolean verifySignature(String message, String signatureBase64, PublicKey publicKey) throws Exception {
        byte[] signature = Base64.getDecoder().decode(signatureBase64);

        // Decrypt the hash from the signature
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedHash = cipher.doFinal(signature);

        // Hash the received plaintext
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] actualHash = digest.digest(message.getBytes(StandardCharsets.UTF_8));

        return MessageDigest.isEqual(decryptedHash, actualHash);
    }
}
