package com.tsystems.javaschool.milkroad;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.impl.UserDAOImpl;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.impl.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Sergey on 11.02.2016.
 */
public class MilkroadAppContext {

    /**
     * On demand holder singleton realization
     * http://habrahabr.ru/post/129494/
     */
    private static class SingletonHolder {
        private static final MilkroadAppContext INSTANCE = new MilkroadAppContext();
    }

    private static final String PERSISTENCE_UNIT = "MilkroadPersistenceUnit";

    private volatile EntityManagerFactory entityManagerFactory;
    private volatile EntityManager entityManager;

    /* DAOs */
    private volatile UserDAO userDAO;

    /* Services */
    private volatile UserService userService;

    private MilkroadAppContext() {
    }

    public static MilkroadAppContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Double checked locking & volatile singleton realization
     * http://habrahabr.ru/post/129494/
     */
    public EntityManagerFactory getEntityManagerFactory() {
        EntityManagerFactory localInstance = entityManagerFactory;
        if (entityManagerFactory == null) {
            synchronized (this) {
                localInstance = entityManagerFactory;
                if (entityManagerFactory == null) {
                    entityManagerFactory = localInstance = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                }
            }
        }
        return localInstance;
    }

    public EntityManager getEntityManager() {
        EntityManager localInstance = entityManager;
        if (localInstance == null || !localInstance.isOpen()) {
            synchronized (this) {
                localInstance = entityManager;
                if (localInstance == null || !localInstance.isOpen()) {
                    entityManager = localInstance = getEntityManagerFactory().createEntityManager();
                }
            }
        }
        return localInstance;
    }

    public UserDAO getUserDAO() {
        UserDAO localInstance = userDAO;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = userDAO;
                if (localInstance == null) {
                    userDAO = localInstance = new UserDAOImpl(getEntityManager());
                }
            }
        }
        return localInstance;
    }

    public UserService getUserService() {
        UserService localInstance = userService;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = userService;
                if (localInstance == null) {
                    userService = localInstance = new UserServiceImpl(getEntityManager(), getUserDAO());
                }
            }
        }
        return localInstance;
    }
}
