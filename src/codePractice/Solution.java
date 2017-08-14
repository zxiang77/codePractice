package codePractice;

import java.util.*;

public class Solution {
	/**
	 * solution for lc 592 Fraction Addition and Subtraction
	 * @param expression
	 * @return
	 */
    public String fractionAddition(String expression) {
//        String[] nums = expression.split("(?=[-,+])");
    	List<String> nums = new ArrayList<>();
        int prev = 0, cur = 0, len = expression.length();
        for (cur = 0; cur < len; cur++) {
        	if (expression.charAt(cur) == '-' || expression.charAt(cur) == '+') {
        		String tmp = expression.substring(prev, cur);
        		if (tmp.compareTo("") != 0) nums.add(tmp);
        		prev = cur;
        	}
        }
        
        String tmp = expression.substring(prev, cur);
    	if (tmp.compareTo("") != 0) nums.add(tmp);
    	
        String sln = "0/1";
        for (String num : nums) {
            sln = add(sln, num);
        }
        return sln;
    }
    
    public String add(String str1, String str2) {
        String[] num1 = str1.split("/");
        String[] num2 = str2.split("/");
        int a = Integer.parseInt(num1[0]);
        int b = Integer.parseInt(num1[1]);
        int c = Integer.parseInt(num2[0]);
        int d = Integer.parseInt(num2[1]);
        
        int num = a * d + c * b;
        int den = b * d;
        int gcdVal = gcd(den, Math.abs(num));
        return num / gcdVal + "/" + den / gcdVal;
    }
    
    public int gcd(int a, int b) {
		if (a == 0 || b == 0) return a + b;
		return gcd(b, a % b);
	}
    
