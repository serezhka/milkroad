package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

import java.util.List;

/**
 * Created by Sergey on 20.02.2016.
 */
public interface AddressDAO<T, K> extends DAO<T, K> {
    List<T> getByUserEmail(final String email) throws MilkroadDAOException;
}
