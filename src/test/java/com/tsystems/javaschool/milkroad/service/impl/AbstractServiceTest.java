package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.model.UserTypeEnum;
import com.tsystems.javaschool.milkroad.util.PassHash;
import com.tsystems.javaschool.milkroad.util.PassUtil;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Random;

/**
 * Created by Sergey on 07.03.2016.
 */
public class AbstractServiceTest {
    @Mock
    protected EntityManager mockEntityManager;

    protected Random random;

    @Before
    protected void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(mockEntityManager).refresh(Mockito.any(UserEntity.class));
        Mockito.when(mockEntityManager.getTransaction()).thenReturn(Mockito.mock(EntityTransaction.class));
        random = new Random();
    }

    /**
     * @param clazz enum class
     * @param <T>   enum class extends Enum
     * @return random value of enum T
     */
    protected <T extends Enum<T>> T randomEnum(final Class<T> clazz) {
        final int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * @param x magic number
     * @return UserEntity generated with magic number x
     * @throws NoSuchAlgorithmException
     */
    protected UserEntity createUserEntity(final int x) throws NoSuchAlgorithmException {
        final String pass = "" + x; // So that pass equals user name, last name
        final PassHash passHash = PassUtil.createPassHash(pass);
        return new UserEntity((long) x, randomEnum(UserTypeEnum.class),
                "" + x, "" + x, new Date(x), x + "@mail.ru", passHash.getHash(), passHash.getSalt());
    }
}
