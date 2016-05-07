package com.usda.fmsc.utilities;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    public static SecretKey generateKey(String key) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
        return generateKey(Base64.decode(key, Base64.DEFAULT));
    }

    public static SecretKey generateKey(byte[] key) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(key);
        kgen.init(128, sr);
        return kgen.generateKey();
    }

    public static SecretKey generateKey(String key, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException  {
        return generateKey(key.toCharArray(), salt);
    }

    private static SecretKey generateKey(char[] key, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final int iterations = 1000;

        final int outputKeyLength = 256;

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(key, salt, iterations, outputKeyLength);
        return secretKeyFactory.generateSecret(keySpec);
    }


    public static byte[] encodeFile(String key, byte[] fileData) throws
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return encodeFile(generateKey(key), fileData);
    }

    public static byte[] encodeFile(SecretKey key, byte[] fileData) throws
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        return encodeFile(key.getEncoded(), fileData);
    }

    public static byte[] encodeFile(byte[] key, byte[] fileData) throws
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        return cipher.doFinal(fileData);
    }


    public static byte[] decodeFile(String key, byte[] fileData) throws
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return decodeFile(generateKey(key), fileData);
    }

    public static byte[] decodeFile(SecretKey key, byte[] fileData) throws
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        return decodeFile(key.getEncoded(), fileData);
    }

    public static byte[] decodeFile(byte[] key, byte[] fileData) throws
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

        return cipher.doFinal(fileData);
    }


    public static SecretKey generateSalt() throws NoSuchAlgorithmException {
        // Generate a 256-bit key
        final int outputKeyLength = 256;

        SecureRandom secureRandom = new SecureRandom();
        // Do *not* seed secureRandom! Automatically seeded from system entropy.
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(outputKeyLength, secureRandom);
        return keyGenerator.generateKey();
    }
}
