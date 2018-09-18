import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Character a = ';';
        String s;
        String pattern = "[a-zA-Z_][a-zA-Z0-9_]*";
        s = "1q_asd";
        System.out.println(s.matches(pattern));

        printSetTests();
    }

    public static void printSetTests(){
        List<String> operators =
                Arrays.asList("+", "-", "*", "/", "%",
                        "==", "<=", ">=", "!=", ">", "<", "!",
                        "||", "&&", "&", "|", "~", "^", ">>>",
                        "<<", ">>", "=", "+=", "-=", "*=", "/=",
                        "%=", "<<=", ">>=", "^=", "|=", "&=");
        HashSet<String> set = new HashSet<String>(operators);

        List<String> seom =
                Arrays.asList("+", "-", "*", "/", "%",
                        "==", "<=", ">=", "!=", ">", "<", "!",
                        "aa", " ", "-=-=-=", "--", "45678");
        for (int i = 0; i < seom.size(); i++) {
            if(set.contains(seom.get(i))){
                System.out.println(seom.get(i) + " true");
            } else {
                System.out.println(seom.get(i) + " false");
            }
        }
    }

}
