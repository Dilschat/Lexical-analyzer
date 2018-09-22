/**
 * Created by niyaz on 21.09.2018.
 */
public class DelimiterUtils {
    private static String delimiterPattern = "[;,.]{}(): ";


    public static Token processDelimiter(char c) {
        return new Token(c, Token.DELIMITER);
    }

    public static boolean isDelimiter(char c) {
        return delimiterPattern.contains(Character.toString(c));
    }

}
