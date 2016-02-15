package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.model.OrderEntity;

import javax.persistence.EntityManager;

/**
 * Created by Sergey on 15.02.2016.
 */
public class OrderDAOImpl extends DAOImpl<OrderEntity, Long> implements OrderDAO<OrderEntity, Long> {
    public OrderDAOImpl(final EntityManager entityManager) {
        super(entityManager, OrderEntity.class);
    }
}
