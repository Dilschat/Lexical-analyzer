import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by niyaz on 20.09.2018.
 */
public class FilesTokenizer {

    private String[] fileNames;

    public FilesTokenizer(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public void tokenizeFiles(){
        Token token;
        Tokenizer tokenizer;
        for (int i = 0; i < fileNames.length; i++) {
            try {
                tokenizer = new Tokenizer(fileNames[0]);
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(fileNames[i].split("\\.")[0] + "_tokinized.txt");

                    while(tokenizer.hasNext()){
                        try {
                            token = tokenizer.getNextToken();
                            writer.print(token.toString());
                            if (token.getElement().equals("\\n")) {
                                writer.println();
                            }
                        } catch (Exception e) {
                            System.out.println("Error while parsing " + fileNames[i] + ":\n" + e.getMessage());
                        }
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("Cannot write to file" + fileNames[i] + "_tokinized.txt");
                } finally {
                    writer.close();
                }
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find file " + fileNames[i]);
            }
        }
    }
}
