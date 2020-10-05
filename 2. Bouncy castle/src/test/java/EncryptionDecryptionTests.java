import org.bouncycastle.cms.CMSException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class EncryptionDecryptionTests {
    @Test
    public void exampleTest() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyStoreException, NoSuchProviderException, CMSException {
        Receiver reciever = new Receiver();
        Sender sender = new Sender(reciever.getCertificate());

        String secretMessage = "My password is 12345Six";
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = sender.encryptData(stringToEncrypt);

        byte[] decryptedData = reciever.decryptData(encryptedData);
        String decryptedMessage = new String(decryptedData);

        assertEquals(secretMessage, decryptedMessage);
    }
}
