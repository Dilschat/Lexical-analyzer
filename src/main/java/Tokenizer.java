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
    private boolean hasNext = true;

    public Tokenizer(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(fileName));
        currentTokenBuffer="";
        currentLine="";
    }

    public boolean hasNext(){
        return hasNext;
    }

    private void obrubatel(){
        currentLine = currentLine.substring(index, currentLine.length());
        index = 0;
    }

    //Draft impl. TODO finish according patterns above.

    public Token getNextToken() throws Exception {
        currentTokenBuffer="";
        if (currentLine.length() == 0) {
            if (! scanner.hasNext()){
                hasNext = false;
                return new Token("\\n", Token.DELIMITER);
            } else {
                currentLine = scanner.nextLine();
                index = 0;
                return new Token("\\n", Token.DELIMITER);
            }
        }
        return processToken();
    }

    //TODO: handle whitespaces
    private Token processToken() throws Exception {
        while (index < currentLine.length()){
            currentTokenBuffer = currentTokenBuffer + currentLine.charAt(index);
            if(isDelimiter()){
                index++;
                return processDelimiter();
            } else if (isOperator()) {
                index++;
                return processOperator();
            } else if (isIdentifier()) {
                index++;
                return processIdentifier();
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




    private Token processDelimiter() {
        obrubatel();
        return new Token(currentTokenBuffer, Token.DELIMITER);
    }

    private boolean isDelimiter() {
        return delimiterPattern.contains(currentTokenBuffer);
    }

    private Token processOperator() {
        if (index < currentLine.length()){
            currentTokenBuffer = currentTokenBuffer+currentLine.charAt(index);
            index++;
            if (isOperator()) {
                return processOperator();
            }
            index--;
            currentTokenBuffer = currentTokenBuffer.substring(0, currentTokenBuffer.length()-1);
        }
        obrubatel();
        return new Token(currentTokenBuffer, Token.OPERATOR);
    }

    private boolean isOperator() {
        return operatorsSet.contains(currentTokenBuffer);
    }

    private boolean isKeyword() {
        return keywordsSet.contains(currentTokenBuffer);
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
        return currentTokenBuffer.matches(indentifyierPattern);
    }

    private Token processIdentifier(){
        if (index < currentLine.length()){
            currentTokenBuffer = currentTokenBuffer+currentLine.charAt(index);
            index++;
            if (isIdentifier()) {
                return processIdentifier();
            }
            index--;
            currentTokenBuffer = currentTokenBuffer.substring(0, currentTokenBuffer.length()-1);
        }
        obrubatel();
        if (isKeyword()){
            return new Token(currentTokenBuffer, Token.KEYWORD);
        }
        return new Token(currentTokenBuffer, Token.IDENTIFIER);
    }
}
