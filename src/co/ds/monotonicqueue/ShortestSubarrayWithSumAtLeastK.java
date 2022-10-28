package co.ds.monotonicqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Date 10/28/2022
 * @author Harsh Jain
 *
 * Problem Link: https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k
 */
public class ShortestSubarrayWithSumAtLeastK {
    /*
        Monotonic Queue Approach
        TC: O(N), SC: O(N)
    */
    public int shortestSubarray(int[] nums, int k) {
        /*
            Why sliding window wouldn't work?
            -> Because decreasing window size doesn't guarantees the sum of the window to be monotonic.
            See this test case [84,-37,32,40,95], K=167 (From my sliding window solution).

            We would like to have our window such that it's monotonic.
        */

        // indices is a monotonic increasing queue which would have indices of the prefix sums.
        Deque<Integer> indices = new ArrayDeque<>();
        indices.addLast(0);

        int ans = Integer.MAX_VALUE;

        long[] prefixSum = new long[nums.length + 1];

        for(int i = 0; i < nums.length; i++)
            prefixSum[i + 1] = prefixSum[i] + nums[i];

        /*
            Now we want smallest subarray such that sum of its elements is at least k, i.e. according
            to our solution we need to have P[j] - P[i] >= k such that (j - i) is minimum.

            or P[j] >= P[i] + k.

            Here(in terms of sliding window approach) P[j] is going to be our windowEnd and P[i] is
            going to be our windowStart; P[j] is what we would get on traversing the prefixSum array,
            but our P[i] is going to be considered from the indices stored in the Deque
            (P[indices.peekFirst]).
        */
        for(int i = 0; i < prefixSum.length; i++) {

            while(!indices.isEmpty() && prefixSum[indices.peekLast()] > prefixSum[i])
                indices.removeLast();

            while(!indices.isEmpty() && prefixSum[i] >= k + prefixSum[indices.peekFirst()])
                ans = Math.min(ans, i - indices.removeFirst());

            indices.addLast(i);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
