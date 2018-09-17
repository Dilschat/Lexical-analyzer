import sun.jvm.hotspot.ui.tree.SimpleTreeGroupNode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {

    private DataInputStream sourceCode;
    //TODO map all lists to hashtable for perfomance improving
    private static List<String> keywords = Arrays.asList("case", "catch", "class", "def", "do", "else", "extends", "false",
            "final", "for", "if", "match", "new", "null", "print", "printf", "println", "throw", "to", "trait", "true",
            "try", "until", "val", "var", "while", "with");
    private static String indentifyierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static String delimiterPattern = "[;|,|.]";
    //TODO add synatic noise
    private static List<String> operators = Arrays.asList("+", "-", "*", "/", "%", "==", "<=", ">=", "!=", ">", "<", "!", "||", "&&",
            "&", "|", "~", "^", ">>>", "<<", ">>", "=", "+=", "-=", "*=", "/=", "%=", "<<=", ">>=", "^=", "|=", "&=");
    private static List<String> literalPattern = Arrays.asList("\"[[\\s|\\S]*\"]"/*string pattern*/,
            "[0-9]*"/*number pattern*/, "[0-9]*.[0-9]*[[e][-|+][[0-9]*[d]?]]?", "\"\"\"[[\\s|\\S|\n]*]\"\"\"");

    public Tokenizer(DataInputStream stream) {
        sourceCode = stream;
    }

    //Draft impl. TODO finish according patterns above.
    public Token getNextToken() {
        String previousCharacters = "";
        Character currentCharacter = null;
        boolean completeToken = false;
        while (!completeToken) {
            try {
                currentCharacter = sourceCode.readChar();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                previousCharacters = previousCharacters.concat(currentCharacter.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (previousCharacters.matches(delimiterPattern)) {
                return new Token(previousCharacters, "delimiter");
            }


        }

    }

    boolean isThisStartOfOperatorOrSyntaxNoise(Character character) {

    }

    boolean isThisStartOfLiteral(Character character) {

    }

    boolean isThisStartOfKeywordOrIdentifier(Character character) {

    }


}
