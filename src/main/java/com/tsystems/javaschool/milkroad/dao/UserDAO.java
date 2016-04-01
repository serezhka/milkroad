package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Sergey on 10.02.2016.
 */
public interface UserDAO<T, K> extends DAO<T, K> {
    T getByEmail(final String email) throws MilkroadDAOException;

    Map<T, BigDecimal> getTopCustomers(final int count) throws MilkroadDAOException;
}
