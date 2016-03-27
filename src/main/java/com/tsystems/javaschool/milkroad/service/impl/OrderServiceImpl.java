package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.AddressDAO;
import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.*;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDAO<OrderEntity, Long> orderDAO;
    private final UserDAO<UserEntity, Long> userDAO;
    private final AddressDAO<AddressEntity, Long> addressDAO;
    private final ProductDAO<ProductEntity, Long> productDAO;

    @Autowired
    public OrderServiceImpl(final OrderDAO<OrderEntity, Long> orderDAO,
                            final UserDAO<UserEntity, Long> userDAO,
                            final AddressDAO<AddressEntity, Long> addressDAO,
                            final ProductDAO<ProductEntity, Long> productDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public List<OrderDTO> getAllOrders() throws MilkroadServiceException {
        final List<OrderEntity> orderEntities;
        try {
            orderEntities = orderDAO.getAll();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading orders");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final List<OrderDTO> orderDTOs = new ArrayList<>();
        for (final OrderEntity orderEntity : orderEntities) {
            orderDTOs.add(EntityDTOConverter.orderDTO(orderEntity));
        }
        return orderDTOs;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(final OrderDTO orderDTO) throws MilkroadServiceException {
        final OrderEntity orderEntity = new OrderEntity();
        try {
            final UserEntity userEntity = userDAO.getByID(orderDTO.getCustomer().getId());
            if (userEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
            }
            final Long addressId = orderDTO.getAddress().getId();
            if (addressId != null) {
                final AddressEntity addressEntity = addressDAO.getByID(orderDTO.getAddress().getId());
                if (addressEntity == null) {
                    throw new MilkroadServiceException(MilkroadServiceException.Type.ADDRESS_NOT_EXISTS);
                }
                orderEntity.setAddress(addressEntity);
            }
            orderEntity.setId(orderDTO.getId());
            orderEntity.setCustomer(userEntity);
            orderEntity.setPriceTotal(orderDTO.getTotalPrice());
            orderEntity.setPaymentMethod(orderDTO.getPaymentMethod());
            orderEntity.setPaymentStatus(orderDTO.getPaymentStatus());
            orderEntity.setShippingMethod(orderDTO.getShippingMethod());
            orderEntity.setShippingStatus(orderDTO.getShippingStatus());
            orderEntity.setDate(orderDTO.getDate());
            orderEntity.setNote(orderDTO.getNote());
            for (final OrderDTO.Detail detail : orderDTO.getDetails()) {
                final OrderDetailEntity detailEntity = new OrderDetailEntity();
                final ProductEntity productEntity = productDAO.getByID(detail.getProduct().getArticle());
                if (detail.getCount() > productEntity.getRemainCount()) {
                    throw new MilkroadServiceException(MilkroadServiceException.Type.PRODUCT_NOT_ENOUGH);
                } else {
                    productEntity.setRemainCount(productEntity.getRemainCount() - detail.getCount());
                }
                detailEntity.setProduct(productEntity);
                detailEntity.setProductCount(detail.getCount());
                detailEntity.setPriceTotal(detail.getTotalPrice());
                orderEntity.addOrderDetail(detailEntity);
            }
            orderDAO.persist(orderEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating order for user with email = " + orderDTO.getCustomer().getEmail());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.orderDTO(orderEntity);
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrdersByUser(final UserDTO userDTO) throws MilkroadServiceException {
        final List<OrderDTO> orderDTOs = new ArrayList<>();
        try {
            final UserEntity userEntity = userDAO.getByID(userDTO.getId());
            final List<OrderEntity> orderEntities = userEntity.getOrders();
            for (final OrderEntity orderEntity : orderEntities) {
                orderDTOs.add(EntityDTOConverter.orderDTO(orderEntity));
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading orders for user with email = " + userDTO.getEmail());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return orderDTOs;
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(final OrderDTO orderDTO) throws MilkroadServiceException {
        final OrderEntity orderEntity;
        try {
            orderEntity = orderDAO.getByID(orderDTO.getId());
            orderEntity.setPaymentStatus(orderDTO.getPaymentStatus());
            orderEntity.setShippingStatus(orderDTO.getShippingStatus());
            orderDAO.merge(orderEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while update order with id = " + orderDTO.getId());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.orderDTO(orderEntity);
    }
}
