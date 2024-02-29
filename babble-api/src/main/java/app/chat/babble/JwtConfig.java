package app.chat.babble;

import app.chat.babble.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(jwtSecret, jwtExpirationMs);
    }
}
