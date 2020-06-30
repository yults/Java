package markup;

import java.util.*;

public class Strong extends AbstractMarkdownElement {
    public Strong(List<TextElement> textElement) {
        super(textElement, "__", "<strong>", "</strong>");
    }
}