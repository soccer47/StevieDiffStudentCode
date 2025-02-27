import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.max;

/**
 * Plagiarism Checker
 * A tool for finding the longest shared substring between two documents.
 *
 * @author Zach Blick
 * @author Stevie K. Halprin
 */
public class PlagiarismChecker {

    /**
     * This method finds the longest sequence of characters that appear in both texts in the same order,
     * although not necessarily contiguously.
     *
     * @param doc1 the first document
     * @param doc2 the second
     * @return The length of the longest shared substring.
     */
    public static int longestSharedSubstring(String doc1, String doc2) {
        // 2D array representing possible paths for substrings
        HashMap<String, Integer>[][] path = new HashMap[doc1.length()][doc2.length()];

        // Initialize all the indexes in the 2D array
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[0].length; j++) {
                path[i][j] = new HashMap<>();
            }
        }

        // Holder integers to represent indexes to the left and above of the current index
        HashMap<String, Integer> left;
        HashMap<String, Integer> up;

        // Iterate through the substring board bottom-up (tabulation approach)
        for (int i = 0; i < doc1.length(); i++) {
            for (int j = 0; j < doc2.length(); j++) {
                // If the current letters of each doc match, set the current index to the upper-left diagonal index + 1
                // Note- upper-left diagonal index represents longest substring within confines of current indexes of
                // each of the docs, so current index should be 1 greater because matching leads to adding another
                // letter (the current letter)
                if (doc1.charAt(i) == doc2.charAt(j)) {
                    // Make sure the upper left diagonal index exists
                    if (i > 0 && j > 0) {
                        char newChar = doc1.charAt(i);
                        for (String sub : path[i][j].keySet()) {
                            path[i][j].put(sub + newChar, sub.length() + 1);
                        }
                    }
                    // Otherwise set the current index to 1 (start of its own substring)
                    else {
                        path[i][j].put(doc1.charAt(i) + "", 1);
                    }
                }
                // Otherwise take in the longest substring that has previously been found within the current indexes
                // of each of the docs on the board
                else {
                    // If there is a valid index above the current index, set 'up' to the index above
                    if (i > 0) {
                        up = path[i - 1][j];
                    }
                    else {
                        // Otherwise let 'up' be an empty HashMap
                        up = new HashMap<>();
                    }
                    // If there is a valid index to the left of the current index, set 'left' to the left index
                    if (j > 0) {
                        left = path[i][j - 1];
                    }
                    else {
                        // Otherwise let 'left' be an empty HashMap
                        left = new HashMap<>();
                    }

                    // Set the current index to the longer of the left and up indexes
                    if (getStringLength(left) > getStringLength(up)) {path[i][j] = left;}
                    else if (getStringLength(left) < getStringLength(up)) {path[i][j] = up;}
                    // If the left and up HashMaps contain Strings of the same length, add both to the current index
                    else {
                        path[i][j].putAll(up);
                        int length = getStringLength(up);
                        for (String key : left.keySet()) {
                            path[i][j].put(key, length);
                        }
                    }
                }
            }
        }

        // Print the number of shared longest substrings
        System.out.println(path[doc1.length() - 1][doc2.length() - 1].size());
        // Return the length of the longest substring
        return getStringLength(path[doc1.length() - 1][doc2.length() - 1]);
    }


    // Return the length of the first key entry in a given HashMap
    public static int getStringLength(HashMap<String, Integer> map) {
        for (int length : map.values()) {
            // Return the length of the first key (also the value of the given key)
            return length;
        }
        // Otherwise if there are no entries, return length 0
        return 0;
    }

    // Returns the length of the longest shared substring and also prints out one of the longest shared substrings
    public static int onlyOneStringlongestSharedSubstring(String doc1, String doc2) {
        // 2D array representing possible paths for substrings
        String[][] path = new String[doc1.length()][doc2.length()];

        // Holder integers to represent indexes to the left and above of the current index
        String left;
        String up;

        // Iterate through the substring board bottom-up (tabulation approach)
        for (int i = 0; i < doc1.length(); i++) {
            for (int j = 0; j < doc2.length(); j++) {
                // If the current letters of each doc match, set the current index to the upper-left diagonal index +
                // the current matching char
                // Note- upper-left diagonal index represents longest substring within confines of current indexes of
                // each of the docs, so current index should be 1 greater because matching leads to adding another
                // letter (the current letter)
                if (doc1.charAt(i) == doc2.charAt(j)) {
                    // Make sure the upper left diagonal index exists
                    if (i > 0 && j > 0) {
                        path[i][j] = path[i - 1][j - 1] + doc1.charAt(i);
                    }
                    // Otherwise set the current index to a String of length 1 (start of its own substring)
                    else {
                        path[i][j] = doc1.charAt(i) + "";
                    }
                }
                // Otherwise take in the longest substring that has previously been found within the current indexes
                // of each of the docs on the board
                else {
                    // If there is a valid index above the current index, set 'up' to the index above
                    if (i > 0) {
                        up = path[i - 1][j];
                    }
                    // Otherwise set 'up' to an empty String
                    else {
                        up = "";
                    }
                    // If there is a valid index to the left of the current index, set 'left' to the left index
                    if (j > 0) {
                        left = path[i][j - 1];
                    }
                    // Otherwise set 'left' to an empty String
                    else {
                        left = "";
                    }

                    // Set the current index to the longer of the left and up indexes
                    if (left.length() > up.length()) {path[i][j] = left;}
                    else {path[i][j] = up;}
                }
            }
        }

        // Print out (one of) the shared longest substring
        System.out.println(path[doc1.length() - 1][doc2.length() - 1]);
        // Return the length of the longest substring
        return path[doc1.length() - 1][doc2.length() - 1].length();
    }
}

