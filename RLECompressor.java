public class RLECompressor {

    private RLECompressor() {}

    public static String compress(String input) {
        if (input == null || input.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i <= input.length(); i++) {
            if (i == input.length() || input.charAt(i) != input.charAt(i - 1)) {
                sb.append(count).append(input.charAt(i - 1));
                count = 1;
            } else {
                count++;
            }
        }
        return sb.toString();
    }

    public static String decompress(String input) {
        if (input == null || input.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < input.length()) {
            StringBuilder num = new StringBuilder();
            while (Character.isDigit(input.charAt(i))) {
                num.append(input.charAt(i++));
            }
            int count = Integer.parseInt(num.toString());
            char c = input.charAt(i++);
            sb.append(String.valueOf(c).repeat(count));
        }
        return sb.toString();
    }
}