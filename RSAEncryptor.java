import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RSAEncryptor {
    public static Message encrypt(Person sender, Person receiver, String message, SocialGraph graph) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, receiver.getPublicKey());
        byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(encrypted);

        Message msg = new Message(sender.getId(), receiver.getId(), encoded);
        msg.setMetadata("compression", "rsa");

        List<Person> path = graph.findPath(sender, receiver);
        if (path != null) {
            for (Person p : path.subList(1, path.size())) {
                p.getInbox().add(msg);
            }
        }
        return msg;
    }

    public static String decrypt(Message message, Person receiver) throws Exception {
        if (!message.getMetadata("compression").equals("rsa")) return message.body;
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, receiver.getPrivateKey());
        byte[] decoded = Base64.getDecoder().decode(message.body);
        return new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);
    }
}
```
