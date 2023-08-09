package kr.co.mz.sns.config.security;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class KeyPairLoader {
    public static KeyPair loadKeyPair(String privateKeyPemPath, String publicKeyPemPath) throws Exception {
        var privateKeyPem = new File(privateKeyPemPath);
        var publicKeyPem = new File(publicKeyPemPath);
        try (
                var privateFileInputStream = new FileInputStream(privateKeyPem);
                var publicFileInputStream = new FileInputStream(publicKeyPem);
                var privateByteArrayOutputStream = convertFileWithBufferToBArrayOutputStream(privateFileInputStream);
                var publicByteArrayOutputStream = convertFileWithBufferToBArrayOutputStream(publicFileInputStream);
        ) {
            var privateKeyBytesString = privateByteArrayOutputStream.toString();
            var publicKeyBytesString = publicByteArrayOutputStream.toString();
            var readPrivateKey = pemToPrivateKey(privateKeyBytesString);
            var readPublicKey = pemToPublicKey(publicKeyBytesString);

            return new KeyPair(readPublicKey, readPrivateKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load KeyPair.", e);
        }
    }

    public static PrivateKey pemToPrivateKey(String privateKeyPem) throws Exception {
        PemReader pemReader = new PemReader(new StringReader(privateKeyPem));
        PemObject pemObject = pemReader.readPemObject();
        pemReader.close();

        byte[] privateKeyBytes = pemObject.getContent();
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(privateKeySpec);
    }

    public static PublicKey pemToPublicKey(String publicKeyPem) throws Exception {
        var pemReader = new PemReader(new StringReader(publicKeyPem));
        var pemObject = pemReader.readPemObject();
        pemReader.close();

        var publicKeyBytes = pemObject.getContent();
        var publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        var keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(publicKeySpec);
    }

    private static ByteArrayOutputStream convertFileWithBufferToBArrayOutputStream(FileInputStream fileInputStream)
            throws Exception {
        byte[] buffer = new byte[1024];
        int readBytes;
        var byteArrayOutputStream = new ByteArrayOutputStream();
        while ((readBytes = fileInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer);
        }

        return byteArrayOutputStream;
    }

}
