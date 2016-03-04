package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.AddressDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.AddressEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 20.02.2016.
 */
public class AddressDAOImpl extends DAOImpl<AddressEntity, Long> implements AddressDAO<AddressEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(AddressDAOImpl.class);

    public AddressDAOImpl(final EntityManager entityManager) {
        super(entityManager, AddressEntity.class);
    }

    @Override
    public List<AddressEntity> getByUserEmail(final String email) throws MilkroadDAOException {
        try {
            final TypedQuery<AddressEntity> entityTypedQuery =
                    entityManager.createNamedQuery("AddressEntity.findAddressesByUserEmail", AddressEntity.class);
            entityTypedQuery.setParameter("email", email);
            return entityTypedQuery.getResultList();
        } catch (final NoResultException e) {
            LOGGER.warn("No addresses with user email = " + email);
            return Collections.emptyList();
        } catch (final Exception e1) {
            LOGGER.error("Error on find address by user email = " + email + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
