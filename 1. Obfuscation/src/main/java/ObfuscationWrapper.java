import java.io.File;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

public class ObfuscationWrapper {

    private static final String USAGE = "Usage: java ObfuscationWrapper [obfuscate/unobfuscate] [filename]";

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                System.err.println (USAGE);
                System.exit (1);
            }
            boolean obfuscate = true;
            if (args[0].equals("obfuscate")) {
                obfuscate = true;
            } else if (args[0].equals("unobfuscate")) {
                obfuscate = false;
            } else {
                System.err.println (USAGE);
                System.exit (1);
            }

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(false);
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(args[1]), new ObfuscationHandler(obfuscate));
        } catch (ParserConfigurationException e) {
            System.err.println("The underlying parser does not support the requested features.");
        } catch (FactoryConfigurationError e) {
            System.err.println("Error occurred obtaining SAX Parser Factory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}