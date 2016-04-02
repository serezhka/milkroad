package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 10.02.2016.
 */
@Repository
public class UserDAOImpl extends DAOImpl<UserEntity, Long> implements UserDAO<UserEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public UserDAOImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity getByEmail(final String email) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<UserEntity> entityTypedQuery =
                    entityManager.createNamedQuery("UserEntity.findByEmail", UserEntity.class);
            entityTypedQuery.setParameter("email", email);
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.warn(e);
            LOGGER.warn("No users found with email " + email);
            return null;
        } catch (final Exception e1) {
            LOGGER.error(e1);
            LOGGER.error("Error on find user by email = " + email + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }

    @Override
    public Map<UserEntity, BigDecimal> getTopCustomers(final int count) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<Object[]> entityTypedQuery =
                    entityManager.createNamedQuery("OrderEntity.getTopCustomers", Object[].class);
            final Map<UserEntity, BigDecimal> topCustomers = new LinkedHashMap<>();
            final List<Object[]> result = entityTypedQuery.setMaxResults(count).getResultList();
            for (final Object[] object : result) {
                topCustomers.put((UserEntity) object[0], (BigDecimal) object[1]);
            }
            return topCustomers;
        } catch (final NoResultException e) {
            LOGGER.warn(e);
            LOGGER.warn("No users found");
            return Collections.emptyMap();
        } catch (final Exception e1) {
            LOGGER.error(e1);
            LOGGER.error("Error on find top customers " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
