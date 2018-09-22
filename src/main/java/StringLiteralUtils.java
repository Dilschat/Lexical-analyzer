import java.util.Scanner;

/**
 * Created by niyaz on 21.09.2018.
 */
public class StringLiteralUtils {

    /**
     * assumption: called after the checking for multiline tokens
     * checks if beginning of the line is an beginning of the string literal
     * @param line
     * @return
     */
    public static boolean isBeginningStringLiteral(String line) {
        return line.charAt(0) == '\"';
    }

    /**
     * processes string literal from the currentLine
     * assumption: beginning of the currentLine is an begininng of the string literal
     * @param currentLine
     * @return string literal token
     * @throws Exception in case of unclosed string literal
     */
    public static Token processStringLiteral(String currentLine) throws Exception {
        String currentTokenBuffer = "\"";
        int index = 1;
        while(true){
            currentTokenBuffer+=currentLine.charAt(index);
            index++;
            if (currentTokenBuffer.charAt(currentTokenBuffer.length()-1) == '\"' &&
                    currentTokenBuffer.charAt(currentTokenBuffer.length()-2) != '\\') {
                break;
            }
            if (index == currentLine.length())
                throw new Exception("Unclosed string literal: " + currentTokenBuffer);
        }
        return new Token(currentTokenBuffer, Token.LITERAL_STRING);
    }

    /**
     * checks if at the index position in the string three double quotes are placed
     * @param index
     * @param currentLine
     * @return
     */
    private static boolean isThreeDoubleQoutes(int index, String currentLine) {
        return index < currentLine.length() && currentLine.charAt(index) == '\"' &&
                index+1 < currentLine.length() && currentLine.charAt(index+1) == '\"' &&
                index+2 < currentLine.length() && currentLine.charAt(index+2) == '\"';
    }

    /**
     * checks if current line is an beginning of the string literal
     * @param currentLine
     * @return
     */
    public static boolean isMultilineStringLiteral( String currentLine) {
        return isThreeDoubleQoutes(0, currentLine);
    }

    /**
     * process multiline string
     * assumption: begging of the currentLine is an multiline string beggining
     * @param currentLine
     * @param scanner needs scanner to read next lines from the input file
     * @return multiline string token
     * @throws Exception in case of unclosed multiline string
     */
    public static Token processMultilineString(String currentLine, Scanner scanner) throws Exception {
        int index = 3;
        String currentTokenBuffer="\"\"\"";
        while(!isThreeDoubleQoutes(index, currentLine)){
            if(index == currentLine.length()){
                currentTokenBuffer+="\n";
                if (! scanner.hasNext()){
                    throw new Exception("Unclosed multiline string: " + currentTokenBuffer);
                } else {
                    currentLine = scanner.nextLine();
                    index = 0;
                }
            } else {
                currentTokenBuffer += currentLine.charAt(index);
                index++;
            }
        }
        currentTokenBuffer+="\"\"\"";
        return new Token(currentTokenBuffer, Token.LITERAL_MULTILINE_STRING);
    }
}
