package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

/**
 * Created by Sergey on 24.02.2016.
 */
public interface CategoryDAO<T, K> extends DAO<T, K> {
    T getByName(final String name) throws MilkroadDAOException;
}
