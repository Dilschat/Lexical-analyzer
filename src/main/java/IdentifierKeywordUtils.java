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
    private static String indentifyierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";


    public static boolean isIdentifier(String str) {
        return str.matches(indentifyierPattern);
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
}
