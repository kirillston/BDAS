import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class ObfuscatorTests {

    @Test
    public void obfuscationWithDifferentAlphabets() throws IOException{
        Obfuscator obfuscator = new Obfuscator("src/test/resources/alphabet.txt", "src/test/resources/secret_alphabet.txt");
        String string = "Hello obfuscator tests";
        String new_string = obfuscator.obfuscate(string);
        assertNotEquals(string, new_string, "Strings must be different");
    }

    @Test
    public void obfuscationWithOneAlphabet() throws IOException{
        Obfuscator obfuscator = new Obfuscator("src/test/resources/alphabet.txt", "src/test/resources/alphabet.txt");
        String string = "Hello some new obfuscator tests";
        String new_string = obfuscator.obfuscate(string);
        assertEquals(string, new_string, "Strings must be same");
    }

    @Test
    public void obfuscationAndUnobfuscation() throws IOException{
        Obfuscator obfuscator = new Obfuscator("src/test/resources/alphabet.txt",
                "src/test/resources/secret_alphabet.txt");
        Obfuscator unobfuscator = new Obfuscator("src/test/resources/secret_alphabet.txt",
                "src/test/resources/alphabet.txt");
        String string = "Hi I am absolutely unmodified";
        assertEquals(string, unobfuscator.obfuscate(obfuscator.obfuscate(string)),
                "Obfuscation and unobfuscation should give same string");
    }

    @Test
    public void alphabetsOfDifferentLength() {
        assertThrows(IOException.class, () -> new Obfuscator("src/test/resources/short_alphabet.txt",
                "src/test/resources/long_alphabet.txt"));
    }

    @Test
    public void alphabetWithRepetitiveSymbols() {
        assertThrows(IOException.class, () -> new Obfuscator("src/test/resources/alphabet.txt",
                "src/test/resources/repetitive_alphabet.txt"));
    }
}
