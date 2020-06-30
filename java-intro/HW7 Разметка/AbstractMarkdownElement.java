package markup;

import java.util.*;

public abstract class AbstractMarkdownElement implements TextElement {
    private List<TextElement> textElements;
    private String leftModifier;
	private String rightModifier;
    private String markdownModifier;

    protected AbstractMarkdownElement(List<TextElement> textElements, String markdownModifier, String leftModifier, String rightModifier) {
        this.textElements = textElements;
        this.markdownModifier = markdownModifier;
        this.leftModifier = leftModifier;
		this.rightModifier = rightModifier;
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        s.append(markdownModifier);
        for (final TextElement textElement : textElements) {
            textElement.toMarkdown(s);
        }
        s.append(markdownModifier);
    }

    @Override
    public void toHtml(StringBuilder s) {
        s.append(leftModifier);
        for (final TextElement textElement : textElements) {
            textElement.toHtml(s);
        }
        s.append(rightModifier);
    }


}