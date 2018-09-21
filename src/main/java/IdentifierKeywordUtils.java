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

    public static boolean isIdentifier(String str) {
        return str.matches(identifierPattern);
    }

    public static Token processIdentifier(String currentLine) {
        String currentTokenBuffer = currentLine.substring(0,1);
        int index = 1;
        while (index < currentLine.length() && isIdentifier(currentTokenBuffer + currentLine.charAt(index)) ){
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
        if (currentLine.length() > 1 && currentLine.substring(1,2).matches("[a-zA-Z_]")){
            currentTokenBuffer+=currentLine.substring(1,2);
        } else {
            throw new Exception("Incorrect strange identifier");
        }
        int index = 2;
        while (true){
            if (currentLine.length() > index) {
                currentTokenBuffer+=currentLine.charAt(index);
                if(currentLine.charAt(index) == '`') {
                    return new Token(currentTokenBuffer, Token.IDENTIFIER);
                }
                index++;
            } else {
                throw new Exception("Incorrect strange identifier");
            }
        }
    }
}
