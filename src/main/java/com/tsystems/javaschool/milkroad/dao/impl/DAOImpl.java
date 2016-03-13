package com.tsystems.javaschool.milkroad.dao.impl;

import com.tsystems.javaschool.milkroad.dao.DAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
@Repository
public abstract class DAOImpl<T, K> implements DAO<T, K> {
    private static final Logger LOGGER = Logger.getLogger(DAOImpl.class);

    protected final Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public DAOImpl(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void persist(final T entity) throws MilkroadDAOException {
        try {
            getEntityManager().persist(entity);
        } catch (final Exception e) {
            LOGGER.error("Error on persist entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, MilkroadDAOException.Type.PERSIST_ERROR);
        }
    }

    public void merge(final T entity) throws MilkroadDAOException {
        try {
            getEntityManager().merge(entity);
        } catch (final Exception e) {
            LOGGER.error("Error on merge entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, MilkroadDAOException.Type.MERGE_ERROR);
        }
    }

    public void remove(final T entity) throws MilkroadDAOException {
        try {
            getEntityManager().remove(entity);
        } catch (final Exception e) {
            LOGGER.error("Error on remove entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, MilkroadDAOException.Type.REMOVE_ERROR);
        }
    }

    public T getByID(final K id) throws MilkroadDAOException {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (final Exception e) {
            LOGGER.error("Error on load entity " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, MilkroadDAOException.Type.FIND_ERROR);
        }
    }

    public List<T> getAll() throws MilkroadDAOException {
        final List<T> entities;
        try {
            //noinspection unchecked
            entities = getEntityManager().createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o").getResultList();
        } catch (final Exception e) {
            LOGGER.error("Error on load entities " + entityClass.getSimpleName());
            throw new MilkroadDAOException(e, MilkroadDAOException.Type.FIND_ERROR);
        }
        return new ArrayList<>(entities);
    }
}
