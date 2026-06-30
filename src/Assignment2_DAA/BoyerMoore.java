package Assignment2_DAA;

import java.util.*;

public class BoyerMoore {

    public static void main(String[] args) {
        String text = "Insertion sort typically has a smaller constant factor than merge sort";
        String pattern = "sort";

        char[] pat = pattern.toCharArray();
        char[] txt = text.toCharArray();
        int m = pat.length;
        int n = txt.length;

        System.out.println("Text    : " + text);
        System.out.println("Pattern : " + pattern);
        System.out.println("n = " + n + ", m = " + m);

        int[] badChar = new int[256];
        Arrays.fill(badChar, -1);
        for (int i = 0; i < m; i++) {
            badChar[pat[i]] = i;
        }

        int[] shift = new int[m + 1];
        int[] bpos = new int[m + 1];
        int[] caseLabel = new int[m + 1]; // which case (1, 2, or 3) filled this shift value

        preprocessStrongSuffix(shift, bpos, caseLabel, pat, m);
        preprocessCase2(shift, bpos, caseLabel, m);

        System.out.println("\n=== Searching step by step ===");

        int s = 0;
        int attempt = 1;
        List<Integer> foundAt = new ArrayList<>();

        System.out.println("\nAttempt | Shift s | Matched | Mismatch          | BadChar(Case)   | GoodSuffix(Case) | ShiftUsed");
        System.out.println("--------|---------|---------|-------------------|-----------------|-------------------|----------");

        while (s <= n - m) {
            int j = m - 1;
            while (j >= 0 && pat[j] == txt[s + j]) {
                j--;
            }

            if (j < 0) {
                int used = shift[0];
                foundAt.add(s);
                System.out.printf("%-7d | %-7d | %-7s | %-17s | %-15s | %-17s | %d%n",
                        attempt, s, "all " + m, "FOUND at " + s, "-", "shift[0]=" + used, used);
                s += used;
            } else {
                char mismatchChar = txt[s + j];
                int lastOcc = badChar[mismatchChar];
                int bcShift = Math.max(1, j - lastOcc);
                // Bad Character Case: Case 1 = char not in pattern at all, Case 2 = char is in pattern
                int bcCase = (lastOcc == -1) ? 1 : 2;

                int gsShift = shift[j + 1];
                int gsCase = caseLabel[j + 1] == 0 ? 3 : caseLabel[j + 1];

                int actualShift = Math.max(bcShift, gsShift);
                int matchedLen = m - 1 - j;
                String mismatchInfo = "pat[" + j + "]='" + pat[j] + "' vs '" + mismatchChar + "'";

                System.out.printf("%-7d | %-7d | %-7s | %-17s | %-15s | %-17s | %d%n",
                        attempt, s, matchedLen, mismatchInfo,
                        bcShift + " (Case " + bcCase + ")", gsShift + " (Case " + gsCase + ")", actualShift);

                s += actualShift;
            }

            attempt++;
        }

        System.out.println();
        if (foundAt.isEmpty()) {
            System.out.println("Pattern \"" + pattern + "\" not found in text.");
        } else {
            StringBuilder result = new StringBuilder("Pattern \"" + pattern + "\" found at index");
            if (foundAt.size() > 1) result.append("es");
            result.append(": ");
            for (int i = 0; i < foundAt.size(); i++) {
                result.append(foundAt.get(i));
                if (i != foundAt.size() - 1) result.append(" and ");
            }
            System.out.println(result);
        }
    }

    static void preprocessStrongSuffix(int[] shift, int[] bpos, int[] caseLabel, char[] pat, int m) {
        int i = m, j = m + 1;
        bpos[i] = j;
        while (i > 0) {
            while (j <= m && pat[i - 1] != pat[j - 1]) {
                if (shift[j] == 0) {
                    shift[j] = j - i;
                    caseLabel[j] = 1;
                }
                j = bpos[j];
            }
            i--;
            j--;
            bpos[i] = j;
        }
    }

    static void preprocessCase2(int[] shift, int[] bpos, int[] caseLabel, int m) {
        int j = bpos[0];
        for (int i = 0; i <= m; i++) {
            if (shift[i] == 0) {
                shift[i] = j;
                caseLabel[i] = (j == m) ? 3 : 2;
            }
            if (i == j) {
                j = bpos[j];
            }
        }
    }
}