package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

/**
 * Statistics service interface.
 * Provides statistics review
 * <p>
 * Created by Sergey on 03.03.2016.
 */
public interface StatisticsService {

    /**
     * Returns top products
     *
     * @param count required product count
     * @return map [ product , sales count ]
     * @throws MilkroadServiceException if an error with database occurs
     */
    Map<ProductDTO, Integer> getTopProducts(final int count) throws MilkroadServiceException;

    /**
     * Returns top customers
     *
     * @param count required customers count
     * @return map [ customer , total cash ]
     * @throws MilkroadServiceException if an error with database occurs
     */
    Map<UserDTO, BigDecimal> getTopCustomers(final int count) throws MilkroadServiceException;

    /**
     * Returns total cash during all time
     *
     * @return total cash
     * @throws MilkroadServiceException if an error with database occurs
     */
    BigDecimal getTotalCash() throws MilkroadServiceException;

    /**
     * Returns total cash by period
     *
     * @param from from date
     * @param to   to date
     * @return total cash by period
     * @throws MilkroadServiceException if an error with database occurs
     */
    BigDecimal getTotalCashByPeriod(final Date from, final Date to) throws MilkroadServiceException;
}
