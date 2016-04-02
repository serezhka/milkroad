package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.OrderEntity;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.StatisticsService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sergey on 03.03.2016.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger LOGGER = Logger.getLogger(StatisticsServiceImpl.class);

    private final ProductDAO<ProductEntity, Long> productDAO;
    private final UserDAO<UserEntity, Long> userDAO;
    private final OrderDAO<OrderEntity, Long> orderDAO;

    @Autowired
    public StatisticsServiceImpl(final ProductDAO<ProductEntity, Long> productDAO,
                                 final UserDAO<UserEntity, Long> userDAO,
                                 final OrderDAO<OrderEntity, Long> orderDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Map<ProductDTO, Integer> getTopProducts(final int count) throws MilkroadServiceException {
        final Map<ProductEntity, Integer> topProductEntities;
        try {
            topProductEntities = productDAO.getTopProducts(count);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading top products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final Map<ProductDTO, Integer> topProductDTOs = new LinkedHashMap<>();
        for (final Map.Entry<ProductEntity, Integer> productEntityIntegerEntry : topProductEntities.entrySet()) {
            topProductDTOs.put(EntityDTOConverter.productDTO(productEntityIntegerEntry.getKey()),
                    productEntityIntegerEntry.getValue());
        }
        return topProductDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Map<UserDTO, BigDecimal> getTopCustomers(final int count) throws MilkroadServiceException {
        final Map<UserEntity, BigDecimal> topCustomersEntities;
        try {
            topCustomersEntities = userDAO.getTopCustomers(count);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading top customers");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final Map<UserDTO, BigDecimal> topCustomerDTOs = new LinkedHashMap<>();
        for (final Map.Entry<UserEntity, BigDecimal> userEntityBigDecimalEntry : topCustomersEntities.entrySet()) {
            topCustomerDTOs.put(EntityDTOConverter.userDTO(userEntityBigDecimalEntry.getKey()),
                    userEntityBigDecimalEntry.getValue());
        }
        return topCustomerDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BigDecimal getTotalCash() throws MilkroadServiceException {
        try {
            return orderDAO.getTotalCash();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while getting total cash");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BigDecimal getTotalCashByPeriod(final Date from, final Date to) throws MilkroadServiceException {
        try {
            return orderDAO.getTotalCashByPeriod(from, to);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while getting total cash by period from " + from + " to " + to);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }
}
