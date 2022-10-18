package co.ds.slidingwindow;

import java.util.Arrays;

/**
 * Date 10/18/2022
 * @author Harsh Jain
 *
 * Problem Link: https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/
 */
public class LongestSubstringWithAtleastKRepeatingCharacters {
    /*
        Divide and Conquer Approach:
            1. The most important point to think here is why and how divide and conquer?
               Let's take an example string: aabbcddeeeff and k = 2.

            2. Here if we take a look at character c, we couldn't include it in either of the halves, because if
               including it would violate the condition of each character having at least 2 occurrences in the given
               substring. Let's say we include it then the possible substrings could be:

                aabbcdd, aabbc, cddeeff, bcddeeff and many more....

            But none of the above strings satisfies the above conditions and that's why we could find the partitioning
            position, i.e. a character's index which doesn't satisfies the given condition and then dividing the given
            problem into 2 different sub problems and then finding the final result from the result of the sub problems.

            TC: O(N*N) (Worst Case), SC: O(N).
    */
    public int longestSubstring(String s, int k) {
        return longestSubstringLength(s, k, 0, s.length() - 1);
    }

    private int longestSubstringLength(String s, int k, int start, int end) {
        if(start > end)
            return 0;

        int[] freqArray = new int[26];

        // Storing the freq of occurrence of characters for the current window(from start till end).
        for(int i = start; i <= end; i++)
            freqArray[s.charAt(i) - 'a']++;

        // Now finding the parition position and then would make the recursive calls.
        for(int mid = start; mid <= end; mid++) {
            if(freqArray[s.charAt(mid) - 'a'] >= k)
                continue;
            int nextToMid = mid + 1;

            /*
                Making our algorithm efficient, by ignoring the invalid characters right after the mid
                index as well.
            */
            while(nextToMid <= end && freqArray[s.charAt(nextToMid) - 'a'] < k)
                nextToMid++;

            return Math.max(longestSubstringLength(s, k, start, mid - 1),
                    longestSubstringLength(s, k, nextToMid, end));
        }

        /*
            If we get out of above loop, it means there isn't any invalid character present in the
            current window and hence this window has all the characters occurring at least k times.
        */
        return end - start + 1;
    }

    /*
        Sliding Window Approach:
            Refer to the leetcode solution section for detailed explanation.
            TC: O(26*N), SC: O(26)
    */
    public int longestSubstringSlidingWindow(String s, int k) {
        int length = s.length();
        int countOfUniqueChars = getCountOfUniqueCharacters(s, length);

        int ans = 0;

        int[] count = new int[26];

        for(int currUnique = 1; currUnique <= countOfUniqueChars; currUnique++) {
            Arrays.fill(count, 0);

            int windowStart = 0, windowEnd = 0, uniqueChars = 0, countAtLeastK = 0;
            while(windowEnd < length) {

                // Expand the current window.
                if(uniqueChars <= currUnique) {
                    int index = s.charAt(windowEnd) - 'a';
                    if(count[index] == 0)
                        uniqueChars++;
                    count[index]++;
                    if(count[index] == k)
                        countAtLeastK++;
                    windowEnd++;
                }

                // Shrink the current window.
                else {
                    int index = s.charAt(windowStart) - 'a';
                    if(count[index] == k)
                        countAtLeastK--;
                    count[index]--;
                    if(count[index] == 0)
                        uniqueChars--;

                    windowStart++;
                }

                if(uniqueChars == countAtLeastK)
                    ans = Math.max(ans, windowEnd - windowStart);
            }
        }

        return ans;
    }

    private int getCountOfUniqueCharacters(String s, int length) {
        boolean[] isPresent = new boolean[26];
        int count = 0;

        for(int i = 0; i < length; i++) {
            if(!isPresent[s.charAt(i) - 'a']) {
                isPresent[s.charAt(i) - 'a'] = true;
                count++;
            }
        }

        return count;
    }

}
