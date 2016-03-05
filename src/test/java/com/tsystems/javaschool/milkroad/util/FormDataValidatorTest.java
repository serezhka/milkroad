package com.tsystems.javaschool.milkroad.util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by Sergey on 05.03.2016.
 */
public class FormDataValidatorTest {
    private static final Logger LOGGER = Logger.getLogger(FormDataValidatorTest.class);

    /**
     * Email validation test
     * Email must be matched with pattern:
     * "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
     */
    @Test
    public void testValidateEmail() {
        LOGGER.info("Test testValidateEmail BEGIN");
        Assert.assertFalse(FormDataValidator.validateEmail(null));
        Assert.assertFalse(FormDataValidator.validateEmail(""));
        Assert.assertFalse(FormDataValidator.validateEmail(" "));
        Assert.assertFalse(FormDataValidator.validateEmail("1"));
        Assert.assertFalse(FormDataValidator.validateEmail("me@"));
        Assert.assertFalse(FormDataValidator.validateEmail("@example.com"));
        Assert.assertFalse(FormDataValidator.validateEmail("me.@example.com"));
        Assert.assertFalse(FormDataValidator.validateEmail(".me@example.com"));
        Assert.assertFalse(FormDataValidator.validateEmail("me@example..com"));
        Assert.assertFalse(FormDataValidator.validateEmail("me.example@com"));
        Assert.assertFalse(FormDataValidator.validateEmail("me\\@example.com"));
        Assert.assertTrue(FormDataValidator.validateEmail("me@example.com"));
        Assert.assertTrue(FormDataValidator.validateEmail("a.nonymous@example.com"));
        Assert.assertTrue(FormDataValidator.validateEmail("name+tag@example.com"));
        LOGGER.info("Test testValidateEmail END");
    }

    /**
     * Name validation test
     * Name - is simple string in milkroad db
     * Name must be not {@code null}, max name size is 45 chars
     */
    @Test
    public void testValidateName() {
        LOGGER.info("Test testValidateName BEGIN");
        Assert.assertTrue(FormDataValidator.validateName("Sergey"));
        Assert.assertTrue(FormDataValidator.validateName("Sergey_93"));
        Assert.assertFalse(FormDataValidator.validateName(null));
        Assert.assertFalse(FormDataValidator.validateName(""));
        Assert.assertFalse(FormDataValidator.validateName("VeryLongStringBiggerThan45CharactersVeryLongStringBiggerThan45Characters"));
        LOGGER.info("Test testValidateName END");
    }

    /**
     * Pass validation test
     * Pass - is simple string in milkroad db
     * Pass must be not {@code null}, max name size is 45 chars
     */
    @Test
    public void testValidatePass() {
        LOGGER.info("Test testValidatePass BEGIN");
        Assert.assertTrue(FormDataValidator.validateName("Pass"));
        Assert.assertTrue(FormDataValidator.validateName("Pass_!!"));
        Assert.assertFalse(FormDataValidator.validateName(null));
        Assert.assertFalse(FormDataValidator.validateName(""));
        Assert.assertFalse(FormDataValidator.validateName("VeryLongStringBiggerThan45CharactersVeryLongStringBiggerThan45Characters"));
        LOGGER.info("Test testValidatePass END");
    }

    /**
     * Date validation test
     * Date - is java.sql.Date
     * Date must be not {@code null}, in format: (yyyy-[m]m-[d]d)
     */
    @Test
    public void testValidateDate() throws Exception {
        LOGGER.info("Test testValidateDate BEGIN");
        Assert.assertTrue(FormDataValidator.validateDate("2000-12-12"));
        Assert.assertTrue(FormDataValidator.validateDate("2000-12-1"));
        Assert.assertTrue(FormDataValidator.validateDate("2000-1-12"));
        Assert.assertFalse(FormDataValidator.validateDate(null));
        Assert.assertFalse(FormDataValidator.validateDate(""));
        Assert.assertFalse(FormDataValidator.validateDate("date"));
        Assert.assertFalse(FormDataValidator.validateDate("1"));
        Assert.assertFalse(FormDataValidator.validateDate("1-1-1"));
        Assert.assertFalse(FormDataValidator.validateDate("2000-13-13"));
        LOGGER.info("Test testValidateDate END");
    }

