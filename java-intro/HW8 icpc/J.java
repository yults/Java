import java.util.*;
import java.lang.*;
public class J {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		char[][] a = new char[n][n];
		for (int i = 0; i < n; i++)
			a[i] = scanner.next().toCharArray();
		int[][] ans = new int[n][n];
		for (int i = n - 1; i > -1; i--) {
			for (int j = i + 1; j < n; j++) {
				int sum = 0;
				for (int k = i + 1; k < j; k++) {
					sum = (sum + (int)(a[i][k] - '0') * ans[k][j]) % 10;
				}
				if (sum != (int)(a[i][j] - '0')) {
					ans[i][j] = 1;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(ans[i][j]);
			}
			System.out.println();
		}
	}
}