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

    public static final String DELIMITER = "Delimiter";
    public static final String KEYWORD = "Keyword";
    public static final String IDENTIFIER = "Identifier";
    public static final String OPERATOR = "Operator";

    public String toString(){
        return "(" + type + "," + element + ")";
    }

}