    /**
     * Integer validation test
     *
     * @throws Exception
     */
    @Test
    public void testValidateInteger() throws Exception {
        LOGGER.info("Test testValidateInteger BEGIN");
        Assert.assertTrue(FormDataValidator.validateInteger("0"));
        Assert.assertTrue(FormDataValidator.validateInteger("-1"));
        Assert.assertTrue(FormDataValidator.validateInteger("" + Integer.MIN_VALUE));
        Assert.assertTrue(FormDataValidator.validateInteger("" + Integer.MAX_VALUE));
        Assert.assertFalse(FormDataValidator.validateInteger(null));
        Assert.assertFalse(FormDataValidator.validateInteger(""));
        Assert.assertFalse(FormDataValidator.validateInteger("integer"));
        Assert.assertFalse(FormDataValidator.validateInteger("" + Long.MIN_VALUE));
        Assert.assertFalse(FormDataValidator.validateInteger("" + Long.MAX_VALUE));
        LOGGER.info("Test testValidateInteger END");
    }

    /**
     * Long validation test
     *
     * @throws Exception
     */
    @Test
    public void testValidateLong() throws Exception {
        LOGGER.info("Test testValidateLong BEGIN");
        Assert.assertTrue(FormDataValidator.validateLong("0"));
        Assert.assertTrue(FormDataValidator.validateLong("-1"));
        Assert.assertTrue(FormDataValidator.validateLong("" + Long.MIN_VALUE));
        Assert.assertTrue(FormDataValidator.validateLong("" + Long.MAX_VALUE));
        Assert.assertFalse(FormDataValidator.validateLong(null));
        Assert.assertFalse(FormDataValidator.validateLong(""));
        Assert.assertFalse(FormDataValidator.validateLong("long"));
        Assert.assertFalse(FormDataValidator.validateLong("1" + Long.MAX_VALUE));
        LOGGER.info("Test testValidateLong END");
    }

    /**
     * BigDecimal validation test
     *
     * @throws Exception
     */
    @Test
    public void testValidateBigDecimal() throws Exception {
        LOGGER.info("Test testValidateBigDecimal BEGIN");
        Assert.assertTrue(FormDataValidator.validateBigDecimal("0"));
        Assert.assertTrue(FormDataValidator.validateBigDecimal("-1"));
        Assert.assertTrue(FormDataValidator.validateBigDecimal("" + BigDecimal.TEN.toPlainString()));
        Assert.assertFalse(FormDataValidator.validateBigDecimal(null));
        Assert.assertFalse(FormDataValidator.validateBigDecimal(""));
        Assert.assertFalse(FormDataValidator.validateBigDecimal("BigDecimal"));
        LOGGER.info("Test testValidateBigDecimal END");
    }

    /**
     * Generic enum validation test
     *
     * @throws Exception
     */
    @Test
    public void testValidateEnum() throws Exception {
        LOGGER.info("Test testValidateEnum BEGIN");
        Assert.assertTrue(FormDataValidator.validateEnum(TestEnum.class, TestEnum.E1.name()));
        Assert.assertTrue(FormDataValidator.validateEnum(TestEnum.class, TestEnum.e2.name()));
        Assert.assertTrue(FormDataValidator.validateEnum(TestEnum.class, TestEnum.E_3.name()));
        Assert.assertTrue(FormDataValidator.validateEnum(TestEnum.class, "E1"));
        Assert.assertTrue(FormDataValidator.validateEnum(TestEnum.class, "e2"));
        Assert.assertTrue(FormDataValidator.validateEnum(TestEnum.class, "E_3"));
        Assert.assertFalse(FormDataValidator.validateEnum(TestEnum.class, null));
        Assert.assertFalse(FormDataValidator.validateEnum(TestEnum.class, ""));
        Assert.assertFalse(FormDataValidator.validateEnum(TestEnum.class, "0"));
        Assert.assertFalse(FormDataValidator.validateEnum(TestEnum.class, "e1"));
        Assert.assertFalse(FormDataValidator.validateEnum(TestEnum.class, "e_4"));
        LOGGER.info("Test testValidateEnum END");
    }

    private enum TestEnum {
        E1, e2, E_3
    }
}
