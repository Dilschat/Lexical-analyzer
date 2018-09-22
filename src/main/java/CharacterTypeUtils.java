public class CharacterTypeUtils {

    /**
     * Checks is this line starts as character starts
     * @param line readed input for recognizing next token
     * @return is start of this line can be recognized as character
     */
    public static boolean isStartCharacter(String line){
        if(line.length()!=0) {
            return line.charAt(0) == '\'';
        }else {
            return false;
        }
    }

    /**
     *
     * @param currentLine readed input for recognizing next token
     * @param index index of vharacter in given string that should be seen next
     * @return recognized token
     * @throws Exception if token can't be recognized it throws exception with message "Wrong character"
     */
    public static Token processCharacter(String currentLine,int index) throws Exception {
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
        if(currentTokenBuffer.length()>1){
            throw new Exception("wrong character");
        }else {
            if(Character.isDefined(currentTokenBuffer.charAt(0))){
                index++;
                return new Token("\'"+currentTokenBuffer+"\'",Token.LITERAL_CHARACTER);
            }
            else {
                throw new Exception("wrong character");

            }
        }

    }
}
