import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class NumericalTypeUtils {

    /**
     * Checks is this line starts as a number
     * @param line ine readed input for recognizing next token
     * @return is start of this line can be recognized as number
     */
    public static boolean isNumberLiteral(String line) {
        return !(line.length()==0) && Character.isDigit(line.charAt(0));
    }

    /**
     *
     * @param previousString already processed part of origin line
     * @param currentLine origin line for recognition
     * @param indx index of next character that should be recognized in origin line
     * @return recognized token
     * @throws Exception if token can't be recognized it throws exception with message "it is not beginning as numeric"
     */
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
