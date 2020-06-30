package md2html;

import java.io.*;

public class Md2Html {
    public static void main(String[] args) {
        StringBuilder ans = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"))) {
            StringBuilder paragraph = new StringBuilder();
            while (true) {
                String s = reader.readLine();
                if (s == null || s.isEmpty()) {
                    if (paragraph.length() != 0 ) {
                        paragraph.setLength(paragraph.length() - 1);
                        int level = 0;
                        while (level < paragraph.length() && paragraph.charAt(level) == '#') {
                            level++;
                        }
                        if (level > 0 && level != paragraph.length() && paragraph.charAt(level) == ' ') {
                            ans.append("<h" + level + ">");
                            new TextParser(new StringBuilder(paragraph.substring(level + 1))).toHtml(ans);
                            ans.append("</h" + level + ">");
                        } else {
                            ans.append("<p>");
                            new TextParser(paragraph).toHtml(ans);
                            ans.append("</p>");
                        }
                        ans.append("\n");
                        paragraph.setLength(0);
                    }
                    if (s == null) {
                        break;
                    }
                } else {
                    paragraph.append(s).append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Input file Error: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"))) {
            writer.write(ans.toString());
        } catch (FileNotFoundException e) {
            System.err.println("Can't create output file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Output file error: " + e.getMessage());
        }
    }
}
