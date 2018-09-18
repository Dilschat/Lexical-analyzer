import sun.jvm.hotspot.ui.tree.SimpleTreeGroupNode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {

    private DataInputStream sourceCode;
    //TODO map all lists to hashtable for perfomance improving
    private static List<String> keywords = Arrays.asList("case", "catch", "class",
            "def", "do", "else", "extends", "false", "final", "for", "if",
            "match", "new", "null", "print", "printf", "println", "throw",
            "to", "trait", "true", "try", "until", "val", "var", "while", "with");
    private static String indentifyierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static String delimiterPattern = "[;|,|.]"; // а как же {} ()
    //TODO add synatic noise
    private static List<String> operators =
            Arrays.asList("+", "-", "*", "/", "%",
                    "==", "<=", ">=", "!=", ">", "<", "!",
                    "||", "&&", "&", "|", "~", "^", ">>>",
                    "<<", ">>", "=", "+=", "-=", "*=", "/=",
                    "%=", "<<=", ">>=", "^=", "|=", "&=");
    private static List<String> literalPattern = Arrays.asList("\"[[\\s|\\S]*\"]"/*string pattern*/,
            "[0-9]*"/*number pattern*/, "[0-9]*.[0-9]*[[e][-|+][[0-9]*[d]?]]?", "\"\"\"[[\\s|\\S|\n]*]\"\"\"");

    private Character currentChar;
    private String previousCharacters;

    public Tokenizer(DataInputStream dataInputStream){
        sourceCode = dataInputStream;
        readNextChar();
    }


    //Draft impl. TODO finish according patterns above.
    public Token getNextToken() throws Exception {
        previousCharacters = "";
        if (isDelimiter(currentChar)){
            readNextChar();
            readCharsTillValuableChar();
            return new Token(currentChar, Token.DELIMITER);
        } else if(isThisStartOfOperatorOrSyntaxNoise(currentChar)){
            return processOperator();
        } else {
            return new Token("ne to", "ne to");
        }
    }

    private Token processOperator() throws Exception {
        if("=!+-*/%&^|".contains(currentChar.toString())){ // simple operations without  < >
            previousCharacters = currentChar.toString();
            readNextChar();
            if (currentChar.equals('=')){ // с присвоением
                previousCharacters = previousCharacters + currentChar.toString();
                readNextChar();
                readCharsTillValuableChar();
                return new Token(previousCharacters, Token.OPERATOR);
            } else{ // без присвоения
                readNextChar();
                readCharsTillValuableChar();
                return new Token(previousCharacters, Token.OPERATOR);
            }
        }


        else if("<".contains(currentChar.toString())){ // processing <
            previousCharacters = currentChar.toString();
            readNextChar();
            if (currentChar.equals('=')){ // <=
                previousCharacters = previousCharacters + currentChar.toString();
                readNextChar();
                readCharsTillValuableChar();
                return new Token(previousCharacters, Token.OPERATOR);
            } else if(currentChar.equals('<')){// <<
                previousCharacters = previousCharacters + currentChar.toString();
                readNextChar();
                readCharsTillValuableChar();
                return new Token(previousCharacters, Token.OPERATOR);
            } else { // <
                readNextChar();
                readCharsTillValuableChar();
                return new Token(previousCharacters, Token.OPERATOR);
            }
        }

        else if(">".contains(currentChar.toString())){ // proccessing >
            previousCharacters = currentChar.toString();
            readNextChar();
            if (currentChar.equals('=')){ // >=
                previousCharacters = previousCharacters + currentChar.toString();
                readNextChar();
                readCharsTillValuableChar();
                return new Token(previousCharacters, Token.OPERATOR);
            } else if(currentChar.equals('>')){// >>
                previousCharacters = previousCharacters + currentChar.toString();
                readNextChar();
                if(currentChar.equals('>')){ // >>>
                    previousCharacters = previousCharacters + currentChar.toString();
                    readNextChar();
                    readCharsTillValuableChar();
                    return new Token(previousCharacters, Token.OPERATOR);
                } else {
                    readCharsTillValuableChar();
                    return new Token(previousCharacters, Token.OPERATOR);
                }
            } else {
                readNextChar();
                readCharsTillValuableChar();
                return new Token(previousCharacters, Token.OPERATOR);
            }
        }

        else if(currentChar.equals('~')){ // processing ~
            readNextChar();
            readCharsTillValuableChar();
            return new Token(previousCharacters, Token.OPERATOR);
        }

        else{
            throw new Exception("kakayoto hyinya");
        }
    }

    private void readCharsTillValuableChar(){
        while(currentChar.equals(" ")){
            readNextChar();
        }
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
     * ( ) [ ] { }
     * ` ' " . ; ,
     * @param currentCHar
     * @return
     */
    private boolean isDelimiter(Character currentCHar) {
        return false; //TODO check if delimiter
    }

    /**
     * for shortness of the code
     */
    private void readNextChar() {
        try {
             currentChar = sourceCode.readChar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    boolean isThisStartOfLiteral(Character character) {

    }

    boolean isThisStartOfKeywordOrIdentifier(Character character) {

    }
*/

}
