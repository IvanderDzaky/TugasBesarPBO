package hotel.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JWTUtil {

    private static final String SECRET = "rahasia123"; // Ganti di produksi
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private static final long EXPIRATION_TIME = 3600_000; // 1 jam

    public static String generateToken(int userId, boolean isAdmin, String email) {
        return JWT.create()
                .withClaim("id", userId)
                .withClaim("isAdmin", isAdmin)
                .withClaim("email", email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public static boolean isValid(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAdmin(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("isAdmin").asBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    public static int getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asInt();
        } catch (Exception e) {
            return -1;
        }
    }

}
