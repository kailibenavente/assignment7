public class Main {
    public static void main(String[] args) throws Exception {
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        Person charlie = new Person("Charlie");

        SocialGraph graph = new SocialGraph();
        graph.addPerson(alice);
        graph.addPerson(bob);
        graph.addPerson(charlie);

        graph.connect(alice, charlie);
        graph.connect(charlie, bob);

        String originalText = "AAABBBCCDAA";
        Message m = MessageSender.sendRLEMessage(alice, bob, "AAABBBCCDAA");

        System.out.println("\nRLE Compressed + Decompressed");
        System.out.println("Original message: " + originalText);
        System.out.println("Compressed message: " + m.body);
        System.out.println("Decompressed message: " + RLECompressor.decompress(m.body));

        String secureText = "This is a confidential and signed message.";

        Message secureMessage = RSAEncryptor.encrypt(alice, bob, secureText, graph);

        System.out.println("\n\nRSA Encrypted + Signed");
        System.out.println("Encrypted Body: " + secureMessage.body);
        System.out.println("Signature: " + secureMessage.getMetadata("signature"));

        // Receiver decrypts and verifies
        String decrypted = RSAEncryptor.decrypt(secureMessage, bob, alice);

        System.out.println("\nDecrypted + Verified Message: " + decrypted);
    }
}
