package markup;

import java.util.*;

public class Text implements TextElement {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder s) {
        s.append(text);
    }

    public void toHtml(StringBuilder s) {
        s.append(text);
    }

}