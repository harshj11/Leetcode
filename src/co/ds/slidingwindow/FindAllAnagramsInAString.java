package co.ds.slidingwindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Date 10/20/2022
 * @author Harsh Jain
 *
 * Problem Link: https://leetcode.com/problems/find-all-anagrams-in-a-string/
 */
public class FindAllAnagramsInAString {
    public List<Integer> findAnagramsBrute(String s, String p) {
        /*
            Brute Force Approach
                1. For every substring of length equal to the string p's length, try to look for
                   all the characters of in s.
                2. If all the characters are exactly matching, add this index to the answer, else
                   move on to the next index.

            TC: O(|S| * |P|), SC: O(|P|).
        */
        int l1 = s.length(), l2 = p.length();
        List<Integer> indices = new ArrayList<>();

        // Creating a freq array to store the count of characters in the string p.
        int[] freqAnagram = new int[26];

        for(int i = 0; i < l2; i++)
            freqAnagram[p.charAt(i) - 'a']++;

        for(int i = 0; i <= l1 - l2; i++) {

            int[] freqCurr = new int[freqAnagram.length];
            System.arraycopy(freqAnagram, 0, freqCurr, 0, freqAnagram.length);

            for(int j = i; j < i + l2; j++) {
                freqCurr[s.charAt(j) - 'a']--;
            }

            if(isValidFreqArray(freqCurr))
                indices.add(i);
        }

        return indices;
    }

    /**
     * A utility method to check if the array passed as an argument has the valid count of integers in it.
     *
     * @param freqArray, containing count of characters.
     * @return true is the freqArray[] is valid.
     */
    private boolean isValidFreqArray(int[] freqArray) {
        for(int val: freqArray) {
            if (val != 0)
                return false;
        }
        return true;
    }

    /*
        Optimal Approach(Sliding window solution).
        TC: O(|S|), SC: O(|P|).
    */
    public List<Integer> findAnagramsOptimal(String s, String p) {

        int l1 = s.length(), l2 = p.length();
        List<Integer> indices = new ArrayList<>();

        // Creating a freq array to store the count of characters in the string p.
        int[] freqAnagram = new int[26];

        for(int i = 0; i < l2; i++)
            freqAnagram[p.charAt(i) - 'a']++;

        int[] freqInString = new int[26];
        int windowStart = 0, windowEnd = 0;

        while(windowEnd <= l1) {

            // Checking if the current window size equals the length of p. If yes validate the current window.
            if(windowEnd - windowStart == l2) {
                if(isValidFreqArray(freqInString, freqAnagram))
                    indices.add(windowStart);
            }

            // Necessary!
            if(windowEnd == l1)
                break;

            int index = s.charAt(windowEnd) - 'a';

            /*
                Expand the window.

                Here we need to make sure that following two conditions are followed:

                1. The size of the current window should be equal to the length of the string p.
                2. The character at the index windowEnd(which is going to be added to the current
                   window) should not exceed the expected count(i.e. the value which is present in
                   the freqAnagram array for the character at windowEnd index).
            */
            if(freqInString[index] < freqAnagram[index] && windowEnd - windowStart < l2) {
                freqInString[index]++;
                windowEnd++;
            }

            /*
                Shrink the window.

                If either of the above conditions(mentioned in the expand the window section)
                is not followed, then shrink the window.
            */
            else {
                while(freqInString[index] >= freqAnagram[index] || windowEnd - windowStart >= l2) {
                    freqInString[s.charAt(windowStart) - 'a']--;
                    windowStart++;
                }
            }
        }

        return indices;
    }

    /**
     * Tests the test[] against the given array. Checks if the count in the given[] matches the count
     * in the test[].
     *
     * @param test, array to be validated.
     * @param given, array against which validation has to be performed.
     *
     * @return true if the test array is valid.
     */
    private boolean isValidFreqArray(int[] test, int[] given) {
        for(int i = 0; i < test.length; i++)
            if(test[i] != given[i])
                return false;
        return true;
    }
}
