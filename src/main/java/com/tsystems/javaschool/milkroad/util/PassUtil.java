package com.tsystems.javaschool.milkroad.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Sergey on 19.02.2016.
 */
public class PassUtil {

    /**
     * @param pass clean pass
     * @return PassHash, where
     * PassHash.hash md5(md5(salt) + md5(pass))
     * PassHash.salt salt
     * @throws NoSuchAlgorithmException
     */
    public static PassHash createPassHash(final String pass) throws NoSuchAlgorithmException {
        return getPassHashWithSalt(pass, getSalt());
    }

    /**
     * @param pass     clean pass
     * @param passHash md5(md5(salt) + md5(pass)) from db
     * @param salt     pass salt from db
     * @return true, if it's ok, false - otherwise
     * @throws NoSuchAlgorithmException
     */
    public static boolean verifyPass(final String pass, final String passHash,
                                     final String salt) throws NoSuchAlgorithmException {
        final PassHash passHashResult = getPassHashWithSalt(pass, salt);
        return passHashResult.getHash().equals(passHash);
    }

    /**
     * @param pass clean password
     * @param salt pass salt
     * @return PassHash, where
     * PassHash.hash - md5(md5(salt) + md5(pass))
     * PassHash.salt - salt
     * @throws NoSuchAlgorithmException
     */
    private static PassHash getPassHashWithSalt(final String pass, final String salt) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        /* pass salt md5 hash */
        final byte[] passSaltHash = messageDigest.digest(salt.getBytes());
        messageDigest.reset();

        /* pass md5 hash */
        final byte[] passHash = messageDigest.digest(pass.getBytes());
        messageDigest.reset();

        /* result md5 hash */
        messageDigest.update(passSaltHash);
        messageDigest.update(passHash);
        final byte[] resultHash = messageDigest.digest();
        final StringBuilder stringBuilder = new StringBuilder();
        for (final byte hashByte : resultHash) {
            stringBuilder.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return new PassHash(stringBuilder.toString(), salt);
    }

    /**
     * @return pass salt
     * @throws NoSuchAlgorithmException
     */
    private static String getSalt() throws NoSuchAlgorithmException {
        final SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        final byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        final StringBuilder stringBuilder = new StringBuilder();
        for (final byte saltByte : salt) {
            stringBuilder.append(Integer.toString((saltByte & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
}
