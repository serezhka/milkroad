package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.UserService;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

/**
 * Created by Sergey on 19.02.2016.
 */
public class UserServiceImplTest {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImplTest.class);

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = MilkroadAppContext.getInstance().getUserService();
    }

    @After
    public void tearDown() throws Exception {
        userService = null;
    }

    @Test
    public void testUserService() throws Exception {
        final UserDTO userDTO = new UserDTO("User", "Service",  Date.valueOf("1993-03-08"), "userService@test.ru", null);
        final String pass = "querty";

        /* Add user */
        userService.addNewUser(userDTO, pass);
        final UserDTO userDTO1 = userService.getUserByEmail(userDTO.getEmail());
        Assert.assertEquals(userDTO.getFirstName(), userDTO1.getFirstName());
        Assert.assertEquals(userDTO.getLastName(), userDTO1.getLastName());
        Assert.assertEquals(userDTO.getBirthday(), userDTO1.getBirthday());
        Assert.assertEquals(userDTO.getEmail(), userDTO1.getEmail());

        /* Phantom user test */
        Assert.assertNull(userService.getUserByEmail("non-existing@mail.ru"));
    }
}
