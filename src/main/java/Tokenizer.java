//import sun.jvm.hotspot.ui.tree.SimpleTreeGroupNode;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Tokenizer {

    private Scanner sourceCode;

    //TODO map all lists to hashtable for perfomance improving
    private static List<String> keywords = Arrays.asList("case", "catch", "class",
            "def", "do", "else", "extends", "false", "final", "for", "if",
            "match", "new", "null", "print", "printf", "println", "throw",
            "to", "trait", "true", "try", "until", "val", "var", "while", "with");
    private static HashSet<String> keywordsSet = new HashSet<String>(keywords);
    private static String indentifyierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static String delimiterPattern = "[;|,|.] "; // а как же {} ()
    //TODO add synatic noise
    private static List<String> operators =
            Arrays.asList("+", "-", "*", "/", "%",
                    "==", "<=", ">=", "!=", ">", "<", "!",
                    "||", "&&", "&", "|", "~", "^", ">>>",
                    "<<", ">>", "=", "+=", "-=", "*=", "/=",
                    "%=", "<<=", ">>=", "^=", "|=", "&=");
    private static HashSet<String> operatorsSet = new HashSet<String>(operators);
    private static List<String> literalPattern = Arrays.asList("\"[[\\s|\\S]*\"]"/*string pattern*/,
            "[0-9]*"/*number pattern*/, "[0-9]*.[0-9]*[[e][-|+][[0-9]*[d]?]]?", "\"\"\"[[\\s|\\S|\n]*]\"\"\"");

    private static List<Character> printableEscapeCharacters =
            Arrays.asList('\b', '\t', '\n', '\f', '\r', '\"', '\'', '\\');
    private static HashSet<Character> printableEscapeCharactersSet =
            new HashSet<Character>(printableEscapeCharacters); // or '\777'
    //A character with Unicode between 0 and 255 may also be represented by an octal escape,
    // i.e. a backslash '\' followed by a sequence of up to three octal characters.

    private String currentLine;
    private String currentTokenBuffer;
    private int index;
    private Scanner scanner;

    public Tokenizer(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(fileName));
        currentTokenBuffer="";
        currentLine="";
    }

    public boolean hasNext(){
        return scanner.hasNext() || currentLine.length() > index;
    }

    //Draft impl. TODO finish according patterns above.

    public Token getNextToken() throws Exception {
        currentTokenBuffer="";
        if (currentLine.length() == 0) {
            currentLine = scanner.nextLine();
            index = 0;
        }
        return processToken();
    }

    private Token processToken() throws Exception {
        while (index < currentLine.length() - 1){
            currentTokenBuffer = currentTokenBuffer + currentLine.charAt(index);
            if(isDelimeter()){
                return processDelimeter();
            } else if (isOperator()) {
                return processOperator();
            } else if (isKeyword()) {
                return processKeyword();
            } else if (isIdentifier()) {

            } else if (isNumberLiteral()) {

            } else if (isStringLiteral()) {

            } else if (isCharacterLiteral()) {

            } else {
                if (index < currentLine.length()) {
                    index++;
                } else {
                    throw new Exception("Huinyu napisali");
                }
            }


        }
        return null;
    }




    private Token processDelimeter() {
        index++;
        return new Token(currentTokenBuffer, Token.DELIMITER);
    }

    private boolean isDelimeter() {
        return delimiterPattern.contains(currentTokenBuffer);
    }

    private Token processOperator() {
        if (index < currentLine.length() -1){
            index++;
            currentTokenBuffer = currentTokenBuffer+currentLine.charAt(index);
            if (isOperator())
                return processOperator();
        }
        return new Token(currentTokenBuffer, Token.OPERATOR);
    }

    private boolean isOperator() {
        return operatorsSet.contains(currentTokenBuffer);
    }

    private Token processKeyword() {
        index++;
        if(currentLine.charAt(index)==' ' || currentLine.charAt(index)=='('){
            return new Token(currentTokenBuffer, Token.KEYWORD);
        }else {
            return processIdentifier();
        }
    }

    private Token processIdentifier() {
        return null;
    }

    private boolean isKeyword() {
        return operatorsSet.contains(currentTokenBuffer);
    }

    private boolean isCharacterLiteral() {
        return false;
    }

    private boolean isStringLiteral() {
        return false;
    }

    private boolean isNumberLiteral() {
        return false;
    }

    private boolean isIdentifier() {
        return false;
    }





}
