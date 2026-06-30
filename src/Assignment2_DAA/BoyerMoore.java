package Assignment2_DAA;

public class BoyerMoore {

    public static void main(String[] args) {
        String text = "Insertion sort typically has a smaller constant factor than merge sort";
        String pattern = "sort";

        int[] last = new int[256];
        for (int i = 0; i < 256; i++) {
            last[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            last[pattern.charAt(i)] = i;
        }

        int n = text.length();
        int m = pattern.length();
        int s = 0; // shift of pattern over text

        while (s <= (n - m)) {
            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                System.out.println("Pattern found at index " + s);
                s = s + 1;
            } else {
                int badChar = text.charAt(s + j);
                int shift = j - last[badChar];
                if (shift < 1) {
                    shift = 1;
                }
                s = s + shift;
            }
        }
    }
}