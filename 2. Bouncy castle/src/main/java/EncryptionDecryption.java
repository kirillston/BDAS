import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OutputEncryptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class EncryptionDecryption {

    private static X509Certificate certificate;
    private static PrivateKey privateKey;


    public static void main(String[] args) throws CertificateException, IOException, CMSException, NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, UnrecoverableKeyException {
        EncryptionDecryption encryptor = new EncryptionDecryption();
        String secretMessage = "My password is 123456Seven";
        System.out.println("Original Message : " + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = encryptData(stringToEncrypt, certificate);
        System.out.println("Encrypted Message : " + new String(encryptedData));
        byte[] rawData = decryptData(encryptedData, privateKey);
        String decryptedMessage = new String(rawData);
        System.out.println("Decrypted Message : " + decryptedMessage);
    }

    public EncryptionDecryption()
            throws CertificateException, NoSuchProviderException, NoSuchAlgorithmException, IOException, KeyStoreException, UnrecoverableKeyException {
        Security.setProperty("crypto.policy", "unlimited");
        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        System.out.println("Max Key Size for AES : " + maxKeySize);
        Security.addProvider(new BouncyCastleProvider());

        CertificateFactory certFactory= CertificateFactory.getInstance("X.509", "BC");

        certificate = (X509Certificate) certFactory.generateCertificate(new FileInputStream("../resources/public.cer"));

        char[] keystorePassword = "password".toCharArray();
        char[] keyPassword = "password".toCharArray();

        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream("private.p12"), keystorePassword);
        privateKey = (PrivateKey) keystore.getKey("baeldung", keyPassword);
    }

    public static byte[] encryptData(byte[] data, X509Certificate encryptionCertificate)
            throws CertificateEncodingException, CMSException, IOException {

        byte[] encryptedData = null;
        if (null != data && null != encryptionCertificate) {

            CMSEnvelopedDataGenerator cmsEnvelopedDataGenerator = new CMSEnvelopedDataGenerator();

            JceKeyTransRecipientInfoGenerator jceKey = new JceKeyTransRecipientInfoGenerator(encryptionCertificate);
            cmsEnvelopedDataGenerator.addRecipientInfoGenerator(jceKey);
            CMSTypedData msg = new CMSProcessableByteArray(data);
            OutputEncryptor encryptor = new JceCMSContentEncryptorBuilder(CMSAlgorithm.AES128_CBC).setProvider("BC").build();
            CMSEnvelopedData cmsEnvelopedData = cmsEnvelopedDataGenerator.generate(msg,encryptor);
            encryptedData = cmsEnvelopedData.getEncoded();
        }
        return encryptedData;
    }

    public static byte[] decryptData(byte[] encryptedData, PrivateKey decryptionKey) throws CMSException {
        byte[] decryptedData = null;
        if (null != encryptedData && null != decryptionKey) {
            CMSEnvelopedData envelopedData = new CMSEnvelopedData(encryptedData);

            Collection<RecipientInformation> recipients
                    = envelopedData.getRecipientInfos().getRecipients();
            KeyTransRecipientInformation recipientInfo
                    = (KeyTransRecipientInformation) recipients.iterator().next();
            JceKeyTransRecipient recipient
                    = new JceKeyTransEnvelopedRecipient(decryptionKey);

            return recipientInfo.getContent(recipient);
        }
        return decryptedData;
    }

    public static X509Certificate getCertificate() {
        return certificate;
    }

    public static PrivateKey getPrivateKey(){
        return privateKey;
    }
}
