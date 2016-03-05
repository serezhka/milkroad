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
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 03.03.2016.
 */
public class StatisticsServiceImpl extends AbstractService implements StatisticsService {
    private static final Logger LOGGER = Logger.getLogger(StatisticsServiceImpl.class);

    private final ProductDAO<ProductEntity, Long> productDAO;
    private final UserDAO<UserEntity, Long> userDAO;
    private final OrderDAO<OrderEntity, Long> orderDAO;

    public StatisticsServiceImpl(final EntityManager entityManager,
                                 final ProductDAO<ProductEntity, Long> productDAO,
                                 final UserDAO<UserEntity, Long> userDAO,
                                 final OrderDAO<OrderEntity, Long> orderDAO) {
        super(entityManager);
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    public List<Pair<ProductDTO, Integer>> getTopProducts(final int count) throws MilkroadServiceException {
        final List<Pair<ProductDTO, Integer>> topProductDTOs = new ArrayList<>();
        try {
            final List<Pair<ProductEntity, Integer>> topProductEntities = productDAO.getTopProducts(count);
            for (final Pair<ProductEntity, Integer> topProductEntity : topProductEntities) {
                topProductDTOs.add(new Pair<>(new ProductDTO(topProductEntity.getKey()), topProductEntity.getValue()));
            }
            return topProductDTOs;
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading top products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }

    @Override
    public List<Pair<UserDTO, BigDecimal>> getTopCustomers(final int count) throws MilkroadServiceException {
        final List<Pair<UserDTO, BigDecimal>> topCustomerDTOs = new ArrayList<>();
        try {
            final List<Pair<UserEntity, BigDecimal>> topCustomersEntities = userDAO.getTopCustomers(count);
            for (final Pair<UserEntity, BigDecimal> topCustomersEntity : topCustomersEntities) {
                topCustomerDTOs.add(new Pair<>(new UserDTO(topCustomersEntity.getKey()), topCustomersEntity.getValue()));
            }
            return topCustomerDTOs;
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading top customers");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }

    @Override
    public BigDecimal getTotalCash() throws MilkroadServiceException {
        try {
            return orderDAO.getTotalCash();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while getting total cash");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }

    @Override
    public BigDecimal getTotalCashByPeriod(final Date from, final Date to) throws MilkroadServiceException {
        try {
            return orderDAO.getTotalCash();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while getting total cash by period from " + from + " to " + to);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }
}
