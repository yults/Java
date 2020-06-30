import java.io.*;
import java.util.*;
import java.lang.*;
public class WordStatWords {
    public static void main(String[] args) {
        try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
			TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
            while (in.ready()) {
                String line = in.readLine() + "@";					
				line = line.toLowerCase();
				int begin = 0;
				for (int i = 0; i < line.length(); i++) {
					if (Character.isLetter(line.charAt(i)) || Character.DASH_PUNCTUATION == Character.getType(line.charAt(i)) || line.charAt(i) == 39) {
						begin++;
					} else if (begin != 0) {
						treeMap.put(line.substring(i - begin, i), treeMap.getOrDefault(line.substring(i - begin, i), 0) + 1);
						begin  = 0;
					}
				}	
            } 
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
			for (Map.Entry temp: treeMap.entrySet()) {
				out.write(temp.getKey() + " " + temp.getValue().toString() + "\r\n");
			}
			in.close(); 
			out.close();
        } catch (FileNotFoundException e) { 
            System.err.println("Input file not found: " + e.getMessage()); 
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
   

