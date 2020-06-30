package markup;

import java.util.*;

public interface TextElement {
    void toMarkdown(StringBuilder s);

    void toHtml(StringBuilder s);
}