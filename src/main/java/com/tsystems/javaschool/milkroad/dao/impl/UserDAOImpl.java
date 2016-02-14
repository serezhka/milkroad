package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.MrUserEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Sergey on 10.02.2016.
 */
public class UserDAOImpl extends DAOImpl<MrUserEntity, Long> implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public UserDAOImpl(final EntityManager entityManager) {
        super(entityManager, MrUserEntity.class);
    }

    @Override
    public MrUserEntity getByEmail(final String email) throws MilkroadDAOException {
        try {
            final TypedQuery<MrUserEntity> entityTypedQuery = entityManager.createNamedQuery("MrUserEntity.findByEmail", MrUserEntity.class);
            entityTypedQuery.setParameter("email", email);
            final MrUserEntity userEntity = entityTypedQuery.getSingleResult();
            userEntity.getAdresses(); // 'cos Addresses have FetchType.LAZY
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            final String message = "No users found with email " + email;
            LOGGER.info(message);
            throw new MilkroadDAOException(message);
        } catch (final Exception e1) {
            LOGGER.warn("Error on find user by email " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1);
        }
    }
}
