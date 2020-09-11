import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;

public class HandlerTests {

    @Test
    public void missingEndTag() throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser = factory.newSAXParser();
        assertThrows(SAXException.class, () ->
            parser.parse(new File("src/test/resources/no_end_tag.xml"), new ObfuscationHandler(true)));
    }

    @Test
    public void invalidBeginning() throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser = factory.newSAXParser();
        assertThrows(SAXException.class, () ->
                parser.parse(new File("src/test/resources/invalid_beginning.xml"), new ObfuscationHandler(true)));
    }

    @Test
    public void missingAttributeValue() throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser = factory.newSAXParser();
        assertThrows(SAXException.class, () ->
                parser.parse(new File("src/test/resources/no_value_attr.xml"), new ObfuscationHandler(true)));
    }
}
