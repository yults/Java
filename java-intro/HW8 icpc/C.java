import java.util.Scanner;
import java.lang.*;
public class C {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int xl = 1000_000_000, yl = 1000_000_000, xr = -1000_000_000, yr = -1000_000_000;
		for (int i = 0; i < n; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int h = scanner.nextInt();
			xl = Math.min(x - h, xl);
			xr = Math.max(x + h, xr);
			yl = Math.min(y - h, yl);
			yr = Math.max(y + h, yr);
		}
		int x = (xl + xr) / 2;
		int y = (yl + yr) / 2;
		int h = (int)Math.ceil((double)Math.max(xr - xl, yr - yl) / 2);
		System.out.println(x + " " + y + " " + h);
	}
}
