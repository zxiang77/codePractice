package codePractice;

import java.util.*;

public class BinarySearchSolutions {

    // 228
    // via binary search
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < nums.length; i++) {
            int end = endIndex(nums, i);
            if(end == i || (end == i + 1 && nums[end] - nums[i] > 1)) {
                result.add(Integer.toString(nums[end]));
                if (end == i + 1) i = end - 1;
            }
                
            else {
                result.add(Integer.toString(nums[i]) + "->" + Integer.toString(nums[end]));
                i = end;
            } 
            
        }
        return result;
    }
    // {0,1,2,4,5,7};
    private int endIndex(int[]nums, int start) {
        int i = start, j = nums.length - 1;
        while(i < j) {
            int mid = i + (j - i) / 2;
            if(j - i == 1 && (nums[j] - nums[i]) == 1) return j;
            else if(j - i == 1 && ((long)nums[j] - (long)nums[i]) > 1L) return i;
            else if((nums[mid] - nums[start]) == mid - start) i = mid;
            else j = mid - 1;
        }
        return i;
    }
    
}
