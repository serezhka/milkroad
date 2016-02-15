package com.tsystems.javaschool.milkroad;

import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.impl.OrderDAOImpl;
import com.tsystems.javaschool.milkroad.dao.impl.ProductDAOImpl;
import com.tsystems.javaschool.milkroad.dao.impl.UserDAOImpl;
import com.tsystems.javaschool.milkroad.model.OrderEntity;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.ProductService;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.impl.OrderServiceImpl;
import com.tsystems.javaschool.milkroad.service.impl.ProductServiceImpl;
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
    private volatile UserDAO<UserEntity, Long> userDAO;
    private volatile ProductDAO<ProductEntity, Long> productDAO;
    private volatile OrderDAO<OrderEntity, Long> orderDAO;

    /* Services */
    private volatile UserService userService;
    private volatile ProductService productService;
    private volatile OrderService orderService;

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

    public UserDAO<UserEntity, Long> getUserDAO() {
        UserDAO<UserEntity, Long> localInstance = userDAO;
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

    public ProductDAO<ProductEntity, Long> getProductDAO() {
        ProductDAO<ProductEntity, Long> localInstance = productDAO;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = productDAO;
                if (localInstance == null) {
                    productDAO = localInstance = new ProductDAOImpl(getEntityManager());
                }
            }
        }
        return localInstance;
    }

    public OrderDAO<OrderEntity, Long> getOrderDAO() {
        OrderDAO<OrderEntity, Long> localInstance = orderDAO;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = orderDAO;
                if (localInstance == null) {
                    orderDAO = localInstance = new OrderDAOImpl(getEntityManager());
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

    public ProductService getProductService() {
        ProductService localInstance = productService;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = productService;
                if (localInstance == null) {
                    productService = localInstance = new ProductServiceImpl(getEntityManager(), getProductDAO());
                }
            }
        }
        return localInstance;
    }

    public OrderService getOrderService() {
        OrderService localinstance = orderService;
        if (localinstance == null) {
            synchronized (this) {
                localinstance = orderService;
                if (localinstance == null) {
                    orderService = localinstance = new OrderServiceImpl(getEntityManager(), getOrderDAO());
                }
            }
        }
        return localinstance;
    }
}
