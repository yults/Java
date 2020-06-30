package md2html;

import java.util.*;

public class TextParser {
    private StringBuilder stringBuilder;
    private final Map<String, String> map = Map.of(
            "**", "strong",
            "__", "strong",
            "*", "em",
            "_", "em",
            "--", "s",
            "`", "code",
            "++", "u");

    public TextParser(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void toHtml(StringBuilder result) {
        Map<String, ArrayList<Integer> > tagPos = new HashMap<>();
        for (String key : map.keySet()) {
            tagPos.put(key, new ArrayList<>());
        }
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == '\\') {
                i++;
                continue;
            }
            String sub = stringBuilder.substring(i, Math.min(i + 2, stringBuilder.length()));
            if (map.containsKey(sub)) {
                tagPos.get(sub).add(i++);
            } else {
                sub = sub.substring(0, 1);
                if (map.containsKey(sub)) {
                    tagPos.get(sub).add(i);
                }
            }
        }
        Map<String, Integer> lastTagPos = new HashMap<>();
        for (String key : map.keySet()) {
            lastTagPos.put(key, 0);
            if (tagPos.get(key).size() % 2 == 1) {
                tagPos.get(key).remove(tagPos.get(key).size() - 1);
            }
        }
        for (int i = 0; i < stringBuilder.length(); i++) {
            char c = stringBuilder.charAt(i);
            switch (c) {
                case '<' :
                    result.append("&lt;");
                    continue;
                case '>' :
                    result.append("&gt;");
                    continue;
                case '&' :
                    result.append("&amp;");
                    continue;
                case '\\' :
                    continue;
            }
            String mdTag = null;
            for (String tag : map.keySet()) {
                if (lastTagPos.get(tag) < tagPos.get(tag).size() && i == tagPos.get(tag).get(lastTagPos.get(tag))) {
                    mdTag = tag;
                }
            }
            if (mdTag == null) {
                result.append(c);
                continue;
            }
            boolean isOpen = lastTagPos.get(mdTag) % 2 == 0;
            result.append("<").append(isOpen ? "" : "/").append(map.get(mdTag)).append(">");
            lastTagPos.merge(mdTag, 1, Integer::sum);
            i += mdTag.length() - 1;
        }
    }
}
