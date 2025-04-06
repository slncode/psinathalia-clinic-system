package com.psinathalia.clinic.system.service;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CryptoService {
    private static final String ALGORITHM = "AES";

    // Chave AES segura em Base64 (substitua por uma chave gerada corretamente)
    private static final String SECRET_KEY_BASE64 = "WrH2iRlfPxuCxfoMwziMZgMpppP1mOSfRb9WFTgO8HY=";

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static Key getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY_BASE64);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar", e);
        }
    }

    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar", e);
        }
    }

    public boolean matches(String rawText, String encryptedText) {
        return passwordEncoder.matches(rawText, encryptedText);
    }

}

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CryptoService {
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    public String encrypt(String rawText) {
//        return passwordEncoder.encode(rawText);
//    }
//
//    public boolean matches(String rawText, String encryptedText) {
//        return passwordEncoder.matches(rawText, encryptedText);
//    }
//}
