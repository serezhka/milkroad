package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

/**
 * Created by Sergey on 03.03.2016.
 */
public interface StatisticsService {
    Map<ProductDTO, Integer> getTopProducts(final int count) throws MilkroadServiceException;

    Map<UserDTO, BigDecimal> getTopCustomers(final int count) throws MilkroadServiceException;

    BigDecimal getTotalCash() throws MilkroadServiceException;

    BigDecimal getTotalCashByPeriod(final Date from, final Date to) throws MilkroadServiceException;
}
