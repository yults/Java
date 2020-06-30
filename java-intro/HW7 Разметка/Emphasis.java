package markup;

import java.util.*;

public class Emphasis extends AbstractMarkdownElement {
    public Emphasis(List<TextElement> textElement) {
        super(textElement, "*", "<em>", "</em>");
    }
}