package com.tsystems.javaschool.milkroad;

import com.tsystems.javaschool.milkroad.dao.*;
import com.tsystems.javaschool.milkroad.model.*;
import com.tsystems.javaschool.milkroad.service.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Sergey on 11.02.2016.
 */
@Deprecated
public class MilkroadAppContext {

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
    private volatile AddressDAO<AddressEntity, Long> addressDAO;
    private volatile CategoryDAO<ProductCategoryEntity, Long> categoryDAO;
    private volatile AttributeDAO<ProductAttributeEntity, Long> attributeDAO;
    private volatile ParameterDAO<ProductParameterEntity, Long> parameterDAO;

    /* Services */
    private volatile UserService userService;
    private volatile CatalogService catalogService;
    private volatile OrderService orderService;
    private volatile AddressService addressService;
    private volatile StatisticsService statisticsService;

    private MilkroadAppContext() {
    }

    public static MilkroadAppContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

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
//                    userDAO = localInstance = new UserDAOImpl(getEntityManager());
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
//                    productDAO = localInstance = new ProductDAOImpl(getEntityManager());
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
//                    orderDAO = localInstance = new OrderDAOImpl(getEntityManager());
                }
            }
        }
        return localInstance;
    }

    public AddressDAO<AddressEntity, Long> getAddressDAO() {
        AddressDAO<AddressEntity, Long> localInstance = addressDAO;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = addressDAO;
                if (localInstance == null) {
//                    addressDAO = localInstance = new AddressDAOImpl(getEntityManager());
                }
            }
        }
        return localInstance;
    }

    public CategoryDAO<ProductCategoryEntity, Long> getCategoryDAO() {
        CategoryDAO<ProductCategoryEntity, Long> localInstance = categoryDAO;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = categoryDAO;
                if (localInstance == null) {
//                    categoryDAO = localInstance = new CategoryDAOImpl(getEntityManager());
                }
            }
        }
        return localInstance;
    }

    public AttributeDAO<ProductAttributeEntity, Long> getAttributeDAO() {
        AttributeDAO<ProductAttributeEntity, Long> localInstance = attributeDAO;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = attributeDAO;
                if (localInstance == null) {
//                    attributeDAO = localInstance = new AttributeDAOImpl(getEntityManager());
                }
            }
        }
        return localInstance;
    }

    public ParameterDAO<ProductParameterEntity, Long> getParameterDAO() {
        ParameterDAO<ProductParameterEntity, Long> localInstance = parameterDAO;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = parameterDAO;
                if (localInstance == null) {
//                    parameterDAO = localInstance = new ParameterDAOImpl(getEntityManager());
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
//                    userService = localInstance = new UserServiceImpl(getEntityManager(), getUserDAO());
                }
            }
        }
        return localInstance;
    }

    public CatalogService getCatalogService() {
        CatalogService localInstance = catalogService;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = catalogService;
                if (localInstance == null) {
//                    catalogService = localInstance = new CatalogServiceImpl(getEntityManager(),
//                            getAttributeDAO(), getCategoryDAO(), getParameterDAO(), getProductDAO(), getUserDAO());
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
//                    orderService = localinstance
//                            = new OrderServiceImpl(getEntityManager(), getOrderDAO(), getUserDAO(), getAddressDAO(), getProductDAO());
                }
            }
        }
        return localinstance;
    }

    public AddressService getAddressService() {
        AddressService localinstance = addressService;
        if (localinstance == null) {
            synchronized (this) {
                localinstance = addressService;
                if (localinstance == null) {
//                    addressService = localinstance = new AddressServiceImpl(getEntityManager(), getAddressDAO(), getUserDAO());
                }
            }
        }
        return localinstance;
    }

    public StatisticsService getStatisticsService() {
        StatisticsService localInstance = statisticsService;
        if (localInstance == null) {
            synchronized (this) {
                localInstance = statisticsService;
                if (localInstance == null) {
//                    statisticsService = localInstance = new StatisticsServiceImpl(getEntityManager(), getProductDAO(), getUserDAO(), getOrderDAO());
                }
            }
        }
        return localInstance;
    }
}
