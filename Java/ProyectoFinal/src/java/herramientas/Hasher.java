/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herramientas;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public  class Hasher {
    public final Integer DEFAULT_ITERATIONS = 10000;
    public final String algorithm = "pbkdf2_sha256";

    public Hasher() {}

    public String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            System.exit(1);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Could NOT generate secret key");
            e.printStackTrace();
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    public String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    public String encode(String password, String salt) {
        return this.encode(password, salt, this.DEFAULT_ITERATIONS);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and
        // HASH; parts are joined with dollar character ("$")
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }



//    public static void main(String[] args) {
//        runTests();
//    }

//    private static void runTests() {
//        System.out.println("===========================");
//        System.out.println("= Testing password hasher =");
//        System.out.println("===========================");
//        System.out.println();
//
//        System.out.println();
//        System.out.println(passwordShouldMatch("joelacc", "pbkdf2_sha256$120000$0jWvExb5io6a$CROB8iuuJBgaseBIf6oZTAFDhjLGjuxGE+sW+QSfaRc="));
//        passwordShouldMatch("mystery", "pbkdf2_sha256$10000$mysalt$KjUU5KrwyUbKTGYkHqBo1IwUbFBzKXrGQgwA1p2AuY0=");  // custom salt
//        passwordShouldMatch("s3cr3t", "pbkdf2_sha256$10000$BjDHOELBk7fR$xkh1Xf6ooTqwkflS3rAiz5Z4qOV1Jd5Lwd8P+xGtW+I=");
//        passwordShouldMatch("puzzle", "pbkdf2_sha256$10000$IFYFG7hiiKYP$rf8vHYFD7K4q2N3DQYfgvkiqpFPGCTYn6ZoenLE3jLc=");
//        passwordShouldMatch("riddle", "pbkdf2_sha256$10000$A0S5o3pNIEq4$Rk2sxXr8bonIDOGj6SU4H/xpjKHhHAKpFXfmNZ0dnEY=");
//
//        if(!passwordShouldMatch("joelacc", "pbkdf2_sha256$120000$0jWvExb5io6a$CROB8iuuJBgaseBIf6oZTAFDhjLGjuxGE+sW+QSfaRc=")){
//            passwordShouldNotMatch("joelacc", "pbkdf2_sha256$120000$0jWvExb5io6a$CROB8iuuJBgaseBIf6oZTAFDhjLGjuxGE+sW+QSfaRc=");
//        }
//        
//        System.out.println();
//        passwordShouldNotMatch("foo", "");
//        passwordShouldNotMatch("joel", "pbkdf2_sha256$120000$0jWvExb5io6a$CROB8iuuJBgaseBIf6oZTAFDhjLGjuxGE+sW+QSfaRc=");
//        
//        
//        passwordShouldNotMatch("mystery", "pbkdf2_md5$10000$qx1ec0f4lu4l$3G81rAm/4ng0tCCPTrx2aWohq7ztDBfFYczGNoUtiKQ=");
//        passwordShouldNotMatch("mystery", "pbkdf2_sha1$10000$qx1ec0f4lu4l$3G81rAm/4ng0tCCPTrx2aWohq7ztDBfFYczGNoUtiKQ=");
//        passwordShouldNotMatch("mystery", "pbkdf2_sha256$10001$Qx1ec0f4lu4l$3G81rAm/4ng0tCCPTrx2aWohq7ztDBfFYczGNoUtiKQ=");
//        passwordShouldNotMatch("mystery", "pbkdf2_sha256$10001$qx1ec0f4lu4l$3G81rAm/4ng0tCCPTrx2aWohq7ztDBfFYczGNoUtiKQ=");
//        passwordShouldNotMatch("mystery", "pbkdf2_sha256$10000$qx7ztDBfFYczGNoUtiKQ=");
//        passwordShouldNotMatch("s3cr3t", "pbkdf2_sha256$10000$BjDHOELBk7fR$foobar");
//        passwordShouldNotMatch("puzzle", "pbkdf2_sha256$10000$IFYFG7hiiKYP$rf8vHYFD7K4q2N3DQYfgvkiqpFPGCTYn6ZoenLE3jLcX");
//    }

    public boolean passwordShouldMatch(String password, String expectedHash) {
        Hasher hasher = new Hasher();

        if (hasher.checkPassword(password, expectedHash)) {
            return true;
        } else {
            String[] parts = expectedHash.split("\\$");
            if (parts.length != 4) {
                System.out.printf(" => Wrong hash provided: '%s'\n", expectedHash);
                return false;
            }
            String salt = parts[2];
            String resultHash = hasher.encode(password, salt);
            String msg = " => Wrong! Password '%s' hash expected to be '%s' but is '%s'\n";
            System.out.printf(msg, password, expectedHash, resultHash);
            return false;
        }
    }

    public  String passwordShouldNotMatch(String password, String expectedHash) {
        Hasher hasher = new Hasher();

        if (hasher.checkPassword(password, expectedHash)) {
            System.out.printf(" => Incorrecto (password '%s' did '%s' match but were not supposed to)\n", password, expectedHash);
            return "Error de contraseña";
        } else {
            return "Las contraseña no coincide";
        }
    }

}