import org.bouncycastle.cms.CMSException;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class EncryptionDecryptionTests {
    @Test
    public void exampleTest() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyStoreException, NoSuchProviderException, CMSException {
        EncryptionDecryption encryptor = new EncryptionDecryption();

        String secretMessage = "My password is 123456Seven";
        System.out.println("Original Message : " + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = encryptor.encryptData(stringToEncrypt, encryptor.getCertificate());
        System.out.println("Encrypted Message : " + new String(encryptedData));
        byte[] rawData = encryptor.decryptData(encryptedData, encryptor.getPrivateKey());
        String decryptedMessage = new String(rawData);
        System.out.println("Decrypted Message : " + decryptedMessage);
    }
}
