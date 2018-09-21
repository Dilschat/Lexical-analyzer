public class CharacterTypeUtils {

    public static boolean isCharacter(String line){
        return line.charAt(0)=='\'';
    }


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
}
