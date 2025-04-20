// Time Complexity: O(N!), where N is the size of the board
//   - In the worst case, we try N options at each row, leading to factorial time
// Space Complexity: O(N^2) for the board + O(N) for the recursion stack

///We use backtracking to place queens row by row on an n x n board such that no two queens attack each other.
// For each row, we try placing a queen in all columns and recursively continue to the next row only if the
// placement is valid.We use a helper function to validate column and diagonal conflicts and build the board
// string configuration when all queens are placed.
class Solution {
    List<List<String>> result;

    public List<List<String>> solveNQueens(int n) {
        this.result = new ArrayList<>();
        boolean[][] board = new boolean[n][n]; // N x N chessboard
        helper(board, 0, n);
        return result;
    }

    private void helper(boolean[][] board, int row, int n) {
        // Base case: All rows have been filled with valid queen placements
        if (row == n) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(board[i][j] ? "Q" : ".");
                }
                list.add(sb.toString());
            }
            result.add(list);
            return;
        }

        // Try placing a queen in every column of the current row
        for (int j = 0; j < n; j++) {
            if (isValid(board, row, j, n)) {
                board[row][j] = true;           // Choose
                helper(board, row + 1, n);      // Recurse
                board[row][j] = false;          // Backtrack
            }
        }
    }

    private boolean isValid(boolean[][] board, int i, int j, int n) {
        int r = i, c = j;

        // Check vertically up
        while (r >= 0) {
            if (board[r][c]) return false;
            r--;
        }

        r = i; c = j;
        // Check diagonal up-left
        while (r >= 0 && c >= 0) {
            if (board[r][c]) return false;
            r--; c--;
        }

        r = i; c = j;
        // Check diagonal up-right
        while (r >= 0 && c < n) {
            if (board[r][c]) return false;
            r--; c++;
        }

        return true;
    }
}

// Time Complexity: O(m * n * 4^L), where
//   - m = number of rows, n = number of columns
//   - L = length of the word
//   - Each cell can branch into 4 directions up to L times
// Space Complexity: O(L), for the recursion stack in the worst case (word of length L)

class Solution {
    int[][] dirs;
    int m, n;

    public boolean exist(char[][] board, String word) {
        this.dirs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        this.m = board.length;
        this.n = board[0].length;

        // Try to start DFS from every cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, i, j, word, 0)) return true;
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int idx) {
        // Base case: entire word matched
        if (idx == word.length()) return true;

        // Out of bounds or already visited or character mismatch
        if (i < 0 || j < 0 || i == m || j == n || board[i][j] == '#' || board[i][j] != word.charAt(idx)) {
            return false;
        }

        // Mark cell as visited
        char temp = board[i][j];
        board[i][j] = '#';

        // Explore all 4 directions
        for (int[] dir : dirs) {
            int r = i + dir[0];
            int c = j + dir[1];
            if (dfs(board, r, c, word, idx + 1)) return true;
        }

        // Backtrack
        board[i][j] = temp;
        return false;
    }
}
