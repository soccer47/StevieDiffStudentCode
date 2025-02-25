import java.util.HashMap;

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
     * @param doc1 the first document
     * @param doc2 the second
     * @return The length of the longest shared substring.
     */
    public static int longestSharedSubstring(String doc1, String doc2) {
        // If the same letter reappears later in the String it shouldn't start the shared substring
        // HashMap? Graph of letters? Indexes of each letter?
        // >> find the letter that comes soonest after in both docs
        // >>> first letter that appears in both docs after the current index

        // HashMaps to hold letters have been found after the last used index in each doc
        // Keys are the letter, values are the index of the letter in the doc
        HashMap<Character, Integer> one = new HashMap();
        HashMap<Character, Integer> two = new HashMap();

        // Int to represent index currently being used on each doc
        int index1 = -1;
        int index2 = -1;

        // Int to represent length of current shared substring
        int length = 0;

        // Continuously find the first letter to appear after the current index in both docs
        while (index1 < doc1.length() - 1 && index2 < doc2.length() - 1) {
            // Increment the current indexes by 1
            index1++;
            index2++;
            // Get the next character of each doc
            char first = doc1.charAt(index1);
            char second = doc2.charAt(index2);

            // Add the letters to the doc if they haven't already been passed since the last used index
            if (!one.containsKey(first)) {
                one.put(first, index1);
            }
            if (!two.containsKey(second)) {
                two.put(second, index2);
            }

            // Check if the current letter for doc1 has been passed in doc2
            if (two.containsKey(first)) {
                // If so, set the last used index of doc2 to the instance of the character
                index2 = two.get(first);
                // Then reset both of the HashMaps containing the characters
                one.clear();
                two.clear();
                // Finally, increment the length of the substring by 1
                length++;
            }
            // If not, check if the current letter for doc2 has been passed in doc1
            else if (one.containsKey(second)) {
                // If so, set the last used index of doc2 to the instance of the character
                index1 = one.get(second);
                // Then reset both of the HashMaps containing the characters
                one.clear();
                two.clear();
                // Finally, increment the length of the substring by 1
                length++;
            }
        }

        // Return the length of the longest substring
        return length;
    }
}
