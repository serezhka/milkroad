package com.tsystems.javaschool.milkroad.util;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Sergey on 05.03.2016.
 */
// TODO PowerMokito slows down tests several times
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(PassUtil.class)
public class PassUtilTest {
    private static final Logger LOGGER = Logger.getLogger(PassUtilTest.class);

    private Random random;

    @Before
    public void setUp() throws Exception {
        random = new Random();
    }

    @After
    public void tearDown() throws Exception {
        random = null;
    }

    /**
     * Verify that getSalt returns unique values
     */
    @Test
    public void testGetSaltIsUnique() throws Exception {
        LOGGER.info("Test testGetSaltUnique BEGIN");
        final int saltsCount = 100000;
        final Set<String> salts = new HashSet<>(saltsCount);
        //PowerMockito.mockStatic(PassUtil.class);
        //PowerMockito.spy(PassUtil.class);
        final String methodName = "getSalt";
        final Method method = PassUtil.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        String salt;
        for (int i = 0; i < saltsCount; i++) {
            salt = (String) method.invoke(null);
            LOGGER.debug(i + " salt: " + salt);
            salts.add(salt);
        }
        LOGGER.debug("Salts " + salts.size() + " = " + saltsCount + " is " + (salts.size() == saltsCount));
        Assert.assertTrue(salts.size() == saltsCount);
        //PowerMockito.verifyPrivate(PassUtil.class, Mockito.times(saltsCount)).invoke(methodName);
        LOGGER.info("Test testGetSaltUnique END");
    }

    /**
     * Check if PassUtil create/verify pass hash works
     * Check if same passwords have different hashes (with salt usage)
     */
    @Test
    public void testPassUtilWorks() throws NoSuchAlgorithmException {
        LOGGER.info("Test testPassUtilWorks BEGIN");
        final int passCount = 100000;
        final int passMaxLength = 8;
        final String characters = " _123aAbBcCdDfFeEgGxXyYzZ?*!";
        String pass;
        for (int i = 0; i < passCount; i++) {
            pass = generateString(random, characters, random.nextInt(passMaxLength + 1));
            final PassHash passHash = PassUtil.createPassHash(pass);
            final PassHash samePassHash = PassUtil.createPassHash(pass);
            LOGGER.debug("Pass: '" + pass + "', hash_1: " + passHash.getHash() + ", hash_2: " + samePassHash.getHash());
            Assert.assertTrue(PassUtil.verifyPass(pass, passHash.getHash(), passHash.getSalt()));
            Assert.assertFalse(passHash.getHash().equals(samePassHash.getHash()));
        }
        LOGGER.info("Test testPassUtilWorks END");
    }

    private String generateString(final Random random, final String characters, final int length) {
        final char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}
