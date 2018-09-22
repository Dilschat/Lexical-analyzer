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
            Arrays.asList(":+", "::", "+:", "->", "<-", "=>");
    private static HashSet<String> syntaxNoiseOperatorsSet = new HashSet<String>(syntaxNoiseOperators);

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

    public static boolean isOperator(String operator)  {
        return operatorsSet.contains(operator);
    }

    public static boolean isSyntaxNoiseOperator(String currentLine) {
        if(currentLine.length()>=2 && syntaxNoiseOperatorsSet.contains(currentLine.substring(0,2))) {
            return true;
        }
        return false;
    }

    public static Token processSyntaxNoiseOperator(String currentLine) {
        return new Token(currentLine.substring(0,2), Token.OPERATOR_NOISE);
    }

}
