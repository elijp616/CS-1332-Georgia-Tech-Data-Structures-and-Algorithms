import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Your implementations of various string searching algorithms.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid epeterson42
 * @GTID 903405747
 *
 * Collaborators: Kia Safai
 *
 * Resources: None
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Sorry invalid pattern, "
                    + "null or 0");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comp can't be null");
        }
        int indexPattern;
        int index;
        List<Integer> indices;
        int[] shiftTable;
        indices = new ArrayList<>();
        index = 0;
        indexPattern = 0;
        if (pattern.length() > text.length()) {
            return indices;
        }
        shiftTable = buildFailureTable(pattern, comparator);
        while (index + pattern.length() <= text.length()) {

            while (indexPattern < pattern.length()
                    && comparator.compare(pattern.charAt(indexPattern),
                    text.charAt(index + indexPattern)) == 0) {
                indexPattern += 1;
            }
            if (indexPattern == 0) {
                index += 1;
            } else {
                //primitive equality
                boolean isMatch = (indexPattern == pattern.length());
                if (isMatch) {
                    indices.add(index);
                }
                index += indexPattern - shiftTable[indexPattern - 1];
                indexPattern = shiftTable[indexPattern - 1];
            }
        }
        return indices;

    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Pattern or comparator "
                    + "can't be null");
        }
        int[] table = new int[pattern.length()];
        if (pattern.length() != 0) {
            table[0] = 0;
        }
        int left;
        int right;
        left = 0;
        right = 1;
        while (right < pattern.length()) {
            if (comparator.compare(
                    pattern.charAt(right), pattern.charAt(left)) == 0) {
                table[right++] = ++left;
            } else {
                if (left == 0) {
                    table[right++] = left;

                } else {
                    left = table[left - 1];
                }
            }
        }
        return table;

    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Sorry invalid pattern, "
                    + "null or 0");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comp can't be null");
        }
        int index;
        int indexPattern;
        List<Integer> myarrList;
        Map<Character, Integer> myTable;
        myarrList = new ArrayList<>();
        myTable = buildLastTable(pattern);
        index = 0;
        while (index + pattern.length() <= text.length()) {
            indexPattern = pattern.length() - 1;
            while (indexPattern >= 0 && comparator.compare(
                    text.charAt(index + indexPattern),
                    pattern.charAt(indexPattern)) == 0) {
                indexPattern -= 1;
            }
            if (indexPattern < 0) {
                myarrList.add(index++);
            } else {
                int marker = myTable.getOrDefault(
                        text.charAt(index + pattern.length() - 1), -1);
                if (marker < indexPattern) {
                    indexPattern -= marker;
                    index += indexPattern;
                } else {
                    index += 1;
                }
            }
        }
        return myarrList;

    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Your pattern can't be null");
        }
        Map<Character, Integer> myTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            myTable.put(pattern.charAt(i), i);
        }
        return myTable;

    }
    /**
     * Checks if text and pattern matches
     *
     * @param pattern a string to check
     * @param text a string to check against
     * @param comparator character equality
     * @return boolean of if it matches
     */
    private static boolean patternMatch(CharSequence pattern, CharSequence text,
                                        CharacterComparator comparator) {
        for (int i = 0; i < pattern.length(); i++) {
            if (comparator.compare(pattern.charAt(i),
                    text.charAt(i)) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to find BASE^(m - 1).
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 113 hash
     * = b * 113 ^ 3 + u * 113 ^ 2 + n * 113 ^ 1 + n * 113 ^ 0 = 98 * 113 ^ 3 +
     * 117 * 113 ^ 2 + 110 * 113 ^ 1 + 110 * 113 ^ 0 = 142910419
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y =
     * (142910419 - 98 * 113 ^ 3) * 113 + 121 = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow() for this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Sorry invalid pattern, "
                    + "null or 0");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comp can't be null");
        }
        int patternHash;
        int otherHash;

        List<Integer> indexList = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return indexList;
        }
        int tmphash = 0;
        //determine new hash (rolling)
        for (int i = 0; i < pattern.length(); i++) {
            tmphash += pattern.charAt(i)
                    * power(BASE, pattern.length() - 1 - i);
        }
        patternHash = tmphash;
        int tmp2 = 0;
        //determine new hash (rolling)
        for (int j = 0; j < text.subSequence(0,
                pattern.length()).length(); j++) {
            tmp2 += text.subSequence(0, pattern.length()).charAt(j)
                    * power(BASE, text.subSequence(0,
                    pattern.length()).length() - 1 - j);
        }
        otherHash = tmp2;
        if (patternHash == otherHash && patternMatch(pattern,
                text.subSequence(0, pattern.length()), comparator)) {
            indexList.add(0);
        }
        for (int k = 1; k < text.length() - pattern.length() + 1; k++) {
            otherHash = (otherHash - (text.charAt(k - 1)

                    * power(BASE, pattern.length() - 1)))
                    * BASE + text.charAt(k + pattern.length() - 1);
            boolean isMatch;
            //primitive equality
            isMatch = (otherHash == patternHash && patternMatch(pattern,
                    text.subSequence(k, k + pattern.length()), comparator));
            if (isMatch) {
                //is true
                indexList.add(k);
            }
        }
        return indexList;

    }
    /**
     * @param base number that will get multiplied
     * @param exponent times that 'a' gets multiplied
     * @return value of a^b
     */
    private static int power(int base, int exponent) {
        if (exponent == 0) {
            return 1;
        } else {
            return base * power(base, exponent - 1);
        }
    }

}
