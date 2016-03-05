package com.tsystems.javaschool.milkroad.util;

/**
 * Container for pass data <p>
 * {@code pass} md5( md5(salt) + md5(pass) ) <br>
 * {@code salt} pass salt <p>
 * <p/>
 * Created by Sergey on 19.02.2016.
 */
public class PassHash {
    private final String hash;
    private final String salt;

    public PassHash(final String hash, final String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
}
