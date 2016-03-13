package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.AttributeDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.ProductAttributeEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Sergey on 01.03.2016.
 */
@Repository
public class AttributeDAOImpl extends DAOImpl<ProductAttributeEntity, Long> implements AttributeDAO<ProductAttributeEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(AttributeDAOImpl.class);

    public AttributeDAOImpl() {
        super(ProductAttributeEntity.class);
    }

    @Override
    public ProductAttributeEntity getByName(final String name) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<ProductAttributeEntity> entityTypedQuery =
                    entityManager.createNamedQuery("ProductAttributeEntity.findByName", ProductAttributeEntity.class);
            entityTypedQuery.setParameter("name", name);
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.warn("No attributes found with name " + name);
            return null;
        } catch (final Exception e1) {
            LOGGER.error("Error on find attribute by name = " + name + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
