public class MessageSender {

    // Sends a run-length encoded message from sender to receiver
    public static Message sendRLEMessage(Person sender, Person receiver, String rawText) {
        String compressed = RLECompressor.compress(rawText);

        Message m = new Message(sender.getId(), receiver.getId(), compressed);
        m.metadata.put("compression", "rle");
        m.metadata.put("originalLength", String.valueOf(rawText.length()));

        receiver.getInbox().add(m);
        return m;
    }
}