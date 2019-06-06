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

    /* We need to use digital signatures. They provide:
    * Integrity
    * Authentication
    * Non-repudiation
    */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            // Generate PrivatePublic Keypair with KeypairGenerator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            pair = keyPairGenerator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();

            // Signing of digital signature
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            byte[] secret = "abcdefghijklmnopqrstuvxyz".getBytes("UTF-8");
            // This can also become a hash of something a TruYou user uses upon logging in. Maybe a hash of the public key?

            signature.update(secret);
            byte[] digitalSignature = signature.sign();

            // Need to find a way to save the digital signature, together with the public key.
            // Then get it to the server, so the digital signature can be verified on the node js server, in a similar way as below.
            // (Tl:dr, need to verify the SHA256withRSA signature with the public key on the node server)

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
