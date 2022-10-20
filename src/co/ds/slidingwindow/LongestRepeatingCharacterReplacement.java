package co.ds.slidingwindow;

/**
 * Date 10/19/2022
 * @author Harsh Jain
 *
 * Problem Link: https://leetcode.com/problems/longest-repeating-character-replacement/
 */
public class LongestRepeatingCharacterReplacement {
    /*
        Brute Force Approach:
            Try exploring all the substrings, now the required longest substring could be found out
            by looking for the character occurring the maximum number of times in the given window
            (substring) and then replace the k characters amongst the rest of the characters from
            this maximum occurring characters. (Just think...).

            TC: O(N^2), SC: O(26).
    */
    public int characterReplacementBrute(String s, int k) {
        int length = s.length();
        int maxLength = 0;

        for(int left = 0; left < length; left++) {

            int mostFrequent = 0;
            int[] freqArr = new int[26];

            for(int right = left; right < length; right++) {

                int index = s.charAt(right) - 'A';
                freqArr[index]++;
                mostFrequent = Math.max(mostFrequent, freqArr[index]);

                if(right - left + 1 - mostFrequent > k)
                    break;
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        return maxLength;
    }

    /*
        Optimal Sliding Window Approach. (Optimizing the previous approach)
        TC: O(N * 26), SC: O(26).
    */
    public int characterReplacement(String s, int k) {
        int length = s.length();
        int windowStart = 0, windowEnd = 0, idxMaxFreq = s.charAt(windowEnd) - 'A', maxLength = 0;

        int[] freq = new int[26];
        freq[s.charAt(windowEnd) - 'A'] = 1;

        while(windowEnd < length) {

            // Expand the window.
            if(windowEnd - windowStart + 1 - freq[idxMaxFreq] <= k) {
                maxLength = Math.max(maxLength, windowEnd - windowStart + 1);

                windowEnd++;

                if(windowEnd == length)
                    break;

                int index = s.charAt(windowEnd) - 'A';
                freq[index]++;

                if(freq[index] > freq[idxMaxFreq])
                    idxMaxFreq = index;
            }

            // Shrink the window.
            else {
                int index = s.charAt(windowStart) - 'A';

                windowStart++;
                freq[index]--;

                if(index == idxMaxFreq)
                    idxMaxFreq = getMaxFreqIndex(freq, idxMaxFreq);
            }
        }

        return maxLength;
    }

    private int getMaxFreqIndex(int[] freq, int index) {
        for(int i = 0; i < 26; i++) {
            if(freq[i] > freq[index])
                index = i;
        }

        return index;
    }
}
