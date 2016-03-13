package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
@Repository
public class ProductDAOImpl extends DAOImpl<ProductEntity, Long> implements ProductDAO<ProductEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(ProductDAOImpl.class);

    public ProductDAOImpl() {
        super(ProductEntity.class);
    }

    @Override
    public List<ProductEntity> getAllByCategory(final String category) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<ProductEntity> entityTypedQuery =
                    entityManager.createNamedQuery("ProductEntity.findAllByCategory", ProductEntity.class);
            entityTypedQuery.setParameter("category", category);
            return entityTypedQuery.getResultList();
        } catch (final NoResultException e) {
            LOGGER.warn("No products found with category " + category);
            return Collections.emptyList();
        } catch (final Exception e1) {
            LOGGER.error("Error on find products by category = " + category + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }

    @Override
    public List<Pair<ProductEntity, Integer>> getTopProducts(final int count) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<Object[]> entityTypedQuery =
                    entityManager.createNamedQuery("OrderDetailEntity.getTopProducts", Object[].class);
            final List<Pair<ProductEntity, Integer>> topProducts = new ArrayList<>();
            final List<Object[]> result = entityTypedQuery.setMaxResults(count).getResultList();
            for (final Object[] object : result) {
                topProducts.add(new Pair<>((ProductEntity) object[0], (int) (long) object[1]));
            }
            return topProducts;
        } catch (final NoResultException e) {
            LOGGER.warn("No products found");
            return Collections.emptyList();
        } catch (final Exception e1) {
            LOGGER.error("Error on find top products with count =" + count + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }

    @Override
    public List<ProductEntity> searchByName(final String pattern) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<ProductEntity> entityTypedQuery =
                    entityManager.createNamedQuery("ProductEntity.findByPattern", ProductEntity.class);
            entityTypedQuery.setParameter("pattern", "%" + pattern.toLowerCase() + "%");
            return entityTypedQuery.getResultList();
        } catch (final NoResultException e) {
            LOGGER.warn("No products found by pattern " + pattern);
            return Collections.emptyList();
        } catch (final Exception e1) {
            LOGGER.error("Error on find products by pattern = " + pattern + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
