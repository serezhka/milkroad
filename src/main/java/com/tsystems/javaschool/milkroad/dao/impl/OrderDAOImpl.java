package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.OrderEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Sergey on 15.02.2016.
 */
public class OrderDAOImpl extends DAOImpl<OrderEntity, Long> implements OrderDAO<OrderEntity, Long> {
    private static final Logger LOGGER = Logger.getLogger(OrderDAOImpl.class);

    public OrderDAOImpl(final EntityManager entityManager) {
        super(entityManager, OrderEntity.class);
    }

    @Override
    public BigDecimal getTotalCash() throws MilkroadDAOException {
        try {
            final TypedQuery<BigDecimal> entityTypedQuery =
                    entityManager.createNamedQuery("OrderEntity.getTotalCash", BigDecimal.class);
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.warn("No total cash");
            return null;
        } catch (final Exception e1) {
            LOGGER.error("Error on get total cash " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }

    @Override
    public BigDecimal getTotalCashByPeriod(final Date from, final Date to) throws MilkroadDAOException {
        try {
            final TypedQuery<BigDecimal> entityTypedQuery =
                    entityManager.createNamedQuery("OrderEntity.getTotalCashByPeriod", BigDecimal.class);
            entityTypedQuery.setParameter("from", from);
            entityTypedQuery.setParameter("to", to);
            return entityTypedQuery.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.warn("No total cash by period from " + from + " to " + to);
            return null;
        } catch (final Exception e1) {
            LOGGER.error("Error on get total cash by period from " + from + " to " + to + " " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e1, MilkroadDAOException.Type.FIND_ERROR);
        }
    }
}
