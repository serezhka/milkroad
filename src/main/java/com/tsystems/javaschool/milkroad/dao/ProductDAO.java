package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public interface ProductDAO<T, K> extends DAO<T, K> {
    List<T> getAllByCategory(final String category) throws MilkroadDAOException;
}
