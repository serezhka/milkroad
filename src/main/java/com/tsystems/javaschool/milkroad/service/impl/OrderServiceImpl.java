package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.OrderEntity;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.service.exception.ServiceExceptionType;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
public class OrderServiceImpl extends AbstractService implements OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDAO<OrderEntity, Long> orderDAO;

    public OrderServiceImpl(final EntityManager entityManager, final OrderDAO<OrderEntity, Long> orderDAO) {
        super(entityManager);
        this.orderDAO = orderDAO;
    }

    @Override
    public List<OrderDTO> getAllOrders() throws MilkroadServiceException {
        final List<OrderDTO> orderDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<OrderEntity> orderEntities = orderDAO.getAll();
            for (final OrderEntity orderEntity : orderEntities) {
                orderDTOs.add(new OrderDTO(
                        new UserDTO(orderEntity.getCustomer()), new AddressDTO(orderEntity.getAddress()), orderEntity
                ));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading orders");
            throw new MilkroadServiceException(e, ServiceExceptionType.UNKNOWN_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return orderDTOs;
    }
}
