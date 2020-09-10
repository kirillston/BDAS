import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Obfuscator {
    private final String source;
    private final String target;

    public Obfuscator(String source_alphabet_file, String target_alphabet_file) throws IOException{
        source = readAlphabet(source_alphabet_file);
        target = readAlphabet(target_alphabet_file);
        checkAlphabets(source, target);
    }

    private static String readAlphabet (String alphabet_file) throws FileNotFoundException{
        String return_value = "";
        try{
            File srcFile = new File(alphabet_file);
            Scanner srcReader = new Scanner(srcFile);
            return_value = srcReader.nextLine();
            srcReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("An error occurred while reading alphabet file.");
        }
        return return_value;
    }

    private static void checkAlphabets (String alpha1, String alpha2) throws IOException {
        int len1 = alpha1.length();
        int len2 = alpha2.length();
        if (len1 != len2) {
            String message = "Alphabets have different length: " + len1 + " and " + len2;
            throw new IOException(message);
        }
        checkAlphabetUniqueness(alpha1);
        checkAlphabetUniqueness(alpha2);
    }

    private static void checkAlphabetUniqueness(String alphabet) throws IOException{
        int len = alphabet.length();
        for (int i=0; i < len; i++) {
            char first_symbol = alphabet.charAt(i);
            for (int j = i + 1; j < len; j++) {
                char second_symbol = alphabet.charAt(j);
                if (first_symbol == second_symbol) {
                    String message = "Alphabet " + alphabet + " has not unique symbol: " + first_symbol;
                    throw new IOException(message);
                }
            }
        }
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
