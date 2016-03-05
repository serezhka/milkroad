package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Sergey on 15.02.2016.
 */
public interface OrderDAO<T, K> extends DAO<T, K> {
    BigDecimal getTotalCash() throws MilkroadDAOException;

    BigDecimal getTotalCashByPeriod(final Date from, final Date to) throws MilkroadDAOException;
}
