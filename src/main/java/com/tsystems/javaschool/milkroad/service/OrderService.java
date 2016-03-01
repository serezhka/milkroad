package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
public interface OrderService {
    List<OrderDTO> getAllOrders() throws MilkroadServiceException;

    OrderDTO createOrder(final OrderDTO orderDTO) throws MilkroadServiceException;

    List<OrderDTO> getOrdersByUser(final UserDTO userDTO) throws MilkroadServiceException;

    OrderDTO updateOrder(final OrderDTO orderDTO) throws MilkroadServiceException;
}
