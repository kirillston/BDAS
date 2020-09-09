import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Obfuscator {
    private static String source;
    private static String target;

    public Obfuscator(String source_alphabet_file, String target_alphabet_file) {
        source = readAlphabet(source_alphabet_file);
        target = readAlphabet(target_alphabet_file);
    }

    private static String readAlphabet (String alphabet_file) {
        String return_value = "";
        try {
            File srcFile = new File(alphabet_file);
            Scanner srcReader = new Scanner(srcFile);
            while (srcReader.hasNextLine()) {
                return_value = srcReader.nextLine();
            }
            srcReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading alphabet file.");
            e.printStackTrace();
        }
        return return_value;
    }

    public String obfuscate(String s) {
        StringBuilder result= new StringBuilder();
        for (int i=0;i<s.length();i++) {
            char c=s.charAt(i);
            int index=source.indexOf(c);
            result.append(target.charAt(index));
        }
        return result.toString();
    }
}
