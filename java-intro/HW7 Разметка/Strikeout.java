package markup;

import java.util.*;

public class Strikeout extends AbstractMarkdownElement {
    public Strikeout(List<TextElement> textElement) {
        super(textElement, "~", "<s>", "</s>");
    }
}