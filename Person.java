import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private String id;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private List<Message> inbox;

    public Person(String id) throws Exception {
        this.id = id;
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair pair = gen.generateKeyPair();
        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
        this.inbox = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Message> getInbox() {
        return inbox;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

}