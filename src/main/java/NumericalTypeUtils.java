import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class NumericalTypeUtils {

    public static boolean isNumberLiteral(String line) {
        return !(line.length()==0) && Character.isDigit(line.charAt(0));
    }

    public static Token processNumericLiteral(String previousString, String currentLine, int indx) throws Exception {
        while (!isNumberLiteral(currentLine)){
            throw new Exception("is not beginning with numeric");
        }
        int index=indx;
        String currentTokenBuffer = previousString;
        if(currentTokenBuffer.length()==0) {
            currentTokenBuffer = Character.toString(currentLine.charAt(0));
        }
        if(index<currentLine.length()-1) {
            index++;
        }else {
            return new Token(currentTokenBuffer, Token.LITERAL_NUMERIC);
        }
        currentTokenBuffer+=currentLine.charAt(index);
        if(NumberUtils.isCreatable(currentTokenBuffer)){
            return processNumericLiteral(currentTokenBuffer,currentLine, index);
        }else {
            index++;
            currentTokenBuffer+=currentLine.charAt(index);
            if(NumberUtils.isCreatable(currentTokenBuffer)){
                return processNumericLiteral(currentTokenBuffer,currentLine,index);
            }else {
                index++;
                currentTokenBuffer+=currentLine.charAt(index);
                if(NumberUtils.isCreatable(currentTokenBuffer)){

                    return processNumericLiteral(currentTokenBuffer,currentLine, index);
                }else {
                    currentTokenBuffer=currentTokenBuffer.substring(0, currentTokenBuffer.length()-3);
                    return new Token(currentTokenBuffer, Token.LITERAL_NUMERIC);
                }


            }
        }
    }
}
