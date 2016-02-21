package com.tsystems.javaschool.milkroad.util;

/**
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
