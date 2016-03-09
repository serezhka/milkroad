package com.tsystems.javaschool.milkroad.service.impl;

import javax.persistence.EntityManager;

/**
 * Created by Sergey on 14.02.2016.
 */
public abstract class AbstractService {
    protected EntityManager entityManager;

    protected AbstractService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
