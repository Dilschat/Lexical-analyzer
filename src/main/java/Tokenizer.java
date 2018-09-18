//import sun.jvm.hotspot.ui.tree.SimpleTreeGroupNode;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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
    private static String delimiterPattern = "[;|,|.]"; // а как же {} ()
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

    private Character currentChar;
    private String previousCharacters;

    public Tokenizer(Scanner scanner){
        sourceCode = scanner;
        scanner.useDelimiter("");
        readNextChar();
    }

    public boolean hasNext(){
        return currentChar != null;
    }

    //Draft impl. TODO finish according patterns above.
    public Token getNextToken() throws Exception {
        previousCharacters = "";
        if (isDelimiter(currentChar)){
            return processDelimiter();
        } else if(isThisStartOfOperatorOrSyntaxNoise(currentChar)){
            return processOperator();
        } else {
            readNextChar();
            return new Token(currentChar, "ne to");
        }
    }

    /**
     * ( ) [ ] { }
     * ` ' " . ; ,
     * @param character
     * @return
     */
    private boolean isDelimiter(Character character) {
        return "()[]{}`.,".contains(character.toString()); //TODO check if delimiter
    }

    private Token processDelimiter(){
        previousCharacters = currentChar.toString();
        readNextChar();
        readCharsTillValuableChar();
        return new Token(previousCharacters, Token.DELIMITER);
    }

    /**
     * all operators in scala
     * + - * / %
     * == != > < >= <=
     * && || !
     * & | ^
     * ~ << >> >>>
     * = += -= *= /= %= <<= >>= &= ^= |=
     * @param currentCHar
     * @return
     */
    //TODO: check for syntax noise or REDO
    private boolean isThisStartOfOperatorOrSyntaxNoise(Character currentCHar) {
        return "+-*/%=!><&|^~".contains(currentCHar.toString());
    }

    /**
     * all operators in scala
     * + - * / %
     * == != > < >= <=
     * && || !
     * & | ^
     * ~ << >> >>>
     * = += -= *= /= %= <<= >>= &= ^= |=
     *
     *
     * assume this functions is called only when current character is an beginning of the operator
     * @return
     * @throws Exception
     */
    private Token processOperator(){
        while(operatorsSet.contains(previousCharacters + currentChar.toString())){
            previousCharacters = previousCharacters + currentChar;
            readNextChar();
        }
        readCharsTillValuableChar();
        return new Token(previousCharacters, Token.OPERATOR);
    }


    /**
     * reads characters while current character is not whitespace
     */
    private void readCharsTillValuableChar(){
        while(currentChar!= null && currentChar == ' '){
            readNextChar();
        }
    }

    /**
     * for shortness of the code
     */
    private void readNextChar() {
        if(sourceCode.hasNext()) {
            currentChar = sourceCode.next().toCharArray()[0];
        } else {
            currentChar = null;
        }
    }


    //TODO: DECOMMENT and do them
    /*
    boolean isThisStartOfLiteral(Character character) {

    }

    boolean isThisStartOfKeywordOrIdentifier(Character character) {

    }
*/

}
