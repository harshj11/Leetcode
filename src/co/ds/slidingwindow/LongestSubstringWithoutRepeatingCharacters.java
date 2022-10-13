package co.ds.slidingwindow;

/**
 * Date 10/13/2022
 * @author Harsh Jain
 *
 * Problem Link: https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */
public class LongestSubstringWithoutRepeatingCharacters {
    /*
        Optimal Approach:
            Think of a window of characters.
            1. Initialize i = 0, j = 1.
            2. Increment j until you find a character which is already present in the window.(repeating character)
                (To keep track of the characters in the current window, you could take a boolean array or a hashset).
            3. Once a repeating character is found, check whether the window size is maximum or not.
            4. After this increment i. Repeat the procedure till j < length.

            Exercise: Think of the condition to handle these case: bbbbbb, abbbbb, abcdddddd etc.

            TC: O(n), SC: O(n).
    */
    public int lengthOfLongestSubstring(String s) {

        int length = s.length();

        if(length == 0 || length == 1)
            return length;

        /*
            A boolean array to track the characters present under current window.
            boolean[] size is 96 because we have to consider only spaces, digits, symbols and
            characters and before ascii value 32, none of the above are present. Since there are
            128 ascii characters, 128 - 32(0 till 31) = 96
        */
        boolean[] isPresent = new boolean[96];
        int i = 0, j = 1;

        isPresent[s.charAt(i) - 32] = true;

        int maxLength = 1;

        while(j < length) {

            // Add the current character to the window and mark it "present" in the curr window till the condition is true.
            while(j < length && !isPresent[s.charAt(j) - 32]) {
                isPresent[s.charAt(j) - 32] = true;
                j++;
            }

            // Mark the starting character in the window to "not present".
            isPresent[s.charAt(i) - 32] = false;

            maxLength = Math.max(maxLength, j - i);

            // Remove the starting character from the window.
            i++;

            // for handling the case: ___aaaaaa___
            if(i == j) {
                isPresent[s.charAt(i) - 32] = true;
                j = i + 1;
            }
        }

        maxLength = Math.max(maxLength, j - i);

        return maxLength;
    }
}
