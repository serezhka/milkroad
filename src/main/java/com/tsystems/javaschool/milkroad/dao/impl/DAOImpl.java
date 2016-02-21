package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.DAO;
import com.tsystems.javaschool.milkroad.dao.exception.DAOErrorType;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
public abstract class DAOImpl<T, K> implements DAO<T, K> {
    private static final Logger LOGGER = Logger.getLogger(DAOImpl.class);

    protected final Class<T> entityClass;
    protected EntityManager entityManager;

    public DAOImpl(final EntityManager entityManager, final Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public void persist(final T entity) throws MilkroadDAOException {
        try {
            entityManager.persist(entity);
        } catch (final Exception e) {
            LOGGER.error("Error on persist entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, DAOErrorType.PERSIST_ERROR);
        }
    }

    public void merge(final T entity) throws MilkroadDAOException {
        try {
            entityManager.merge(entity);
        } catch (final Exception e) {
            LOGGER.error("Error on merge entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, DAOErrorType.MERGE_ERROR);
        }
    }

    public void remove(final T entity) throws MilkroadDAOException {
        try {
            entityManager.remove(entity);
        } catch (final Exception e) {
            LOGGER.error("Error on remove entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, DAOErrorType.REMOVE_ERROR);
        }
    }

    public T getByID(final K id) throws MilkroadDAOException {
        try {
            return entityManager.find(entityClass, id);
        } catch (final Exception e) {
            LOGGER.error("Error on load entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, DAOErrorType.FIND_ERROR);
        }
    }

    public List<T> getAll() throws MilkroadDAOException {
        final List<T> entities;
        try {
            //noinspection unchecked
            entities = entityManager.createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o").getResultList();
        } catch (final Exception e) {
            LOGGER.error("Error on load entities " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, DAOErrorType.FIND_ERROR);
        }
        return new ArrayList<>(entities);
    }
}
