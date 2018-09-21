import java.util.Objects;

public class Token {
    private String element;
    private String type;

    public Token(String element, String type) {
        this.element = element;
        this.type = type;
    }

    public Token(Character element, String type) {
        this.element = element.toString();
        this.type = type;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final String DELIMITER = "Delimiter";
    public static final String KEYWORD = "Keyword";
    public static final String IDENTIFIER = "Identifier";
    public static final String OPERATOR = "Operator";
    //public static final String LITERAL = "Literal";
    public static final String LITERAL_CHARACTER = "Character literal";
    public static final String LITERAL_STRING = "String literal";
    public static final String LITERAL_MULTILINE_STRING = "Multiline string literal";
    public static final String LITERAL_NUMERIC = "Numeric literal";

    public String toString(){
        return "(" + type + "," + element + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(element, token.element) &&
                Objects.equals(type, token.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(element, type);
    }
}
