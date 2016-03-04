package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.CategoryDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.ProductCategoryEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Sergey on 24.02.2016.
 */
public class CategoryDAOImpl extends DAOImpl<ProductCategoryEntity, Long> implements CategoryDAO<ProductCategoryEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(CategoryDAOImpl.class);

    public CategoryDAOImpl(final EntityManager entityManager) {
        super(entityManager, ProductCategoryEntity.class);
    }

    @Override
    public ProductCategoryEntity getByName(final String name) throws MilkroadDAOException {
        try {
            final TypedQuery<ProductCategoryEntity> entityTypedQuery =
                    entityManager.createNamedQuery("ProductCategoryEntity.findByName", ProductCategoryEntity.class);
            entityTypedQuery.setParameter("name", name);
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.warn("No categories found with name " + name);
            return null;
        } catch (final Exception e1) {
            LOGGER.error("Error on find category by name = " + name + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
