package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.StatisticsService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 03.03.2016.
 */
public class StatisticsServiceImpl extends AbstractService implements StatisticsService {
    private static final Logger LOGGER = Logger.getLogger(StatisticsServiceImpl.class);

    private final ProductDAO<ProductEntity, Long> productDAO;
    private final UserDAO<UserEntity, Long> userDAO;

    public StatisticsServiceImpl(final EntityManager entityManager,
                                 final ProductDAO<ProductEntity, Long> productDAO,
                                 final UserDAO<UserEntity, Long> userDAO) {
        super(entityManager);
        this.productDAO = productDAO;
        this.userDAO = userDAO;
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
    public List<UserDTO> getTopCustomers(final int count) throws MilkroadServiceException {
        final List<UserDTO> userDTOs = new ArrayList<>();
        try {
            final List<UserEntity> userEntities = userDAO.getTopCustomers(count);
            for (final UserEntity userEntity : userEntities) {
                userDTOs.add(new UserDTO(userEntity));
            }
            return userDTOs;
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading top customers");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }
}
