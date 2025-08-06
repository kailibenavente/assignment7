import java.util.HashMap;
import java.util.Map;

public class Message {
    public String senderId;
    public String receiverId;
    public Map<String, String> metadata;
    public String body;

    public Message(String senderId, String receiverId, String body) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.body = body;
        this.metadata = new HashMap<>();
    }
}