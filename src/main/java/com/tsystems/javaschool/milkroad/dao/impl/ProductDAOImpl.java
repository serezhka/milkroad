package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
public class ProductDAOImpl extends DAOImpl<ProductEntity, Long> implements ProductDAO<ProductEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(ProductDAOImpl.class);

    public ProductDAOImpl(final EntityManager entityManager) {
        super(entityManager, ProductEntity.class);
    }

    @Override
    public List<ProductEntity> getAllByCategory(final String category) throws MilkroadDAOException {
        try {
            final TypedQuery<ProductEntity> entityTypedQuery =
                    entityManager.createNamedQuery("ProductEntity.findAllByCategory", ProductEntity.class);
            entityTypedQuery.setParameter("category", category);
            return entityTypedQuery.getResultList();
        } catch (final NoResultException e) {
            final String message = "No products found with category " + category;
            LOGGER.warn(message);
            return Collections.emptyList();
        } catch (final Exception e1) {
            LOGGER.error("Error on find products by category = " + category + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
