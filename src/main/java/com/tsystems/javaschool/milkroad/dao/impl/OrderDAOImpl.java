package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.model.MrOrderEntity;

import javax.persistence.EntityManager;

/**
 * Created by Sergey on 15.02.2016.
 */
public class OrderDAOImpl extends DAOImpl<MrOrderEntity, Long> implements OrderDAO<MrOrderEntity, Long> {
    public OrderDAOImpl(final EntityManager entityManager) {
        super(entityManager, MrOrderEntity.class);
    }
}
