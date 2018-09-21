import java.util.Scanner;

/**
 * Created by niyaz on 21.09.2018.
 */
public class StringLiteralUtils {

    public static boolean isBeginningStringLiteral(char startLine) {
        return startLine == '\"';
    }

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

    public static boolean isMultilineStringLiteral(int index, String currentLine) {
        return index < currentLine.length() && currentLine.charAt(index) == '\"' &&
                index+1 < currentLine.length() && currentLine.charAt(index+1) == '\"' &&
                index+2 < currentLine.length() && currentLine.charAt(index+2) == '\"';
    }

    public static Token processMultilineString(String currentLine, Scanner scanner) throws Exception {
        int index = 3;
        String currentTokenBuffer="\"\"\"";
        while(!isMultilineStringLiteral(index, currentLine)){
            if(index == currentLine.length()){
                currentTokenBuffer+="\n";
                if (! scanner.hasNext()){
                    throw new Exception("Unclosed multiline string");
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
