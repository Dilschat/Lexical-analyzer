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

}
