//import sun.jvm.hotspot.ui.tree.SimpleTreeGroupNode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Tokenizer {
    private static List<Character> printableEscapeCharacters =
            Arrays.asList('\b', '\t', '\n', '\f', '\r', '\"', '\'', '\\');
    private static HashSet<Character> printableEscapeCharactersSet =
            new HashSet<Character>(printableEscapeCharacters); // or '\777'
    //A character with Unicode between 0 and 255 may also be represented by an octal escape,
    // i.e. a backslash '\' followed by a sequence of up to three octal characters.

    private String currentLine;
    private Scanner scanner;
    private boolean hasNext = true;

    /**
     * constructor for the tokenizer
     * @param fileName - name of the file to parse
     * @throws FileNotFoundException
     */
    public Tokenizer(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(fileName));
        if (scanner.hasNext()) {
            currentLine = scanner.nextLine();
        } else {
            hasNext = false;
        }
    }

    /**
     * tells if there is next token
     * @return true if there is next token
     */
    public boolean hasNext(){
        return hasNext;
    }

    /**
     * deletes x symbols from the current line
     * where x equals to length of the Tokens element
     * if token is a multiline string literal
     * it deletes only amout of symbols after last newline
     * @param token
     */
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

    /**
     * assumption: hasNext() is true
     * @return next Token from the input file
     * @throws Exception in case of unparsable input
     */
    public Token getNextToken() throws Exception {
        try {
            Token token = processIgnoredCode();
            if (token != null)
                return token;
            token = checkEndOfString();
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

    /**
     * deletes whitespaces and comments from the beginning of the currentLine
     * also handles multiline comments (moves scanner in that case)
     * @return newline token if needed
     * @throws Exception in case of unclosed multiline comments
     */
    private Token processIgnoredCode() throws Exception {
        boolean everyThingIsClear = false;
        while(!everyThingIsClear) {
            everyThingIsClear = true;
            //processing whitespaces
            if (currentLine.length()>0 && currentLine.charAt(0) == ' ') {
                obrubatel(1);
                everyThingIsClear = false;
            }
            //processing oneline comments
            if (currentLine.length() > 0 && currentLine.charAt(0) == '/'
                    && currentLine.length() > 1 && currentLine.charAt(1) == '/') {
                if (!scanner.hasNext()) {
                    hasNext = false;
                } else {
                    currentLine = scanner.nextLine();
                }
                return new Token("\\n", Token.DELIMITER);
            }
            //processing multiline coomments
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

    /**
     * checks if current string is processed
     * if current line is processed
     *      it checks if file is empty
     *      if file is empty
     *          there is no more token to read, sets hasNext to false
     *      and then returns newline token
     * @return newline token if current line is processed
     */
    private Token checkEndOfString() {
        if (currentLine.length() == 0) {
            if (! scanner.hasNext()) {
                hasNext = false;
            } else {
                currentLine = scanner.nextLine();
            }
            return new Token("\\n", Token.DELIMITER);
        }
        return null;
    }

    /**
     * main functions that parses tokens
     * assumption: current string is not empty
     * @return next token from the string
     * @throws Exception in case of unparsable data
     */
    private Token processToken() throws Exception {
        if (currentLine.length() > 0){
            //processing operators
            if(OperatorUtils.isSyntaxNoiseOperator(currentLine)){
                return OperatorUtils.processSyntaxNoiseOperator(currentLine);
            }
            else if (OperatorUtils.isBeginningOperator(currentLine)) {
                return OperatorUtils.processOperator(currentLine);
            }
            //processing delimiters
            else if(DelimiterUtils.isDelimiter(currentLine.charAt(0))){
                return DelimiterUtils.processDelimiter(currentLine.charAt(0));
            }
            //processing string literal
            else if (StringLiteralUtils.isMultilineStringLiteral(currentLine)) {
                return StringLiteralUtils.processMultilineString(currentLine, scanner);
            }
            else if (StringLiteralUtils.isBeginningStringLiteral(currentLine)){
                return StringLiteralUtils.processStringLiteral(currentLine);
            }
            /*
            =================
            Dilshat
            =================
             */
            //processing number literals
            else if (isNumberLiteral(currentLine)) {
                return processNumericLiteral("",currentLine,0);
            }
            else if (currentLine.charAt(0)=='\'') {
                return processCharacter(currentLine,0);
            }
            //processing identifiers
            else if (IdentifierKeywordUtils.isStartOfStrangeIdentifier(currentLine.charAt(0))){
                return IdentifierKeywordUtils.processStrangeIdentifier(currentLine);
            }
            else if (IdentifierKeywordUtils.isIdentifierStart(currentLine)) {
                return IdentifierKeywordUtils.processIdentifier(currentLine);
            }
            else {
                    throw new Exception("cann't parse: " + currentLine);
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
