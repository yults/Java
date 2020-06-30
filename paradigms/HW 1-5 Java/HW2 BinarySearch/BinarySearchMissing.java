package search;

public class BinarySearchMissing {
    //Pre: -1 < i < a.length : a[i] <= a[i - 1]
    private static int iterativeBinSearch(int[] a, int x) {
        //Pre
        int l = -1, r = a.length;
        //Inv: (l == -1 || a[l] > x) && (r == a.length || a[r] <= x) && r - l >= 1 && r' - l' <= (r - l + 1) / 2 (O(log(a.length)))
        while (r - l > 1) {
            // Pre && Inv && r - l > 1
            int m = (l + r) / 2;
            // r - l > 1 && l < m < r
            if (a[m] <= x) {
                // Pre && Inv && a[m] <= x
                r = m;
                // l' == l -> l' == -1 || a[l'] > x
                // r' == m && l < m < r && a[m] <= x -> r' < a.length && a[r'] <= x
                // l' == l && r' == m -> r' - l' == m - l == (l + r) / 2 - l <= (r - l + 1) / 2
                // l' == l && r' == m && l < m < r -> r' - l' >= 1
            } else {
                // Pre && Inv && a[m] > x
                l = m;
                // r' == r -> r' == a.length || a[r'] <= x
                // l' == m && l < m < r -> l' >= 0 & a[l'] > x
                // l' == m && r' == r  -> r' - l' == r - (l + r) / 2 <= (r - l + 1) / 2
                // l' == m && r' == r && l < m < r -> r' - l' >= 1
            }
        }
        // r - l <= 1 && r - l >= 1 -> r - l == 1
        // r == a.length -> a.back < x
        // r < a.length -> a[r] <= x
        // r > 0 -> a[r - 1] > x
        if (r == a.length || a[r] != x) {
            // a.back < x || a[r] < x
            return -r - 1;
        }
        // a[r] == x
        return r;
    }
    // Post: (a.length == 0 || a.back < x || a[r] != x) && (r == -insertPos - 1) || (a[r] == x && 0 <= r < a.length)

    //Pre: -1 < i < a.length a[i] <= a[i - 1]
    // && (l == -1 || a[l] > x)
    // && (r == a.length || a[r] <= x)
    // && r - l >= 1
    // && r' - l' <= (r - l + 1) / 2
    private static int recursiveBinSearch(int[] a, int x, int l, int r) {
        // Pre
        if (r - l == 1) {
            // Pre && r - l == 1
            if (r == a.length || a[r] != x) { //cond
                // Pre && r - l == 1 && cond
                return -r - 1;
            }
            // Pre && r - l == 1 && !cond
            return r;
        }
        // Pre & r - l > 1
        int m = (l + r) / 2;
        // l < m < r
        if (a[m] <= x) {
            // Pre && l' == l -> l' == -1 || a[l'] > x
            // r' == m && l < m < r -> r' < a.length & a[r'] <= x
            // r' == m && l' == l && l < m < r -> r' - l' == (l + r) / 2 - l <= (r - l + 1) / 2
            // r' == m && l' == l && l < m < r -> r' - l' >= 1
            // Pre'
            return recursiveBinSearch(a, x, l, m);
        } else {
            // Pre && r' == r -> r' == a.length || a[r'] <= x
            // l' = m && l < m < r -> l' >= 0 && a[l'] > x
            // l' == m && r' == r && l < m < r -> r' - l' == r - (r + l) / 2 <= (r - l + 1) / 2
            // Pre && l' == m && r' == r && l < m < r -> r' - l' >= 1
            // Pre'
            return recursiveBinSearch(a, x, m, r);
        }
    }
    // Post: (a.length == 0 || a.back < x || a[r] != x) && (r == -insertion point - 1) || (a[r] == x && 0 <= r < a.length)

    public static void main(String[] args) {
        // Pre: args != null && (args.length == 0 || args.length == 1 || 1 < i < args.length : args[i] <= args[i - 1])
        // && -1 < i < args.length : args[i] convert to int
        int x = Integer.parseInt(args[0]);
        // -1 < i < args.length : args[i] convert to int  -> args[0] convert to int && x == (int)args[0]
        int[] a = new int[args.length - 1];
        // args != null -> args.length - 1 >= 0
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
            // -1 < i < args.length args[i] convert to int -> args[i] convert to int && a[i - 1] == (int)args[i]
            //  1 < i < args.length : args[i] <= args[i - 1] -> -1 < i < a.length : a[i] <= a[i - 1]
        }
        System.out.println(recursiveBinSearch(a, x, -1, a.length));
    }
    // Post: (result == position && a[position] <= x && a[position - 1] > x && -1 < position < a.length)
    // || (a.length == 0 || a.back < x || a[position] != x) && (result == -insertPos - 1)
}
