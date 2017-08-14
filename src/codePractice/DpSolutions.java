package codePractice;

import java.util.*;

public class DpSolutions {
	
	// 650. if enumerating from back in j, we only care the first value we encounter, which saves some calculation
	public static int minSteps(int n) {
        if(n == 1) return 0;
        if(n < 4) return n;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1] = 0;
        for(int i = 2; i < 4; i++) dp[i] = i;
        for(int i = 4; i < n + 1; i++) {
            for(int j = 1; j < i; j++) {
                if(i % j != 0) continue;
                dp[i] = Math.min(dp[i], dp[j] + i / j);
            }
        }
        
        return dp[n];
    }

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        HashMap<Integer, List<Integer>> tree = new HashMap<>();
        for(int i = 0; i < pid.size(); i++) {
            if(!tree.containsKey(pid.get(i))) tree.put(pid.get(i), new ArrayList<Integer>());
            if(!tree.containsKey(ppid.get(i))) tree.put(ppid.get(i), new ArrayList<Integer>());
            tree.get(ppid.get(i)).add(pid.get(i));
        }
        
        List<Integer> result = new ArrayList<>();
        LinkedList<Integer> buffer = new LinkedList<>();
        buffer.push(kill);
        while(!buffer.isEmpty()) {
            int child = buffer.poll();
            result.add(child);
            for(int i : tree.get(child)) {
                buffer.push(i);
            }
        }
        return result;
        // removeChildren(tree, kill);
        // return new ArrayList<Integer>(tree.keySet());
    }
    
    public int findMinDifference(List<String> timePoints) {
        int result = Integer.MAX_VALUE;
        int n = timePoints.size();
        timePoints.sort((a, b)->timeDiffCmp(a, b));
        
        for(int i = 0; i < n; i++) {
            result = Math.min(result, timeDiff(timePoints.get( (i + 1) % n ), timePoints.get(i) ));
        }
        
        return result;
    }
    
    private int timeDiffCmp(String t1, String t2) {
        return (Integer.parseInt(t1.substring(0, 2)) - Integer.parseInt(t2.substring(0, 2))) * 60
        		+ (Integer.parseInt(t1.substring(3)) - Integer.parseInt(t2.substring(3))); 
    }
    
    private int timeDiff(String t1, String t2) {
        int i = Math.abs(timeDiffCmp(t1, t2));
        return Math.min(i, 1440 - i);
    }
    
    public static int[] findDiagonalOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[] result = new int[matrix.length * matrix[0].length];
        int[] coord = {0, 0};
        int rowInc = 1, colInc = -1, index = 0;
        for (int i = 0; i < m + n - 1; i++) {
            while(coord[0] != -1 && coord[1] != -1 && coord[0] != m && coord[1] != n) {
                result[index++] = matrix[coord[0]][coord[1]];
                nextNumber(coord, rowInc, colInc);
            }
            nextNumber(coord, colInc, rowInc);
            // 0->row, 1->col
            if(coord[0] == 0 && coord[1] != n - 1) { coord[1]++; rowInc = 1; colInc = -1;}
            else if(coord[1] == 0 && coord[0] != m - 1) { coord[0]++; rowInc = -1; colInc = 1; } 
            else if(coord[0] == m - 1) { coord[1]++; rowInc = -1; colInc = 1; } 
            else if(coord[1] == n - 1) { coord[0]++; rowInc = 1; colInc = -1; }
        }
        return result;
    }
    
    public static void nextNumber(int[] coord, int rowInc, int colInc) {
        coord[0] += rowInc;
        coord[1] += colInc;
    }
    
    // 564. remove boxes
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return calculateScore(boxes, dp, 0, n - 1, 0);        
    }
    
    private int calculateScore(int[] boxes, int[][][] dp, int l, int r, int k) {
        if(l > r) return 0;
        if(dp[l][r][k] > 0) return dp[l][r][k];
        while(l < r && boxes[r] == boxes[r - 1]) {r--; k++;}
        
        dp[l][r][k] = calculateScore(boxes, dp, l, r - 1, 0) + (k + 1) * (k + 1);
        
        for(int i = l; i < r; i++) {
            if(boxes[i] == boxes[r]) {
                dp[l][r][k] = Math.max(dp[l][r][k], 
                                       calculateScore(boxes, dp, l, i, k + 1) + 
                                       calculateScore(boxes, dp, i + 1, r - 1, 0));
            }
        }
        
        return dp[l][r][k];
    }
    
    // 174. dungeon game
    public int calculateMinimumHP(int[][] dungeon) {
        if(dungeon == null || dungeon.length == 0) return 0;
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = dungeon[m - 1][n - 1] < 0 ? 1 - dungeon[m - 1][n - 1] : 1;
        for(int i = m - 2; i >= 0; i--) dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - dungeon[i][n - 1]);
        for(int i = n - 2; i >= 0; i--) dp[m - 1][i] = Math.max(1, dp[m - 1][i + 1] - dungeon[m - 1][i]);
        
        for(int i = m - 2; i >= 0; i--) {
            for(int j = n - 2; j >= 0; j--) {
                int next = Math.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Math.max(1, next - dungeon[i][j]);
            }
        }
        return dp[0][0];
    }
    
    // 42. trapping rain water
    public int trap(int[] height) {
        if(height == null || height.length == 0) return 0;
        int n = height.length;
        int[] leftMax  = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        rightMax[n - 1] = height[n - 1];
        for(int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
            rightMax[n - 1 - i] = Math.max(height[n - 1 - i], rightMax[n - i]);
        }
        int ans = 0;
        for(int i = 1; i < n - 1; i++) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }
    
    // a test for priority queue
    public void pqTest() {
    	PriorityQueue<Integer> pq = new PriorityQueue<>(1, (a, b)-> a - b );
    	System.out.println(String.format("[size of pq] %d", pq.size()));
    	for (int i = 0; i < 20; i++) {
    		pq.offer(i);
    	}
    	System.out.println(String.format("[size of pq] %d", pq.size()));
    	while(!pq.isEmpty()) {
    		System.out.println(pq.poll());
    	}
    }
    
    /**
     * 407 Trapping rain water II
     * the height of water in a cell is determined by its surrounding cells,
     * firstly by up, down, left, right, then another level outward, like this:
     *   3|2|3
     * 3|2|1|2|3
     * 2|1|0|1|2
     * 3|2|1|2|3
     *   3|2|3
     *   
     * 1. we want to search from outside cells towards inside cells(start searching from edges of the matrix)
     * 2. also, we want to search from lowest cells to highest cells (therefore, we need a priority queue)
     * 
     * Given a priority queue, we can start off from the outer-most lowest cells, for each operation:
     * 1) we poll the Cell with lowest height from priority queue;
     * 2) i) if its unvisited neighbor is lower than itself, add (height of self - height of neighbor) to sum,
     *    change the height of neighbor to self's height (like pushing the wall inward) and push its neighbor 
     *    to the queue;
     *    ii) if its unvisited neighbor is higher than itself, add 0 to the sum, and push its neighbor to the queue
     * 3) repeat 1) and 2) until the queue is empty;  
     *
     */
    class Cell {
        int x, y, h;
        public Cell(int _x, int _y, int _h) {
            x = _x;
            y = _y;
            h = _h;
        }
    }
    public int trapRainWater(int[][] heightMap) {
        if(heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) return 0;
        PriorityQueue<Cell> pq = new PriorityQueue<>(1, (a, b)-> a.h - b.h);
        int m = heightMap.length, n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            visited[i][0] = true;
            visited[i][n - 1] = true;
            pq.offer(new Cell(i, 0, heightMap[i][0]));
            pq.offer(new Cell(i, n - 1, heightMap[i][n - 1]));
        }
        for(int i = 0; i < n; i++) {
            visited[0][i] = true;
            visited[m - 1][i] = true;
            pq.offer(new Cell(0, i, heightMap[0][i]));
            pq.offer(new Cell(m - 1, i, heightMap[m - 1][i]));
        }
        int[] dx = {1, 0, -1, 0}; // down, right, up, left
        int[] dy = {0, 1, 0, -1};
        int sum = 0;
        while(!pq.isEmpty()) {
            Cell curCell = pq.poll();
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + curCell.x;
                int ny = dy[i] + curCell.y;
                if(nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    sum += Math.max(0, curCell.h - heightMap[nx][ny] );
                    pq.offer(new Cell(nx, ny, Math.max(curCell.h, heightMap[nx][ny])));
                }
            }
        }
        return sum;
    }
    
}

