import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

class ObfuscationHandler extends DefaultHandler {

    private final Writer out;
    private final Obfuscator obfuscator;
    private static FileWriter fileWriter;
    private static XMLStreamWriter xMLStreamWriter;

    private static String filename;

    public ObfuscationHandler(boolean obfuscate) {
        out = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        try {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date(System.currentTimeMillis());
            filename = "src/main/resources/output-" + formatter.format(date) + ".xml";
            fileWriter = new FileWriter(filename);
            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
            xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(fileWriter);
        } catch (XMLStreamException | IOException exception) {
            exception.printStackTrace();
        }
        if (obfuscate) {
            obfuscator = new Obfuscator("src/main/resources/alphabet.txt",
                    "src/main/resources/secret_alphabet.txt");
        } else {
            obfuscator = new Obfuscator("src/main/resources/secret_alphabet.txt",
                    "src/main/resources/alphabet.txt");
        }
    }

    public void startDocument() {
        try {
            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeCharacters("\n");
        } catch (XMLStreamException exception) {
            exception.printStackTrace();
        }
    }

    public void endDocument() {
        try {
            xMLStreamWriter.writeEndDocument();
            xMLStreamWriter.flush();
            xMLStreamWriter.close();
            fileWriter.close();
            System.out.println("Resulting XML is written in file: " + filename);
        } catch (XMLStreamException | IOException exception) {
            exception.printStackTrace();
        }
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes atts) {
        try {
            xMLStreamWriter.writeStartElement(qName);

            if (atts != null) {
                for (int i=0, len = atts.getLength(); i<len; i++) {
                    xMLStreamWriter.writeAttribute(atts.getQName(i), obfuscator.obfuscate(atts.getValue(i)));
                }
            }
        } catch (XMLStreamException exception) {
            exception.printStackTrace();
        }
    }

    public void endElement(String uri, String localName,
                           String qName) {
        try {
            xMLStreamWriter.writeEndElement();
        } catch (XMLStreamException exception) {
            exception.printStackTrace();
        }
    }

    public void characters(char[] ch, int start, int len) {
        String untrimmed_string = new String(ch, start, len);
        String trimmed_string = untrimmed_string.trim();
        try {
            if (!trimmed_string.isEmpty()) {
                xMLStreamWriter.writeCharacters(obfuscator.obfuscate(trimmed_string));
            } else {
                xMLStreamWriter.writeCharacters(untrimmed_string);
            }
        } catch (XMLStreamException exception) {
            exception.printStackTrace();
        }
    }
}