package app.chat.babble;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtTokenTest {
    public static void main(String[] args) {
       //generateSecret();
       decode();
    }

    private static void generateSecret() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String keyString = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println(keyString);
    }

    private static void decode() {
        String jwtToken = "";
        String secret = "";
        // Decoding the JWT token without verifying the signature
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwtToken);

            // Extracting claims from the token
            Claims claims = claimsJws.getBody();

            // Accessing individual claims
            String subject = claims.getSubject();
            System.out.println("Subject: " + subject);

            // You can access other claims in a similar way
        } catch (Exception e) {
            // Handle parsing/decoding errors
            System.out.println("Error decoding JWT: " + e.getMessage());
        }
    }
}
