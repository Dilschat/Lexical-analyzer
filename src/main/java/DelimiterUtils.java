/**
 * Created by niyaz on 21.09.2018.
 */
public class DelimiterUtils {
    private static String delimiterPattern = "[;,.]{}(): ";

    /**
     * assumption: isDelimiter(c) == true
     * @param c character to process
     * @return elimiter token
     */
    public static Token processDelimiter(char c) {
        return new Token(c, Token.DELIMITER);
    }

    /**
     * @param c character to check if it is delimiter
     * @return true if c is delimiter
     */
    public static boolean isDelimiter(char c) {
        return delimiterPattern.contains(Character.toString(c));
    }

    /**
     * checks if beginning of the string is an delimiter
     * @param str character to check if it is delimiter
     * @return
     */
    public static boolean isDelimiter(String str) {
        return delimiterPattern.contains(str.substring(0,1));
    }

}
