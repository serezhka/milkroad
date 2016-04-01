package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 14.02.2016.
 */
public interface ProductDAO<T, K> extends DAO<T, K> {
    List<T> getAllByCategory(final String category) throws MilkroadDAOException;

    Map<T, Integer> getTopProducts(final int count) throws MilkroadDAOException;

    /**
     * @param pattern - full (or part of) product name
     */
    List<T> searchByName(final String pattern) throws MilkroadDAOException;
}
