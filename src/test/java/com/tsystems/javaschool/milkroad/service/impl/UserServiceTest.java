package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 19.02.2016.
 */
public class UserServiceTest extends AbstractServiceTest {
    @InjectMocks
    private UserServiceImpl mockedUserService;
    @Mock
    private UserDAO<UserEntity, Long> mockUserDAO;

    private List<UserEntity> userEntities;
    private List<UserDTO> userDTOs;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        userEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userEntities.add(createUserEntity(i));
        }
        userDTOs = new ArrayList<>();
        for (final UserEntity userEntity : userEntities) {
            userDTOs.add(EntityDTOConverter.userDTO(userEntity));
        }
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Assert.assertTrue(mockedUserService.getAllUsers().isEmpty());
        Mockito.when(mockUserDAO.getAll()).thenReturn(userEntities);
        Assert.assertArrayEquals(userDTOs.toArray(), mockedUserService.getAllUsers().toArray());
        Mockito.verify(mockUserDAO, Mockito.times(2)).getAll();
    }

    @Test
    public void testGetUserByEmailPositive() throws Exception {
        for (final UserEntity userEntity : userEntities) {
            Mockito.when(mockUserDAO.getByEmail(userEntity.getEmail())).thenReturn(userEntity);
            Assert.assertEquals(EntityDTOConverter.userDTO(userEntity),
                    mockedUserService.getUserByEmail(userEntity.getEmail()));
            Mockito.verify(mockUserDAO, Mockito.times(1)).getByEmail(userEntity.getEmail());
        }
    }

    @Test
    public void testGetUserByEmailNegative() throws Exception {
        final String email = "not-existing@mail.ru";
        try {
            mockedUserService.getUserByEmail(email);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByEmail(email);
    }

    @Test
    public void testGetUserByEmailAndPassPositive() throws Exception {
        String pass;
        for (final UserEntity userEntity : userEntities) {
            Mockito.when(mockUserDAO.getByEmail(userEntity.getEmail())).thenReturn(userEntity);
            pass = userEntity.getFirstName(); // Pass equals user name, see createUserEntity method
            Assert.assertEquals(EntityDTOConverter.userDTO(userEntity),
                    mockedUserService.getUserByEmailAndPass(userEntity.getEmail(), pass));
            Mockito.verify(mockUserDAO, Mockito.times(1)).getByEmail(userEntity.getEmail());
        }
    }

    @Test
    public void testGetUserByEmailAndPassNegativeUser() throws Exception {
        final String email = "not-existing@mail.ru";
        try {
            mockedUserService.getUserByEmailAndPass(email, "pass");
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByEmail(email);
    }

    @Test
    public void testGetUserByEmailAndPassNegativePass() throws Exception {
        final UserEntity userEntity = userEntities.get(random.nextInt(userEntities.size()));
        Mockito.when(mockUserDAO.getByEmail(Mockito.anyString()))
                .thenReturn(userEntity);
        try {
            mockedUserService.getUserByEmailAndPass(userEntity.getEmail(), "wrong-pass");
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.PASS_INVALID, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByEmail(userEntity.getEmail());
    }

    @Test
    public void testAddNewUserPositive() throws Exception {
        String pass;
        for (final UserEntity userEntity : userEntities) {
            pass = userEntity.getFirstName(); // Let pass equals user name
            Assert.assertEquals(EntityDTOConverter.userDTO(userEntity),
                    mockedUserService.addNewUser(EntityDTOConverter.userDTO(userEntity), pass));
            Mockito.verify(mockUserDAO, Mockito.times(1)).getByEmail(userEntity.getEmail());
        }
        Mockito.verify(mockUserDAO, Mockito.times(userEntities.size())).persist(Mockito.any(UserEntity.class));
    }

    @Test
    public void testAddNewUserNegative() throws Exception {
        final UserEntity userEntity = userEntities.get(random.nextInt(userEntities.size()));
        final String pass = userEntity.getFirstName(); // Let pass equals user name
        Mockito.when(mockUserDAO.getByEmail(userEntity.getEmail()))
                .thenReturn(userEntity);
        try {
            mockedUserService.addNewUser(EntityDTOConverter.userDTO(userEntity), pass);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_EMAIL_ALREADY_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(0)).persist(Mockito.any(UserEntity.class));
    }

    @Test
    public void testUpdateUserInfoPositive() throws Exception {
        final UserEntity userEntity = userEntities.get(random.nextInt(userEntities.size()));
        Mockito.when(mockUserDAO.getByID(userEntity.getId()))
                .thenReturn(userEntity);
        Assert.assertEquals(EntityDTOConverter.userDTO(userEntity),
                mockedUserService.updateUserInfo(EntityDTOConverter.userDTO(userEntity)));
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(userEntity.getId());
        Mockito.verify(mockUserDAO, Mockito.times(1)).merge(Mockito.any(UserEntity.class));
    }

    @Test
    public void testUpdateUserInfoNegative() throws Exception {
        final UserEntity userEntity = userEntities.get(random.nextInt(userEntities.size()));
        Mockito.when(mockUserDAO.getByID(userEntity.getId()))
                .thenReturn(null);
        try {
            mockedUserService.updateUserInfo(EntityDTOConverter.userDTO(userEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(userEntity.getId());
        Mockito.verify(mockUserDAO, Mockito.times(0)).persist(Mockito.any(UserEntity.class));
    }

    @Test
    public void testUpdateUserPassPositive() throws Exception {
        final UserEntity userEntity = userEntities.get(random.nextInt(userEntities.size()));
        final UserDTO userDTO = EntityDTOConverter.userDTO(userEntity);
        final String newPass = userEntity.getLastName(); // Let new pass equals last name
        Mockito.when(mockUserDAO.getByID(userEntity.getId()))
                .thenReturn(userEntity);
        Assert.assertEquals(userDTO, mockedUserService.updateUserPass(userDTO, newPass));
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(userEntity.getId());
        Mockito.verify(mockUserDAO, Mockito.times(1)).merge(Mockito.any(UserEntity.class));
    }

    @Test
    public void testUpdateUserPassNegative() throws Exception {
        final UserEntity userEntity = userEntities.get(random.nextInt(userEntities.size()));
        final String newPass = userEntity.getLastName(); // Let new pass equals last name
        Mockito.when(mockUserDAO.getByID(userEntity.getId()))
                .thenReturn(null);
        try {
            mockedUserService.updateUserPass(EntityDTOConverter.userDTO(userEntity), newPass);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(userEntity.getId());
        Mockito.verify(mockUserDAO, Mockito.times(0)).persist(Mockito.any(UserEntity.class));
    }
}
