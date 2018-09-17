public class Main {
    public static void main(String[] args){
        Character a = ';';
        String s;
        String pattern = "[a-zA-Z_][a-zA-Z0-9_]*";
        s = "1q_asd";
        System.out.println(s.matches(pattern));
    }

}
