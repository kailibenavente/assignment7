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

        public void setMetadata(String key, String value) {
        metadata.put(key, value);
    }

        public String getMetadata(String key) {
        return metadata.get(key);
    }

    public String toString() {
        return "From: " + senderId + "\n"
             + "To: " + receiverId + "\n"
             + "Metadata: " + metadata + "\n"
             + "Body: " + body;
    }

}