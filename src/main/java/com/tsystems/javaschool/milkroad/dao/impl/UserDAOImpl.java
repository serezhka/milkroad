package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
            final String message = "No users found with email " + email;
            LOGGER.warn(message);
            return null;
        } catch (final Exception e1) {
            LOGGER.error("Error on find user by email " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
