import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by niyaz on 21.09.2018.
 */
public class OperatorUtils {
    private static List<String> operators =
            Arrays.asList("+", "-", "*", "/", "%",
                    "==", "<=", ">=", "!=", ">", "<", "!",
                    "||", "&&", "&", "|", "~", "^", ">>>",
                    "<<", ">>", "=", "+=", "-=", "*=", "/=",
                    "%=", "<<=", ">>=", "^=", "|=", "&=");
    private static HashSet<String> operatorsSet = new HashSet<String>(operators);

    private static List<String> syntaxNoiseOperators =
            Arrays.asList(":+", "::", "+:", "->", "<-", "=>", "<%");
    private static HashSet<String> syntaxNoiseOperatorsSet = new HashSet<String>(syntaxNoiseOperators);

    /**
     * processes operators
     * from the beginning of the currentLine
     * assumption: first symbol of the string is an Operator
     * @param currentLine
     * @return operator token
     */
    public static Token processOperator(String currentLine) {
        String currentTokenBuffer = Character.toString(currentLine.charAt(0));
        if (currentLine.length() >= 2 && isOperator(currentLine.substring(0,2))){
            currentTokenBuffer = currentLine.substring(0,2);
            if(currentLine.length() >= 3 && isOperator(currentLine.substring(0,3))){
                currentTokenBuffer = currentLine.substring(0,3);
            }
        }
        return new Token(currentTokenBuffer, Token.OPERATOR);
    }

    /**
     * checks if str is an operator
     * @param str
     * @return
     */
    private static boolean isOperator(String str)  {
        return operatorsSet.contains(str);
    }


    /**
     * checks if string can be beginning of the operator
     * @param str
     * @return
     */
    public static boolean isBeginningOperator(String str)  {
        return operatorsSet.contains(str.substring(0,1));
    }

    /**
     * checks if beginning of the string is syntax noise operator
     * @param currentLine
     * @return
     */
    public static boolean isSyntaxNoiseOperator(String currentLine) {
        if(currentLine.length()>=2 && syntaxNoiseOperatorsSet.contains(currentLine.substring(0,2))) {
            return true;
        }
        return false;
    }

    /**
     * assumption: two symbols at the beginning are syntax noise operator
     * processes syntax noise operator
     * @param currentLine
     * @return Operator token
     */
    public static Token processSyntaxNoiseOperator(String currentLine) {
        return new Token(currentLine.substring(0,2), Token.OPERATOR_NOISE);
    }

}
