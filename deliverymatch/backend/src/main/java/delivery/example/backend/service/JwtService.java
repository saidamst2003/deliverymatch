package delivery.example.backend.service;

import delivery.example.backend.dto.AuthUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.function.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    private String secretKey;

    public JwtService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256);
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("HmacSHA256 algorithm not found for JWT secret key generation.", e);
        }
    }

    private SecretKey getKeys() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKeys())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String generateJwtToken(AuthUserDTO authUserDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", authUserDTO.id());
        claims.put("firstName", authUserDTO.fullName());
        claims.put("email", authUserDTO.email());
        claims.put("role", authUserDTO.role());

        long expirationTimeMillis = System.currentTimeMillis() + (24 * 60 * 60 * 1000);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(authUserDTO.email())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expirationTimeMillis))
                .and()
                .signWith(getKeys())
                .compact();
    }
}