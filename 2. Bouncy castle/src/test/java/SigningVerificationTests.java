import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.junit.jupiter.api.Assertions.*;

public class SigningVerificationTests {

    @Test
    public void exampleTest()
            throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException,
            KeyStoreException, NoSuchProviderException, CMSException, OperatorCreationException {
        Signer signer = new Signer();
        Verifier verifier = new Verifier();

        String testData = "some test data";
        byte[] rawData = testData.getBytes();

        byte[] signedData = signer.signData(rawData);
        Boolean check = verifier.verifySignedData(signedData);
        assertTrue(check);
    }
}
