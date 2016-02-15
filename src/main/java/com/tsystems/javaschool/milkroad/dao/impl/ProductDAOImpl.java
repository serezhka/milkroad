package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.model.MrProductEntity;

import javax.persistence.EntityManager;

/**
 * Created by Sergey on 15.02.2016.
 */
public class ProductDAOImpl extends DAOImpl<MrProductEntity, Long> implements ProductDAO<MrProductEntity, Long> {
    public ProductDAOImpl(final EntityManager entityManager) {
        super(entityManager, MrProductEntity.class);
    }
}
