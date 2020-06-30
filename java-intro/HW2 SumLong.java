public class SumLong {
    public static void main(String[] args) {
		long ans = 0;
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			int begin = -1;
			for (int j = 0; j < s.length(); j++) {
				if (Character.isWhitespace(s.charAt(j))) {
					if (begin != -1) {
						ans += Long.parseLong(s.substring(begin, j));
						begin = -1;
					}
				} else if (begin == -1) {
					begin = j;
				}
			}
			if (begin != -1) {
				ans += Long.parseLong(s.substring(begin, s.length()));
			}
		}
		System.out.println(ans);
    }

}
