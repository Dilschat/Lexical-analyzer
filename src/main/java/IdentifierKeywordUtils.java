import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by niyaz on 21.09.2018.
 */
public class IdentifierKeywordUtils {

    private static List<String> keywords = Arrays.asList("case", "catch", "class",
            "def", "do", "else", "extends", "false", "final", "for", "if",
            "match", "new", "null", "print", "printf", "println", "throw",
            "to", "trait", "true", "try", "until", "val", "var", "while", "with");
    private static HashSet<String> keywordsSet = new HashSet<String>(keywords);
    private static String identifierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";

    public static boolean isIdentifierStart(char c) {
        return isPrintableChar(c)
                && !(DelimiterUtils.isDelimiter(c) || Character.isDigit(c));
    }

    private static boolean isIdentifierMiddle(char c) { return isPrintableChar(c)
            && !DelimiterUtils.isDelimiter(c);}

    public static Token processIdentifier(String currentLine) {
        String currentTokenBuffer = currentLine.substring(0,1);
        int index = 1;
        while (index < currentLine.length() &&
                isIdentifierMiddle(currentLine.charAt(index)) ){
            currentTokenBuffer = currentTokenBuffer+currentLine.charAt(index);
            index++;
        }
        if (isKeyword(currentTokenBuffer)){
            return new Token(currentTokenBuffer, Token.KEYWORD);
        }
        return new Token(currentTokenBuffer, Token.IDENTIFIER);
    }

    private static boolean isKeyword(String identifier) {
        return keywordsSet.contains(identifier);
    }

    public static boolean isStartOfStrangeIdentifier(char str){
        return str == '`';
    }

    public static Token processStrangeIdentifier(String currentLine) throws Exception {
        String currentTokenBuffer = currentLine.substring(0,1);
        int index = 1;
        if (currentLine.length() > 1 && isIdentifierStart(currentLine.charAt(index))){
            currentTokenBuffer+=currentLine.charAt(index);
            index++;
        } else {
            throw new Exception("Incorrect strange identifier: " + currentTokenBuffer);
        }
        while (true){
            if (currentLine.length() > index) {
                currentTokenBuffer+=currentLine.charAt(index);
                if(currentLine.charAt(index) == '`') {
                    if (keywordsSet.contains(currentTokenBuffer.substring(1, currentTokenBuffer.length()-1))) {
                        return new Token(currentTokenBuffer, Token.IDENTIFIER);
                    } else {
                        throw  new Exception("Incorrect strange identifier: " + currentTokenBuffer);
                    }
                }
                index++;
            } else {
                throw new Exception("Incorrect strange identifier: " + currentTokenBuffer);
            }
        }
    }

    private static boolean isPrintableChar( char c ) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }
}
