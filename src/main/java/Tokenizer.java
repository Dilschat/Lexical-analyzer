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

    private static List<Character> printableEscapeCharacters =
            Arrays.asList('\b', '\t', '\n', '\f', '\r', '\"', '\'', '\\');
    private static HashSet<Character> printableEscapeCharactersSet =
            new HashSet<Character>(printableEscapeCharacters); // or '\777'
    //A character with Unicode between 0 and 255 may also be represented by an octal escape,
    // i.e. a backslash '\' followed by a sequence of up to three octal characters.

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
        } else if(isThisStartOfLiteral(currentChar)) {
            return processLiteral();
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

    private boolean isThisStartOfLiteral(Character character) {
        return Character.isDigit(character) || character.equals('"') || character.equals('\'');
    }

    private Token processLiteral() throws Exception {
        previousCharacters = currentChar.toString();
        
    }

    /*
    Symbol literals
symbolLiteral  ::=  ‘'’ plainid
A symbol literal 'x is a shorthand for the expression scala.Symbol("x"). Symbol is a case class, which is defined as follows.

package scala
final case class Symbol private (name: String) {
  override def toString: String = "'" + name
}
The apply method of Symbol's companion object caches weak references to Symbols, thus ensuring that identical symbol literals are equivalent with respect to reference equality.

Whitespace and Commen
     */

    /*

    boolean isThisStartOfKeywordOrIdentifier(Character character) {

    }
*/
}