    /**
     * A dp solution to solve @486
     * for each subproblem its, f is the leaf of minimax, it only chooses from a and b;
     * in this layer, we have things like (0,1), (1,2),...,etc. which are only 2 scores subproblems (leaf node condition)
     * then j is the next layer in minimax. It chooses from f and g. Because it is next layer in the minimax. The payoff 
     * of player 1 in leaf node is that of player 2. f is dp[0,1] can be written as payoff_1 - payoff_2 for leaf node. 
     * From this layer, it means payoff_2' - payoff_1' (it is for (0,1)). Similarly, payoff of g is payoff_2'' - payoff_1''
     * (this is for (1,2)); then player1 choose nums[2] for the former condition and nums[0] for later condition. 
     * 
     * dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
     * 
     * we realize that we only need information for previous layer. It can be condensed to O(n) complexity in space
     * 
     *    j -> (0,2)
     * |a||f|j|m|o|
     * | ||b|g|k|n|
     * | || |c|h|l|
     * | || | |d|i|
     * | || | | |e|
     * @param nums
     * @return
     */
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < nums.length; i++) dp[i][i] = nums[i];
        
        for (int len = 1; len < nums.length; len++) {
            for (int i = 0; i < nums.length - len; i++) {
                int j = i + len;
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] >= 0;
    }
    
    /**
     * 
     * @author zilixiang
     *
     */
    
    /**
     * DP @95
     * start from length 0, but it will traverse through all the numbers. As BST from an array will have the following pattern:
     * [L,L,L,L,Root,R,R,R,R]
     * everything on LHS of Root are left node, RHS as right node.
     * 
     * Starting from length 0 to length n. for each length @len, we iterate i through 0 to len - 1;
     * Node on LHS are represented as result[i], RHS as result[len-i-1];
     * 
     * @param n
     * @return
     */
    
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode>[] result = new List[n + 1];
        result[0] = new ArrayList<TreeNode>();
        if(n == 0) return result[0];
        result[0].add(null);
        
        for(int len = 1; len <= n; len++) {
            result[len] = new ArrayList<TreeNode>();
            for(int i = 0; i < len; i++) {
                int j = len - i - 1;
                for(TreeNode nodeL : result[i]) {
                    for(TreeNode nodeR : result[j]) {
                        TreeNode root = new TreeNode(i + 1);
                        root.left = nodeL;
                        root.right = clone(nodeR, i + 1);
                        result[len].add(root);
                    }
                }
            }
        }
        return result[n];
        
    }
    
    private TreeNode clone(TreeNode n, int offset) {
    if (n == null) {
        return null;
    }
    TreeNode node = new TreeNode(n.val + offset);
    node.left = clone(n.left, offset);
    node.right = clone(n.right, offset);
    return node;
    }
    
    /**
     * DP @474
     * Simple Knapsack problem
     * create an m * n table, with index (i, j) represents using i "0" and j "1";
     * given a string str with m' 0 and n' 1, it checks dp[m - n'][n - n'] to get its max count 
     * 
     * @param strs
     * @param m
     * @param n
     * @return
     */
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int[] count = count(str);
            for (int i = m; i >= count[0]; i--) {
                for (int j = n; j >= count[1]; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - count[0]][j - count[1]] + 1);
                }
            }
        }
        return dp[m][n];
    }
    
    public int[] count(String str) {
        int[] res = new int[2];
        for (int i=0;i<str.length();i++)
            res[str.charAt(i) - '0']++;
        return res;
    }
    
    /**
     * dp @516
     * 
     * @param s
     * @return
     */
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n + 1];
        for(int i = 0; i < n; i++) {
            dp[i][i + 1] = 1;
        }
        
        for(int i = 2; i <= n; i++) { // length
            for (int j = 0; j < n - i + 1; j++) {
                // j, i - j
                if(s.charAt(j) == s.charAt(i + j - 1)) {
                    dp[j][j + i] = dp[j + 1][i + j - 1] + 2;
                } else {
                    dp[j][j + i] = Math.max(dp[j + 1][j + i], dp[j][j + i - 1]);
                }
            }
        }
        return dp[0][n];
        
    }
    
    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.sort(nums);
        int maxIdx = 0, maxVal = 0;
        for (int i = 1; i < n; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                    if (dp[i] > maxVal) {
                        maxVal = dp[i];
                        maxIdx = i;
                    }
                }
            }
        }
        for (int i = 0; i <= maxIdx; i++) {
            if (nums[maxIdx] % nums[i] == 0) ret.add(nums[i]);
        }
        return ret;
    }
    
    Random rand = new Random();
    
    int s = rand.nextInt();
    
    public boolean checkInclusion(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        if(m > n) return false;
        int[] counter = new int[26];
        for(int i = 0; i < m; i++) {
            counter[s1.charAt(i) - 'a']++;
        }
        if(allZero(counter)) return true;
        for(int i = 0; i < n - m + 1; i++) {
            int[] dup = counter.clone();
            for(int j = 0; j < m; j++) {
                dup[s2.charAt(i + j) - 'a']--;
            }
            if(allZero(counter)) return true;
        }
        return false;
    }
    
    public boolean allZero(int[] arr) {
        for (int i : arr) {
            if (i != 0) return false;
        }
        return true;
    }
    
    public int[] findPermutation(String s) {
        int n = s.length(), min = Integer.MAX_VALUE;
        int[] result = new int[n];
        for(int i = 0; i < n; i++) result[i] = i + 1;
        
        for(int h = 0; h < n; h++) {
            if(s.charAt(h) == 'D') {
                int l = h;
                while(h < n && s.charAt(h) == 'D') h++;
                reverse(result, l, h);
            }
        }
        return result;
        
    }
    
    public void reverse(int[] nums, int l, int h) {
        while(l < h) {
            nums[l] ^= nums[h];
            nums[h] ^= nums[l];
            nums[l] ^= nums[h];
            l++; h--;
        }
    }
    
    public String addBoldTag(String s, String[] dict) {
        boolean bold[] = new boolean[s.length()];
        for(int i = 0, high = 0; i < s.length(); i++){
            for(String word : dict) {
                if(s.startsWith(word, i)) high = Math.max(high, i + word.length());
            }
            bold[i] = high > i;
        }
        // construct
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bold.length; i++) {
            if(!bold[i]) {
                sb.append(s.charAt(i));
                continue;
            }
            int end = i;
            while(bold[++end]);
            sb.append("<b>" + s.substring(i, end) + "</b>");
            i = end - 1;
        }
        return sb.toString();
    }
    
    HashMap<Integer, Integer> counter = new HashMap<>();
    public int[] findFrequentTreeSum(TreeNode root) {
        find(root);
        int max = 0;
        for(Map.Entry entry : counter.entrySet()) max = Math.max((int)entry.getValue(), max);
        
        List<Integer> ret = new ArrayList<>();
        for(Map.Entry entry : counter.entrySet()) {
            if((int)entry.getValue() == max) ret.add((int)entry.getKey());
        }
        int[] sln = new int[ret.size()];
        for(int i = 0; i < sln.length; i++) {
            sln[i] = ret.get(i);
        }
        return sln;
    }
    
    public int find(TreeNode root) {
        if(root == null) return 0;
        int left = find(root.left);
        int right = find(root.right);
        int val = left + right + root.val;
        counter.put(val, counter.getOrDefault(val, 0) + 1);
        return val;
    }
    
    public int maxDistance(int[][] arrays) {
        if (arrays == null || arrays.length <= 1) return 0;
        int idxmin1 = 0, idxmin2 = 0, idxmax1 = 0, idxmax2 = 0;
        int min1 = arrays[0][0], min2 = min1, max1 = arrays[0][arrays[0].length - 1], max2 = max1;
        int m = arrays.length;
        for(int i = 0; i < m; i++) {
            int n = arrays[i].length;
            if(min1 > arrays[i][0]) {
                min2 = min1;
                min1 = arrays[i][0];
                idxmin2 = idxmin1;
                idxmin1 = i;
            }
            if(max1 < arrays[i][n - 1]) {
                max2 = max1;
                max1 = arrays[i][n - 1];
                idxmax2 = idxmax1;
                idxmax1 = i;
            }
        }
        if(idxmin1 == idxmax1) {
            int n1 = arrays[idxmin1].length;
            int n2 = arrays[idxmin2].length;
            int n3 = arrays[idxmax1].length;
            int n4 = arrays[idxmax2].length;
            return Math.max(Math.abs(arrays[idxmin1][0] - arrays[idxmax2][n4 - 1]), 
                            Math.abs(arrays[idxmin2][0] - arrays[idxmax1][n3 - 1]));
        }
        return Math.abs(arrays[idxmin1][0] - arrays[idxmax1][arrays[idxmax1].length - 1]);
        
    }
    
    List<Integer> nums;
    public TreeNode reshape(TreeNode root) {
        dfs(root);
        nums.sort(new Comparator<Integer>(){
        	public int compare(Integer a, Integer b){
        		return a - b;
        	}
        });
        int[] sorted = new int[nums.size()];
        for(int i = 0; i < sorted.length; i++) sorted[i] = nums.get(i);
        TreeNode newRoot = buildBST(root, sorted);
        return newRoot;
    }
    
    
    public TreeNode buildBST(TreeNode root, int[] nums) {
        int left = numNodes(root.left);
        TreeNode root1 = new TreeNode(nums[left]);
        root1.left = buildBST(root.left, Arrays.copyOfRange(nums, 0, left));
        root1.right = buildBST(root.right, Arrays.copyOfRange(nums, left + 1, nums.length));
        return root1;
    }
    
    public int numNodes(TreeNode node){
        if(node == null) return 0;
        return 1 + numNodes(node.left) + numNodes(node.right);
    }

    public void dfs(TreeNode node) {
        if(node == null) return;
        nums.add(node.val);
        dfs(node.left);
        dfs(node.right);        
    }
    
}
