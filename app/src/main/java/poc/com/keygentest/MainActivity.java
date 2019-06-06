package poc.com.keygentest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class MainActivity extends AppCompatActivity {
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            // Generate PrivatePublic Keypair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            pair = keyPairGenerator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();

            // Sign digital signature
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            byte[] secret = "abcdefghijklmnopqrstuvxyz".getBytes("UTF-8");
            signature.update(secret);
            byte[] digitalSignature = signature.sign();

            // Nu een manier zoeken om die digitalsignature op te slaan, samen met de public key.
            // zodat die op de serverkant geverified kan worden, op een soortgelijke manier als onderstaand.

            // Verify Digital Signature.
            Signature signatureV = Signature.getInstance("SHA256withRSA");
            signatureV.initVerify(publicKey);
            byte[] verifysecret = "abcdefghijklmnopqrstuvxyz".getBytes("UTF-8");
            signatureV.update(verifysecret);
            boolean verified = signatureV.verify(digitalSignature);
            System.out.println("Verified: " + verified);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
