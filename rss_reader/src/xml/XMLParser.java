package xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class XMLParser {

    private enum Mode {
        DEFAULT,
        CONTENT,
        TAG_STRING,
        TAG_START,
        TAG_END,
        TAG_ARGS,
    }

    private enum Mode1 {
        DEFAULT,
        TAG_START,
        TAG_END,
        KWARG,
        STRING,
        CDATA
    }

    private static final ArrayList<String> KEYWORDS = new ArrayList<>(Arrays.asList(new String[]{
            "rss", "channel", "title", "description", "language",
            "rating", "copyright", "pubDate", "lastBuildDate",
            "generator", "docs", "cloud", "ttl", "managingEditor",
            "webMaster", "skipHours", "image", "url", "link",
            "width", "height", "category", "author", "source",
            "item", "textInput", "name", "hour", "day", "guid",
            "skipDays", "comments", "enclosure", "atom:link",
            "uri", "icon", "updated", "subtitle", "entry",
            "feed"}));


    Deque<Field> tags;

    public static void main(String[] args) throws Exception {
        XMLParser parser = new XMLParser();
        parser.parse();
    }

    public void parse() throws Exception {

        FileReader reader = new FileReader(new File("temp.xml"));
        int next;
        char c = 0, prevC = 0;
        StringBuilder cData = new StringBuilder();
        StringBuilder tagName = new StringBuilder();
        StringBuilder tagKey = new StringBuilder();
        StringBuilder tagValue = new StringBuilder();
        StringBuilder content = new StringBuilder();

        Tag currentTag = null;
        Stack<Tag> stack = new Stack<>();
        Mode1 mode = Mode1.DEFAULT;

        while ( (next = reader.read()) >= 0 ) {
            c = (char) next;

            if (c == '\n') continue;

            switch (mode) {
                case DEFAULT: {
                    if (c == '<') {
                        mode = Mode1.TAG_START;
                        tagName.setLength(0);
                        tagKey.setLength(0);
                        tagValue.setLength(0);
                    } else {
                        content.append(c);
                    }
                }
                break;
                case TAG_START: {
                    if (c == '/') {
                        String con = content.toString().strip();
                        if (con.length() > 0) {
                            currentTag.content = con;
                            content.setLength(0);
                        }
                        tagName.setLength(0);
                        mode = Mode1.TAG_END;
                    } else if (c == ' ' && tagName.length() != 0) {
                        String tag = tagName.toString();
                        if (KEYWORDS.contains(tag)) {
                            currentTag = new Tag(tag);
                            tagName.setLength(0);
                            content.setLength(0);
                            stack.push(currentTag);
                            mode = Mode1.KWARG;
                        }
                    } else if (c == '>') {
                        String tag = tagName.toString();
                        if (KEYWORDS.contains(tag)) {
                            currentTag = new Tag(tag);
                            tagName.setLength(0);
                            content.setLength(0);
                            stack.push(currentTag);
                            mode = Mode1.DEFAULT;
                        }
                    } else if (c == '!') {
                        mode = Mode1.CDATA;
                    } else {
                        content.append(c);
                        tagName.append(c);
                    }
                }
                break;
                case KWARG: {
                    if (c == '"') {
                        mode = Mode1.STRING;
                    } else if (c == '>') {
                        mode = Mode1.DEFAULT;
                    } else if (c != '=') {
                        tagKey.append(c);
                    }
                }
                break;
                case STRING: {
                    if (c == '"') {
                        currentTag.setKwarg(tagKey.toString(), tagValue.toString());
                        mode = Mode1.KWARG;
                    } else {
                        tagValue.append(c);
                    }
                }
                break;
                case TAG_END: {
                    if (c == '>') {
                        String tag = tagName.toString();
                        if (KEYWORDS.contains(tag)) {
                            closeTag(stack, tag);
                            mode = Mode1.DEFAULT;
                            content.setLength(0);
                            tagName.setLength(0);
                        }
                    } else {
                        content.append(c);
                        tagName.append(c);
                    }
                }
                break;
                case CDATA: {
                    if (c == ']' && prevC == ']') {
                        currentTag.cData = cData.toString();
                        cData.setLength(0);
                        mode = Mode1.KWARG;
                    } else {
                        cData.append(c);
                    }
                }
                break;
            }
            prevC = c;

        }

        System.out.println(stack.pop());
    }

    private void closeTag(Stack stack, String name) {
        ArrayList<Tag> children = new ArrayList<>();
        Tag tag;
        while (!stack.isEmpty()) {
            tag = (Tag) stack.peek();
            if (tag.name.equals(name)) {
                Collections.reverse(children);
                tag.children = children;
                return;
            } else {
                children.add((Tag) stack.pop());
            }
        }
    }

    class Tag {
        private String name;
        private String content;
        private String cData;
        private ArrayList<Tag> children;
        private Map<String, String> kwargs;

        private Tag(String name) {
            this.name = name;
            this.content = null;
            this.children = new ArrayList<>();
            this.kwargs = new HashMap<>();
        }

        public String toString() {
            StringJoiner sj = new StringJoiner(", ");
            StringBuilder string = new StringBuilder(String.format("<%s", name));
            if (kwargs.size() > 0) {
                string.append(" ");
                kwargs.keySet().stream().forEach(n -> sj.add(
                        String.format("%s: %s", n, kwargs.get(n))));
                string.append(sj);
            }
            string.append(String.format("> %s\n", content));


            for (Tag child: children) {
                string.append(String.format("\t%s", child));
            }

            string.append(String.format("</%s>\n", name));

            return String.format("%s", string.toString());
        }

        private void setKwarg(String key, String value) {
            this.kwargs.put(key, value);
        }
    }

    public void parseRe() throws Exception {

        FileReader reader = new FileReader("temp.xml");
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = reader.read()) > 0) {
            sb.append((char) c);
        }
        reader.close();
        String data = sb.toString();

        Pattern tagPattern = Pattern.compile("(?<=<).*?(?=>)");
        Matcher matcher = tagPattern.matcher(data);

        tags = new ArrayDeque<>();

        while (matcher.find()) {
            String[] line = matcher.group().split(" ");
            String tagName = line[0];

            if (tagName.startsWith("/")) {
                endTag(tagName.substring(1, tagName.length()));
            } else {
                Field tag = new Field(tagName);
                if (line.length > 1) {
                    for (int i = 1; i < line.length; i++) {
                        String[] kwarg = line[i].split("=", 2);
                        tag.setKwarg(kwarg[0], kwarg[1]);
                    }
                }
                tags.add(tag);
            }
        }

        Field root = tags.poll();
        System.out.println(root);
    }


    private void endTag(String name) {
        ArrayList<Field> children = new ArrayList<>();
        Field tag;
        while (!tags.isEmpty()) {
            tag = tags.peekLast();
            if (tag.name.equals(name)) {
                tag.children = children;
                return;
            }
            children.add(tags.pollLast());
        }
    }


    public void parse2() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("temp.xml"));
        Mode mode = Mode.DEFAULT;

        int ch;
        char symbol = 0, oldSymbol = 0;

        StringBuilder tagName = new StringBuilder();
        StringBuilder tagKey = new StringBuilder();
        StringBuilder tagValue = new StringBuilder();
        StringBuilder content = new StringBuilder();
        Deque<Field> tagStack = new ArrayDeque<>();
        Field currentTag = null;

        while ((ch = reader.read()) > 0) {
            symbol = (char) ch;

            switch (mode) {
                case DEFAULT: {
                    switch (ch) {
                        case '<': {
                            mode = Mode.TAG_START;
                            break;
                        }
                        case '"': {
                            mode = Mode.TAG_STRING;
                            break;
                        }
                        default: content.append(symbol);
                    }
                }
                break;

                case CONTENT: {
                    switch (symbol) {
                        case '/': {
                            if (oldSymbol == '<') {
                                mode = Mode.TAG_END;
                            } else {
                                content.append(symbol);
                            }
                            break;
                        }
                        default: content.append(symbol);
                    }
                }
                break;

                case TAG_STRING:
                    switch (symbol) {
                        case '"': {
                            // Set new key-word value pair.
                            currentTag.setKwarg(tagKey.toString(), content.toString());
                            tagKey.setLength(0);
                            content.setLength(0);
                            mode = Mode.TAG_ARGS;
                            break;
                        }
                        default: content.append(symbol);
                    }
                break;

                case TAG_START: {
                    switch (symbol) {
                        case '/': {
                            if (oldSymbol == '<')
                                mode = Mode.TAG_END;
                            break;
                        }
                        case ' ': {
                            currentTag = new Field(tagName.toString());
                            mode = Mode.TAG_ARGS;
                            break;
                        }
                        case '>': {
                            if (currentTag == null)
                                // <tagName>
                                currentTag = new Field(tagName.toString());
                            tagStack.push(currentTag);
                            tagName.setLength(0);
                            currentTag = null;

                            mode = Mode.DEFAULT;
                            break;
                        }
                        default: tagName.append(symbol);
                    }
                }
                break;

                case TAG_END: {
                    switch (symbol) {
                        case '>': {
                            if (currentTag.name.equals(tagName)) {
                                tagStack.push(currentTag);
                                content.setLength(0);
                                mode = Mode.DEFAULT;
                            } else {
                                content.append(symbol);
                            }
                            break;
                        }
                        default: tagName.append(symbol);
                    }
                }
                break;

                case TAG_ARGS: {
                    switch (symbol) {
                        case '=': {
                            tagKey = new StringBuilder(content);
                            content.setLength(0);
                            break;
                        }
                        case '"': {
                            mode = Mode.TAG_STRING;
                            break;
                        }
                        case '>': {
                            mode = Mode.CONTENT;
                            break;
                        }
                        case ' ':
                            break;
                        default: content.append(symbol);
                    }
                }
                break;

            }
            oldSymbol = symbol;
        }

        reader.close();
    }



    private class Field {
        private String name;
        private String content;
        private ArrayList<Field> children;
        private Map<String, String> kwargs;

        private Field(String name) {
            this.name = name;
            this.content = null;
            this.children = new ArrayList<>();
            this.kwargs = new HashMap<>();
        }

        private void setKwarg(String key, String value) {
            this.kwargs.put(key, value);
        }

        public String toString() {
            StringJoiner sj = new StringJoiner(", ");
            StringBuilder string = new StringBuilder(String.format("<%s", name));
            if (kwargs.size() > 0) {
                string.append(" ");
                kwargs.keySet().stream().forEach(n -> sj.add(
                        String.format("%s: %s", n, kwargs.get(n))));
                string.append(sj);
            }
            string.append(">\n");

            for (Field child: children) {
                string.append(String.format("\n\t%s", child));
            }

            string.append(String.format("\n</%s>", name));

            return String.format("%s", string.toString());
        }

        public boolean isEqual(String name) {
            return this.name.equals(name);
        }

    }

}
