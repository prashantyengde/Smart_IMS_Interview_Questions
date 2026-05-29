package problem.GridPathRangeCheck;

import java.util.*;
public class OtimizedGridPathRangeCheck {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt();
        int[][] g = new int[n][m], mx = new int[n][m], mn = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) g[i][j] = in.nextInt();
        int t = in.nextInt();
        mx[0][0] = mn[0][0] = g[0][0];
        for (int j = 1; j < m; j++) mx[0][j] = mn[0][j] = mx[0][j-1] + g[0][j];
        for (int i = 1; i < n; i++) mx[i][0] = mn[i][0] = mx[i-1][0] + g[i][0];
        for (int i = 1; i < n; i++)
            for (int j = 1; j < m; j++) {
                mx[i][j] = Math.max(mx[i-1][j], mx[i][j-1]) + g[i][j];
                mn[i][j] = Math.min(mn[i-1][j], mn[i][j-1]) + g[i][j];
            }
        System.out.println(t >= mn[n-1][m-1] && t <= mx[n-1][m-1] ? "Yes" : "No");
    }
}