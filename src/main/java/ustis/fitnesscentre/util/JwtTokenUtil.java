package ustis.fitnesscentre.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ustis.fitnesscentre.model.Client;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    @Value("${jwt.signing-key}")
    private String signingKey;

    @Value("${jwt.token-expiration-time}")
    private String tokenExpirationTime;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Client client) {
        return doGenerateToken(client.getPhoneNumber());
    }

    private String doGenerateToken(String subject) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(tokenExpirationTime) * 1000L))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (JwtException e) {
            return false;
        }
        // TODO перенести логи в логгер после его подключения
//        } catch (MalformedJwtException e) {
//            System.out.println("Invalid JWT token: " + e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            System.out.println("Unsupported JWT token: " + e.getMessage());
//        } catch (SignatureException e) {
//            System.out.println("Invalid JWT signature: " + e.getMessage());
//        } catch (IllegalArgumentException e) {
//            System.out.println("JWT token is empty or null: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error while parsing JWT token: " + e.getMessage());
//        }
    }
}