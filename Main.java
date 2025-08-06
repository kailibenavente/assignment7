public class Main {
    public static void main(String[] args) throws Exception {
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");

        Message m = MessageSender.sendRLEMessage(alice, bob, "AAABBBCCDAA");

        System.out.println("Compressed message: " + m.body);
        System.out.println("Decompressed message: " + RLECompressor.decompress(m.body));
    }
}