package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

import java.util.List;

/**
 * Created by Sergey on 02.03.2016.
 */
public interface ParameterDAO<T, K> extends DAO<T, K> {
    List<T> getByProductID(final K productID) throws MilkroadDAOException;

    T getByProductIDAndAttrID(final K productID, final K attrID) throws MilkroadDAOException;
}
