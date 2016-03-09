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

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
public class OrderServiceImpl extends AbstractService implements OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDAO<OrderEntity, Long> orderDAO;
    private final UserDAO<UserEntity, Long> userDAO;
    private final AddressDAO<AddressEntity, Long> addressDAO;
    private final ProductDAO<ProductEntity, Long> productDAO;

    public OrderServiceImpl(final EntityManager entityManager,
                            final OrderDAO<OrderEntity, Long> orderDAO,
                            final UserDAO<UserEntity, Long> userDAO,
                            final AddressDAO<AddressEntity, Long> addressDAO,
                            final ProductDAO<ProductEntity, Long> productDAO) {
        super(entityManager);
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
        this.productDAO = productDAO;
    }

    @Override
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
            entityManager.refresh(orderEntity);
            orderDTOs.add(EntityDTOConverter.orderDTO(orderEntity));
        }
        return orderDTOs;
    }

    @Override
    public OrderDTO createOrder(final OrderDTO orderDTO) throws MilkroadServiceException {
        final OrderEntity orderEntity = new OrderEntity();
        try {
            entityManager.getTransaction().begin();
            final UserEntity userEntity = userDAO.getByID(orderDTO.getCustomer().getId());
            if (userEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
            }
            final AddressEntity addressEntity = addressDAO.getByID(orderDTO.getAddress().getId());
            if (addressEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.ADDRESS_NOT_EXISTS);
            }
            orderEntity.setId(orderDTO.getId());
            orderEntity.setCustomer(userEntity);
            orderEntity.setAddress(addressEntity);
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
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating order for user with email = " + orderDTO.getCustomer().getEmail());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return EntityDTOConverter.orderDTO(orderEntity);
    }

    @Override
    public List<OrderDTO> getOrdersByUser(final UserDTO userDTO) throws MilkroadServiceException {
        final List<OrderDTO> orderDTOs = new ArrayList<>();
        try {
            final UserEntity userEntity = userDAO.getByID(userDTO.getId());
            entityManager.refresh(userEntity);
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
    public OrderDTO updateOrder(final OrderDTO orderDTO) throws MilkroadServiceException {
        final OrderEntity orderEntity;
        try {
            entityManager.getTransaction().begin();
            orderEntity = orderDAO.getByID(orderDTO.getId());
            orderEntity.setPaymentMethod(orderDTO.getPaymentMethod());
            orderEntity.setPaymentStatus(orderDTO.getPaymentStatus());
            orderEntity.setShippingMethod(orderDTO.getShippingMethod());
            orderEntity.setShippingStatus(orderDTO.getShippingStatus());
            orderDAO.merge(orderEntity);
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while update order with id = " + orderDTO.getId());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return EntityDTOConverter.orderDTO(orderEntity);
    }
}
