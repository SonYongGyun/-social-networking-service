package kr.co.mz.sns.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

import static kr.co.mz.sns.config.security.SecurityConstants.JWT_EXPIRATION;

@Component
public class JWTService {

    @Value("${private.key.path}")
    private String privateKeyPemPath;
    @Value("${public.key.path}")
    private String publicKeyPemPath;

    private PrivateKey privateKey;
    private PublicKey publicKey;

//    public static KeyPair loadKeyPair(String privateKeyPemPath, String publicKeyPemPath) throws Exception {
//        var privateKeyPem = new File(privateKeyPemPath);
//        var publicKeyPem = new File(publicKeyPemPath);
//        try (
//                var privateFileInputStream = new FileInputStream(privateKeyPem);
//                var publicFileInputStream = new FileInputStream(publicKeyPem);
//                var privateByteArrayOutputStream = convertFileWithBufferToBArrayOutputStream(privateFileInputStream);
//                var publicByteArrayOutputStream = convertFileWithBufferToBArrayOutputStream(publicFileInputStream);
//        ) {
//            var privateKeyBytesString = privateByteArrayOutputStream.toString();
//            var publicKeyBytesString = publicByteArrayOutputStream.toString();
//            var readPrivateKey = pemToPrivateKey(privateKeyBytesString);
//            var readPublicKey = pemToPublicKey(publicKeyBytesString);
//
//            return new KeyPair(readPublicKey, readPrivateKey);
//        }
//    }
//
//    public static PrivateKey pemToPrivateKey(String privateKeyPem) throws Exception {
//        PemReader pemReader = new PemReader(new StringReader(privateKeyPem));
//        PemObject pemObject = pemReader.readPemObject();
//        pemReader.close();
//
//        byte[] privateKeyBytes = pemObject.getContent();
//        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//        return keyFactory.generatePrivate(privateKeySpec);
//    }
//
//    public static PublicKey pemToPublicKey(String publicKeyPem) throws Exception {
//        var pemReader = new PemReader(new StringReader(publicKeyPem));
//        var pemObject = pemReader.readPemObject();
//        pemReader.close();
//
//        var publicKeyBytes = pemObject.getContent();
//        var publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
//        var keyFactory = KeyFactory.getInstance("RSA");
//
//        return keyFactory.generatePublic(publicKeySpec);
//    }
//
//    private static ByteArrayOutputStream convertFileWithBufferToBArrayOutputStream(FileInputStream fileInputStream)
//            throws Exception {
//        byte[] buffer = new byte[1024];
//        int readBytes;
//        var byteArrayOutputStream = new ByteArrayOutputStream();
//        while ((readBytes = fileInputStream.read(buffer)) != -1) {
//            byteArrayOutputStream.write(buffer);
//        }
//
//        return byteArrayOutputStream;
//    }

    @PostConstruct
    private void init() {
        try {
            KeyPair keyPair = KeyPairLoader.loadKeyPair(privateKeyPemPath, publicKeyPemPath);
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException("Failed to Key Load", e);
        }

    }

    public String generateToken(Authentication authentication) {
        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        var seq = customUserDetails.getUserDto().getSeq();
        var email = customUserDetails.getUserDto().getEmail();
        var name = customUserDetails.getUserDto().getName();
        name = name == null ? "Anonymous" : name;

        var currentDate = new Date();
        var expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .addClaims(Map.of("seq", seq, "email", email, "name", name))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }


    /**
     * 제시하신 getUserNameFromJWT 메서드는 JWT 토큰에서 'subject'라는 클레임을 추출하는 역할을 합니다. 이 'subject' 클레임은 일반적으로 사용자의 고유 식별자(예를 들어 사용자
     * 이름, 이메일, 사용자 ID 등)를 담고 있습니다.
     * <p>
     * 즉, 이 메서드는 주어진 JWT 토큰이 어떤 사용자에 대한 정보를 포함하고 있는지를 확인하는데 사용됩니다. 이를 통해 서버는 클라이언트의 요청에 대한 인증을 수행하고, 해당 사용자에 대한 권한을
     * 검증하며, 필요한 경우 사용자에 대한 추가적인 처리를 수행할 수 있습니다.
     * <p>
     * 예를 들어, 웹 애플리케이션에서 클라이언트는 로그인을 한 후 JWT 토큰을 받게 됩니다. 그 후 클라이언트는 이 토큰을 이용해 인증이 필요한 요청을 서버에 보냅니다. 이때 서버는
     * getUserNameFromJWT 메서드를 사용하여 토큰에서 사용자 식별자를 추출하고, 이를 바탕으로 해당 요청이 유효한 사용자에 의해 발생된 것임을 확인할 수 있습니다.
     *
     * @param token
     * @return
     */
    public String getEmailJWT(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
