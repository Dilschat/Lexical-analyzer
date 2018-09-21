//import sun.jvm.hotspot.ui.tree.SimpleTreeGroupNode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
    private static String delimiterPattern = "[;|,|.]{}(): "; // а как же {} ()
    //TODO add synatic noise
    private static List<String> operators =
            Arrays.asList("+", "-", "*", "/", "%",
                    "==", "<=", ">=", "!=", ">", "<", "!",
                    "||", "&&", "&", "|", "~", "^", ">>>",
                    "<<", ">>", "=", "+=", "-=", "*=", "/=",
                    "%=", "<<=", ">>=", "^=", "|=", "&=");
    private static HashSet<String> operatorsSet = new HashSet<String>(operators);

    private static List<Character> printableEscapeCharacters =
            Arrays.asList('\b', '\t', '\n', '\f', '\r', '\"', '\'', '\\');
    private static HashSet<Character> printableEscapeCharactersSet =
            new HashSet<Character>(printableEscapeCharacters); // or '\777'
    //A character with Unicode between 0 and 255 may also be represented by an octal escape,
    // i.e. a backslash '\' followed by a sequence of up to three octal characters.

    private String currentLine;
    private Scanner scanner;
    private boolean hasNext = true;

    public Tokenizer(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(fileName));
        currentLine="";
    }

    public boolean hasNext(){
        return hasNext;
    }

    private void obrubatel(Token token){
        currentLine = currentLine.substring(token.getElement().length(), currentLine.length());
    }

    //Draft impl. TODO finish according patterns above.

    public Token getNextToken() throws Exception {
        if (currentLine.length() == 0) {
            if (! scanner.hasNext()){
                hasNext = false;
                return new Token("\\n", Token.DELIMITER);
            } else {
                currentLine = scanner.nextLine();
                return new Token("\\n", Token.DELIMITER);
            }
        }
        Token token = processToken();
        obrubatel(token);
        while (token.getElement().equals(" ")){
            token = getNextToken();
            obrubatel(token);
        }
        return token;
    }

    //TODO: handle whitespaces
    private Token processToken() throws Exception {
        if (currentLine.length() > 0){
            if(isDelimiter()){
                return processDelimiter();
            } else if (isMultilineStringLiteral(0)) {
                return processMultilineString();
            } else if (isBeginningStringLiteral(currentLine.charAt(0))){
                return processStringLiteral(currentLine);
            } else if (isOperator(Character.toString(currentLine.charAt(0)))) {
                return processOperator(currentLine);
            } else if (isIdentifier(Character.toString(currentLine.charAt(0)))) {
                return processIdentifier(currentLine);
            } else if (NumericalTypeUtils.isNumberLiteral(currentLine)) {
                return NumericalTypeUtils.processNumericLiteral("",currentLine,0);
            } else if (CharacterTypeUtils.isCharacter(currentLine)) {
                return CharacterTypeUtils.processCharacter(currentLine,0);
            } else {

                    throw new Exception("Huinyu napisali");

            }
        }
        return null;
    }

    private Token processDelimiter() {
        return new Token(currentLine.charAt(0), Token.DELIMITER);
    }

    private boolean isDelimiter() {
        return delimiterPattern.contains(Character.toString(currentLine.charAt(0)));
    }

    private Token processOperator(String currentLine) {
        String currentTokenBuffer = Character.toString(currentLine.charAt(0));
        int index = 1;
        if (isOperator(currentLine.substring(0,2))){
            currentTokenBuffer = currentLine.substring(0,2);
            if(isOperator(currentLine.substring(0,3))){
                currentTokenBuffer = currentLine.substring(0,3);
            }
        }
        return new Token(currentTokenBuffer, Token.OPERATOR);
    }

    private boolean isOperator(String operator)  {
        return operatorsSet.contains(operator);
    }

    private boolean isKeyword(String identifier) {
        return keywordsSet.contains(identifier);
    }




    private boolean isMultilineStringLiteral(int index) {
        return index < currentLine.length() && currentLine.charAt(index) == '\"' &&
                index+1 < currentLine.length() && currentLine.charAt(index+1) == '\"' &&
                index+2 < currentLine.length() && currentLine.charAt(index+2) == '\"';
    }

    private Token processMultilineString() throws Exception {
        int index = 3;
        String currentTokenBuffer="";
        while(!isMultilineStringLiteral(index)){
            if(index == currentLine.length()){
                currentTokenBuffer+="\n";
                if (! scanner.hasNext()){
                    throw new Exception("Unclosed multiline string");
                } else {
                    currentLine = scanner.nextLine();
                    index = 0;
                }
            } else {
                currentTokenBuffer += currentLine.charAt(index);
                index++;
            }
        }
        return new Token(currentTokenBuffer, Token.LITERAL_MULTILINE_STRING);
    }

    public boolean isBeginningStringLiteral(char startLine) {
        return startLine == '\"';
    }

    private Token processStringLiteral(String currentLine) throws Exception {
        String currentTokenBuffer = "";
        int index = 1;
        while(true){
            currentTokenBuffer+=currentLine.charAt(index);
            index++;
            if (currentTokenBuffer.charAt(currentTokenBuffer.length()-1) == '\"' &&
                    currentTokenBuffer.charAt(currentTokenBuffer.length()-2) != '\\') {
                break;
            }
            if (index == currentLine.length())
                throw new Exception("Unclosed string literal: " + currentTokenBuffer);
        }
        return new Token(currentTokenBuffer, Token.LITERAL_STRING);
    }



    private boolean isIdentifier(String identifier) {
        return identifier.matches(indentifyierPattern);
    }

    private Token processIdentifier(String currentLine) {
        String currentTokenBuffer = currentLine.substring(0,1);
        int index = 1;
        while (isOperator(currentTokenBuffer + currentLine.charAt(index))){
            currentTokenBuffer = currentTokenBuffer+currentLine.charAt(index);
            index++;
        }
        if (isKeyword(currentTokenBuffer)){
            return new Token(currentTokenBuffer, Token.KEYWORD);
        }
        return new Token(currentTokenBuffer, Token.IDENTIFIER);
    }
}
