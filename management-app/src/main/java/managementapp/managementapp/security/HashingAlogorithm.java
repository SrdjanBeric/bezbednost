package managementapp.managementapp.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class HashingAlogorithm {
    private static final String HASHING_SECRET_KEY = "this_is_some_random_secret";

    private static Key generateSecretKey() throws NoSuchAlgorithmException {
        // Koristi generator tajnih ključeva za generiranje ključa
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecureRandom secureRandom = new SecureRandom();
        keyGen.init(secureRandom);
        return keyGen.generateKey();
    }

    public static String calculateHmac(String token) {
        try {
            // Stvori HMAC objekt s tajnim ključem i algoritmom
            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(HASHING_SECRET_KEY.getBytes(), "HmacSHA256"));

            // Izračunaj HMAC vrijednost za token
            byte[] hmacBytes = hmac.doFinal(token.getBytes());

            // Pretvori HMAC vrijednost u heksadecimalni oblik
            StringBuilder sb = new StringBuilder();
            for (byte b : hmacBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
