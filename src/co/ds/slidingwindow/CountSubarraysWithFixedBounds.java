package co.ds.slidingwindow;

/**
 * Date 10/22/2022
 * @author Harsh Jain
 *
 * Problem Link: https://leetcode.com/problems/count-subarrays-with-fixed-bounds/
 */
public class CountSubarraysWithFixedBounds {
    /*
        Sliding window approach.
        TC: O(N), SC: O(1).
    */
    public long countSubarrays(int[] nums, int minK, int maxK) {
        int recentMinIndex = -1, recentMaxIndex = -1, windowStart = 0;
        long subarraysCount = 0;

        for(int windowEnd = 0; windowEnd < nums.length; windowEnd++) {

            if(nums[windowEnd] < minK || nums[windowEnd] > maxK) {
                windowStart = windowEnd + 1;
                recentMinIndex = -1;
                recentMaxIndex = -1;

                continue;
            }

            if(nums[windowEnd] == minK)
                recentMinIndex = windowEnd;

            if(nums[windowEnd] == maxK)
                recentMaxIndex = windowEnd;

            if(recentMaxIndex != -1 && recentMinIndex != -1)
                subarraysCount += (windowEnd - windowStart + 1) -
                        (windowEnd - Math.min(recentMinIndex, recentMaxIndex));
        }

        return subarraysCount;
    }
}

// 4 1 5 6 3 7 2 1 3 6 9    --> array
// 0 1 2 3 4 5 6 7 8 9 10   --> indices
// minK = 1, maxK = 6

// 4 1 5 6 3    To be considered
// 1 5 6 3      To be considered
// 5 6 3        Not to be considered
// 6 3          Not to be considered
// 3            Not to be considered
