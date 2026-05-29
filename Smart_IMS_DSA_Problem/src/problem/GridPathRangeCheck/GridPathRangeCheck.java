package problem.GridPathRangeCheck;

import java.util.*;

public class GridPathRangeCheck {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();

        long[][] grid = new long[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = in.nextLong();
            }
        }

        long target = in.nextLong();

        long[][] maxDp = new long[n][m];
        long[][] minDp = new long[n][m];

        maxDp[0][0] = grid[0][0];
        minDp[0][0] = grid[0][0];

        for (int j = 1; j < m; j++) {
            maxDp[0][j] = maxDp[0][j - 1] + grid[0][j];
            minDp[0][j] = minDp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < n; i++) {
            maxDp[i][0] = maxDp[i - 1][0] + grid[i][0];
            minDp[i][0] = minDp[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                maxDp[i][j] =
                        Math.max(maxDp[i - 1][j], maxDp[i][j - 1]) + grid[i][j];

                minDp[i][j] =
                        Math.min(minDp[i - 1][j], minDp[i][j - 1]) + grid[i][j];
            }
        }

        long maxSum = maxDp[n - 1][m - 1];
        long minSum = minDp[n - 1][m - 1];

        System.out.println((target >= minSum && target <= maxSum) ? "Yes" : "No");
    }
}