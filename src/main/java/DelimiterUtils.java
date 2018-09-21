/**
 * Created by niyaz on 21.09.2018.
 */
public class DelimiterUtils {
    private static String delimiterPattern = "[;,.]{}(): ";


    public static Token processDelimiter(String currentLine) {
        return new Token(currentLine.charAt(0), Token.DELIMITER);
    }

    public static boolean isDelimiter(String currentLine) {
        return delimiterPattern.contains(Character.toString(currentLine.charAt(0)));
    }

}
