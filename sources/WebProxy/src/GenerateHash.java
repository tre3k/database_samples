import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateHash {
    public static String md5Hash(String inputText){
        String outHash = new String();

        MessageDigest md = null;
        byte [] digest = new byte[0];

        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(inputText.getBytes());
            digest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        outHash = bigInt.toString(16);

        while( outHash.length() < 32 ){
            outHash = "0" + outHash;
        }

        return outHash;
    }
}
