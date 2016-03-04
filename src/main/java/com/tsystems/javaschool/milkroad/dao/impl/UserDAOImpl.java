package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
public class UserDAOImpl extends DAOImpl<UserEntity, Long> implements UserDAO<UserEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public UserDAOImpl(final EntityManager entityManager) {
        super(entityManager, UserEntity.class);
    }

    @Override
    public UserEntity getByEmail(final String email) throws MilkroadDAOException {
        try {
            final TypedQuery<UserEntity> entityTypedQuery =
                    entityManager.createNamedQuery("UserEntity.findByEmail", UserEntity.class);
            entityTypedQuery.setParameter("email", email);
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.warn("No users found with email " + email);
            return null;
        } catch (final Exception e1) {
            LOGGER.error("Error on find user by email = " + email + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }

    @Override
    public List<Pair<UserEntity, BigDecimal>> getTopCustomers(final int count) throws MilkroadDAOException {
        try {
            final TypedQuery<Object[]> entityTypedQuery =
                    entityManager.createNamedQuery("OrderEntity.getTopCustomers", Object[].class);
            final List<Pair<UserEntity, BigDecimal>> topCustomers = new ArrayList<>();
            final List<Object[]> result = entityTypedQuery.setMaxResults(count).getResultList();
            for (final Object[] object : result) {
                topCustomers.add(new Pair<>((UserEntity) object[0], (BigDecimal) object[1]));
            }
            return topCustomers;
        } catch (final NoResultException e) {
            LOGGER.warn("No users found");
            return Collections.emptyList();
        } catch (final Exception e1) {
            LOGGER.error("Error on find top customers " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
