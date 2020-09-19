package xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {

    private enum Mode {
        DEFAULT,
        CONTENT,
        TAG_STRING,
        TAG_START,
        TAG_END,
        TAG_ARGS,
    }

    Deque<Field> tags;

    public static void main(String[] args) throws Exception {
        XMLParser parser = new XMLParser();
        parser.parseRe();
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


    public void parse() throws Exception {
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
