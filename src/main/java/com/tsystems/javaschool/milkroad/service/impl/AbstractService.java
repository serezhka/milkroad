package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;

import javax.persistence.EntityManager;

/**
 * Created by Sergey on 14.02.2016.
 */
public abstract class AbstractService {
    protected final EntityManager entityManager = MilkroadAppContext.getInstance().getEntityManager();

    protected AbstractService(final EntityManager entityManager) {
        /*this.entityManager = entityManager;*/
    }
}
