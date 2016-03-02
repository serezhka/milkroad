package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

/**
 * Created by Sergey on 01.03.2016.
 */
public interface AttributeDAO<T, K> extends DAO<T, K> {
    T getByName(final String name) throws MilkroadDAOException;
}