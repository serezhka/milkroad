package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.MrUserEntity;
import com.tsystems.javaschool.milkroad.model.UserTypeEnum;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;

/**
 * Created by Sergey on 11.02.2016.
 */
public class UserDAOImplTest {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImplTest.class);

    private EntityManager entityManager;
    private UserDAO<MrUserEntity, Long> userDAO;

    private MrUserEntity user1 = new MrUserEntity(null, UserTypeEnum.SIMPLE_USER,
            "TestName", "TestSurname", Date.valueOf("2000-01-01"), "test1@mail.ru",
            "12345678123456781234567812345678", "12345678123456781234567812345678");

    private MrUserEntity user2 = new MrUserEntity(null, UserTypeEnum.SIMPLE_USER,
            "TestName", "TestSurname", Date.valueOf("2000-01-01"), "test2@mail.ru",
            "12345678123456781234567812345678", "12345678123456781234567812345678");

    @Before
    public void setUp() throws Exception {
        final MilkroadAppContext milkroadAppContext = MilkroadAppContext.getInstance();
        entityManager = milkroadAppContext.getEntityManager();
        userDAO = milkroadAppContext.getUserDAO();
    }

    @After
    public void tearDown() {
        userDAO = null;
        entityManager = null;
    }

    @Test
    /**
     * There are strange things happen
     * if it will be separate test per method
     */
    public void test0UserDAOImpl() throws MilkroadDAOException {
        /* Try to delete test users if exists */
        LOGGER.info("delete test users begin");
        entityManager.getTransaction().begin();
        try {
            userDAO.remove(userDAO.getByEmail(user1.getEmail()));
            userDAO.remove(userDAO.getByEmail(user2.getEmail()));
        } catch (final MilkroadDAOException ignored) {
        }
        entityManager.getTransaction().commit();
        LOGGER.info("delete test users end");

        /* Persist test */
        LOGGER.info("persist test begin");
        entityManager.getTransaction().begin();
        userDAO.persist(user1);
        userDAO.persist(user2);
        entityManager.getTransaction().commit();
        LOGGER.info("persist test end");

        /* GetByEmail test */
        LOGGER.info("get by email test begin");
        entityManager.getTransaction().begin();
        user1 = userDAO.getByEmail(user1.getEmail());
        user2 = userDAO.getByEmail(user2.getEmail());
        entityManager.getTransaction().commit();
        LOGGER.info("get by email test end");

        /* Merge test */
        LOGGER.info("merge test begin");
        user1.setUserType(UserTypeEnum.ADMIN);
        user2.setUserType(UserTypeEnum.ADMIN);
        entityManager.getTransaction().begin();
        userDAO.merge(user1);
        userDAO.merge(user2);
        entityManager.getTransaction().commit();
        LOGGER.info("merge test end");

        /* Get all test */
        LOGGER.info("get all test begin");
        final List<MrUserEntity> entities;
        entityManager.getTransaction().begin();
        entities = userDAO.getAll();
        entityManager.getTransaction().commit();
        Assert.assertTrue(entities.contains(user1));
        Assert.assertTrue(entities.contains(user2));
        LOGGER.info("get all test end");

        /* Remove test */
        LOGGER.info("remove test begin");
        entityManager.getTransaction().begin();
        userDAO.remove(user1);
        userDAO.remove(user2);
        entityManager.getTransaction().commit();
        LOGGER.info("remove test end");

        /* Remove check */
        LOGGER.info("remove check begin");
        entityManager.getTransaction().begin();
        Assert.assertNull(userDAO.getByID(user1.getId()));
        Assert.assertNull(userDAO.getByID(user2.getId()));
        entityManager.getTransaction().commit();
        LOGGER.info("remove check end");
    }
}