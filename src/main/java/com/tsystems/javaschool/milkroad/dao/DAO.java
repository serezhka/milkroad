package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
public interface DAO<T, K> {
    void persist(T entity) throws MilkroadDAOException;

    void merge(T entity) throws MilkroadDAOException;

    void remove(T entity) throws MilkroadDAOException;

    T getByID(K id) throws MilkroadDAOException;

    List<T> getAll() throws MilkroadDAOException;
}
