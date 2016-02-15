package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.model.ProductEntity;

import javax.persistence.EntityManager;

/**
 * Created by Sergey on 15.02.2016.
 */
public class ProductDAOImpl extends DAOImpl<ProductEntity, Long> implements ProductDAO<ProductEntity, Long> {
    public ProductDAOImpl(final EntityManager entityManager) {
        super(entityManager, ProductEntity.class);
    }
}
