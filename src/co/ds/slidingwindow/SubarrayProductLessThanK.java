package co.ds.slidingwindow;

/**
 * Date 10/21/2022
 * @author Harsh Jain
 *
 * Problem Link: https://leetcode.com/problems/subarray-product-less-than-k/
 */
public class SubarrayProductLessThanK {
    /*
        Binary Search Logarithm Approach.(From Leetcode solution section, Awesome!)
        Solution: Logarithm of product of all the elements = Sum of logarithm of individual elements.
        TC: O(N*logN), SC: O(N).
    */
    public int numSubarrayProductLessThanKBinarySearch(int[] nums, int k) {
        if(k <= 1) return 0;

        double logk = Math.log(k);

        int ans = 0;

        double[] prefixLog = new double[nums.length];
        prefixLog[0] = Math.log(nums[0]);

        for(int i = 1; i < nums.length; i++)
            prefixLog[i] = prefixLog[i - 1] + Math.log(nums[i]);

        for(int i = 0; i < nums.length; i++) {
            if(prefixLog[i] < logk)
                ans += (i + 1);
            else {
                int high = i - 1, low = 0;
                while(low <= high) {
                    int mid = low + ((high - low) >> 1);
                    if(prefixLog[i] - prefixLog[mid] < logk - 1e-9)
                        high = mid - 1;
                    else
                        low = mid + 1;
                }

                ans += (i - low);
            }
        }

        return ans;
    }

    /*
        Sliding Window Approach.
        TC: O(N), SC: O(1).
    */
    public int numSubarrayProductLessThanK(int[] nums, int k) {

        if(k <= 1) return 0;

        int prod = 1;
        int ans = 0;

        // windowEnd denotes the index to be added.
        int windowStart = 0, windowEnd = 0;

        while(windowEnd < nums.length) {

            // Expand the window.
            if(prod * nums[windowEnd] < k) {
                ans += (windowEnd - windowStart + 1);
                prod *= nums[windowEnd++];
            }

            // Shrink the window.
            else {
                /*
                    If on shrinking the window, we reach end of the window, i.e. when we keep
                    on incrementing the windowStart, we might reach at a point where windowStart
                    would become = windowEnd and at that moment our prod would = 1 and in such
                    a case we wouldn't want our prod /= nums[windowStart++], which would make
                    prod = 0 as well as windowStart > windowEnd. Therefore this if condition is
                    very important.
                */
                if(windowStart == windowEnd) {
                    windowStart++;
                    windowEnd++;
                } else
                    prod /= nums[windowStart++];
            }
        }
        return ans;
    }
}
