package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.ParameterDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.ProductParameterEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 02.03.2016.
 */
@Repository
public class ParameterDAOImpl extends DAOImpl<ProductParameterEntity, Long> implements ParameterDAO<ProductParameterEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(ParameterDAOImpl.class);

    public ParameterDAOImpl() {
        super(ProductParameterEntity.class);
    }

    @Override
    public List<ProductParameterEntity> getByProductID(final Long productID) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<ProductParameterEntity> entityTypedQuery =
                    entityManager.createNamedQuery("ProductParameterEntity.findAllByProductID", ProductParameterEntity.class);
            entityTypedQuery.setParameter("productID", productID);
            return entityTypedQuery.getResultList();
        } catch (final NoResultException e) {
            LOGGER.warn("No parameters with product id = " + productID);
            return Collections.emptyList();
        } catch (final Exception e1) {
            LOGGER.error("Error on find parameters by product id = " + productID + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }

    @Override
    public ProductParameterEntity getByProductIDAndAttrID(final Long productID, final Long attrID) throws MilkroadDAOException {
        try {
            final EntityManager entityManager = getEntityManager();
            final TypedQuery<ProductParameterEntity> entityTypedQuery =
                    entityManager.createNamedQuery("ProductParameterEntity.findAllByProductIDAndAttrID", ProductParameterEntity.class);
            entityTypedQuery.setParameter("productID", productID);
            entityTypedQuery.setParameter("attrID", attrID);
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.warn("No parameter with product id = " + productID + " and attr id = " + attrID);
            return null;
        } catch (final Exception e1) {
            LOGGER.error("Error on find parameter by product id = " + productID + " and attr id = " + attrID + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
