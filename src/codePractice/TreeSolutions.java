package codePractice;

import java.util.*;

public class TreeSolutions {
	public boolean isSymmetric(TreeNode root) {
        List<Integer> preOrder = new ArrayList<>();
        TreeNode cur = root;
        Stack<TreeNode> buffer = new Stack<>();
        while(cur != null || !buffer.empty()) {
            if(cur != null) {
                preOrder.add(cur.val);
                buffer.push(cur);
                cur = cur.left;
            } else {
                cur = buffer.pop();
                cur = cur.right;
            }
        }
        
        preOrder.remove(0);
        int m = preOrder.size() / 2, s = 0;
        while(m < preOrder.size()) {
            if(preOrder.get(m) == preOrder.get(s)) return false;
            m++; s++;
        }
        return true;
        
    }
	

    // 113. path sum II
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> carry = new ArrayList<Integer>();
        dfs(root, result, carry, sum);
        return result;
    }
    
    public void dfs(TreeNode node, List<List<Integer>> result, List<Integer> carry, int sum) {
        if(node == null) return;
        carry.add(node.val);
        sum -= node.val;
        if(sum == 0 && node.left == null && node.right == null) result.add(new ArrayList<Integer>(carry));
        dfs(node.left, result, carry, sum);
        dfs(node.right, result, carry, sum);
        carry.remove(carry.size() - 1);
    }
    
    // 329. longest increasing path in matrix
    // Note: dfs with memorization
    public int longestIncreasingPath(int[][] matrix) {
        int[][] visited = new int[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++) {
            Arrays.fill(visited[i], -1);
        }
//        int[] coord = {0, 0}; // track asc/desc, if it is desc one way, asc other ways, vice versa
        // if asc ++, if desc --, 
        int max = 0;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
            	int[] coord = {i, j};
                max = Math.max(max, dfs(visited, matrix, coord));
            }
        }
        return max;
    }
    
    public boolean movable(int[][] visited, int m, int n) {
        return m >= 0 && m < visited.length &&
               n >= 0 && n < visited[0].length;
    }
    
    public int dfs(int[][] visited, int[][] matrix, int[] coord) {
        if(!movable(visited, coord[0], coord[1])) return 0;
        if(visited[coord[0]][coord[1]] >= 0) return visited[coord[0]][coord[1]];
        int[][] move = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // down, up, right, left
        int max = 0;
        for(int i = 0; i < 4; i++) {
            int m = coord[0] + move[i][0], n = coord[1] + move[i][1];
            if(movable(visited, m, n) && matrix[m][n] > matrix[coord[0]][coord[1]]) {
            	int[] newCoord = {m, n}; 
                max = Math.max(dfs(visited, matrix, newCoord), max);
            }
        }
        visited[coord[0]][coord[1]] = max + 1;
        
        return max + 1;
    }
    
}
