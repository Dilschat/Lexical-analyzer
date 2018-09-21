//import sun.jvm.hotspot.ui.tree.SimpleTreeGroupNode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Tokenizer {

    private Scanner sourceCode;

    //TODO add synatic noise

    private static List<Character> printableEscapeCharacters =
            Arrays.asList('\b', '\t', '\n', '\f', '\r', '\"', '\'', '\\');
    private static HashSet<Character> printableEscapeCharactersSet =
            new HashSet<Character>(printableEscapeCharacters); // or '\777'
    //A character with Unicode between 0 and 255 may also be represented by an octal escape,
    // i.e. a backslash '\' followed by a sequence of up to three octal characters.

    private String currentLine;
    private Scanner scanner;
    private boolean hasNext = true;

    public Tokenizer(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(fileName));
        if (scanner.hasNext()) {
            currentLine = scanner.nextLine();
        } else {
            hasNext = false;
        }
    }

    public boolean hasNext(){
        return hasNext;
    }

    private void obrubatel(Token token){
        int start = token.getElement().length();
        if (token.getType().equals(Token.LITERAL_MULTILINE_STRING)){
            start = token.getElement().split("\n").length-1;
            start = token.getElement().split("\n")[start].length();
        }
        currentLine = currentLine.substring(start, currentLine.length());
    }

    private void obrubatel(int amount){
        currentLine = currentLine.substring(amount, currentLine.length());
    }

    //Draft impl. TODO finish according patterns above.

    public Token getNextToken() throws Exception {
        try {
            Token token = processIgnoredCode();
            if (token != null)
                return token;
            token = checkEndOfLine();
            if (token != null)
                return token;
            token = processToken();
            obrubatel(token);
            return token;
        } catch (Exception e) {
            hasNext = false;
            throw new Exception(e.getMessage());
        }
    }

    private Token processIgnoredCode() throws Exception {
        boolean everyThingIsClear = false;
        while(!everyThingIsClear) {
            everyThingIsClear = true;
            if (currentLine.length()>0 && currentLine.charAt(0) == ' ') {
                obrubatel(1);
                everyThingIsClear = false;
            }
            if (currentLine.length() > 0 && currentLine.charAt(0) == '/'
                    && currentLine.length() > 1 && currentLine.charAt(1) == '/') {
                if (!scanner.hasNext()) {
                    hasNext = false;
                } else {
                    currentLine = scanner.nextLine();
                }
                return new Token("\\n", Token.DELIMITER);
            }
            if (currentLine.length() > 0 && currentLine.charAt(0) == '/'
                    && currentLine.length() > 1 && currentLine.charAt(1) == '*') {
                everyThingIsClear = false;
                obrubatel(2);
                while (! (currentLine.length() > 0 && currentLine.charAt(0) == '*'
                        && currentLine.length() > 1 && currentLine.charAt(1) == '/')) {
                    if (currentLine.length() == 0) {
                        if (scanner.hasNext()) {
                            currentLine = scanner.nextLine();
                        } else {
                            throw new Exception("Unclosed multiline comment");
                        }
                    } else {
                        obrubatel(1);
                    }
                }
                obrubatel(2);
                if (currentLine.length() == 0) {
                    if (scanner.hasNext()) {
                        currentLine = scanner.nextLine();
                    } else {
                        hasNext = false;
                    }
                    return new Token("\\n", Token.DELIMITER);
                }
            }
        }
        return null;
    }

    private Token checkEndOfLine() {
        if (currentLine.length() == 0) {
            if (! scanner.hasNext()){
                hasNext = false;
                return new Token("\\n", Token.DELIMITER);
            } else {
                currentLine = scanner.nextLine();
                return new Token("\\n", Token.DELIMITER);
            }
        }
        return null;
    }

    //TODO: handle whitespaces
    private Token processToken() throws Exception {
        if (currentLine.length() > 0){
            if(DelimiterUtils.isDelimiter(currentLine)){
                return DelimiterUtils.processDelimiter(currentLine);
            } else if (StringLiteralUtils.isMultilineStringLiteral(0, currentLine)) {
                return StringLiteralUtils.processMultilineString(currentLine, scanner);
            } else if (IdentifierKeywordUtils.isStartOfStrangeIdentifier(currentLine.charAt(0))){
                return IdentifierKeywordUtils.processStrangeIdentifier(currentLine);
            } else if (StringLiteralUtils.isBeginningStringLiteral(currentLine.charAt(0))){
                return StringLiteralUtils.processStringLiteral(currentLine);
            } else if (OperatorUtils.isOperator(Character.toString(currentLine.charAt(0)))) {
                return OperatorUtils.processOperator(currentLine);
            } else if (IdentifierKeywordUtils.isIdentifier(Character.toString(currentLine.charAt(0)))) {
                return IdentifierKeywordUtils.processIdentifier(currentLine);
            } else if (isNumberLiteral(currentLine)) {
                return processNumericLiteral("",currentLine,0);
            } else if (currentLine.charAt(0)=='\'') {
                return processCharacter(currentLine,0);
            } else {
                    throw new Exception("Huinyu napisali");
            }
        }
        return null;
    }


    private Token processCharacter(String currentLine,int index) throws Exception {
        String currentTokenBuffer="";
        if(index+1<currentLine.length()) {
            index++;
        }else {
            throw new Exception("wrong character");
        }
        while (!(currentLine.charAt(index)=='\'')){
            if(index+1>=currentLine.length()){
                throw new Exception("wrong character");
            }
            if(currentLine.charAt(index+1)=='\''){

                currentTokenBuffer+=currentLine.charAt(index);
                index++;
                continue;
            }
            currentTokenBuffer+=currentLine.charAt(index);
            index++;

        }
        if(currentTokenBuffer.length()>1 && currentTokenBuffer.charAt(0)!='\\'){
            throw new Exception("wrong character");
        }else {
            if(Character.isDefined(currentTokenBuffer.charAt(0))){
                index++;
                return new Token(currentTokenBuffer,Token.LITERAL_CHARACTER);
            }
            else {
                throw new Exception("wrong character");

            }
        }

    }

    private boolean isNumberLiteral(String currentTokenBuffer) {
        return StringUtils.isNumeric(Character.toString(currentTokenBuffer.charAt(0)));
    }

    private Token processNumericLiteral(String previousString, String currentLine, int indx){
        int index=indx;
        String currentTokenBuffer = previousString;
        if(currentTokenBuffer.length()==0) {
            currentTokenBuffer = Character.toString(currentLine.charAt(0));
        }
        if(index<currentLine.length()-1) {
            index+=1;
        }else {
            index+=1;
            return new Token(currentTokenBuffer, "Numerical " + Token.LITERAL_NUMERIC);
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
                    index-=2;
                    currentTokenBuffer=currentTokenBuffer.substring(0, currentTokenBuffer.length()-3);
                    return new Token(currentTokenBuffer, "Numerical " + Token.LITERAL_NUMERIC);
                }
            }
        }
    }
}
