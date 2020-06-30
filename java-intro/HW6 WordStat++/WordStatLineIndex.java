import java.io.*;
import java.util.*;
import java.lang.*;
public class WordStatLineIndex {
    public static void main(String[] args) {
		Map<String, ArrayList<Integer> > treeMap = new TreeMap<>();
		int str = 1, idx = 1;
		try {b                     ]
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
			int c = 0;
			while (true) {
				while (!(Character.isLetter(c) || c == '\'' || Character.DASH_PUNCTUATION == Character.getType(c) || c == -1)) {
					if (c == '\n' || c == '\r') {
						for (int i = 0; i < System.lineSeparator().length(); i++) {
							c = in.read();
						}
						str++;
						idx = 1;
					} else {
						c = in.read();
					}
				}
				if (c == -1) {
					break;
				}
				StringBuilder stringBuilder = new StringBuilder();
				while (Character.isLetter(c) || c == '\'' || Character.DASH_PUNCTUATION == Character.getType(c)) {
					stringBuilder.append((char)c);
					c = in.read();
				}
				if (!treeMap.containsKey(stringBuilder.toString().toLowerCase())) {
					treeMap.put(stringBuilder.toString().toLowerCase(), new ArrayList<>());
				}
				treeMap.get(stringBuilder.toString().toLowerCase()).add(str);
				treeMap.get(stringBuilder.toString().toLowerCase()).add(idx++);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("Input file not found: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Input file error: " + e.getMessage());
		}
		try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
			for (Map.Entry<String, ArrayList<Integer> > temp: treeMap.entrySet()) {
				out.write(temp.getKey() + " " + temp.getValue().size() / 2);
				for (int i = 0; i < temp.getValue().size() - 1; i += 2){
					out.write(" " + temp.getValue().get(i) + ":" + temp.getValue().get(i + 1));
				}
				out.write("\n");
			}
			out.close();
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't create output file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Output file error: " + e.getMessage());
        }
    }
}


