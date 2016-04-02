package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Order service interface.
 * Provides orders review and management features
 * <p>
 * Created by Sergey on 15.02.2016.
 */
public interface OrderService {

    /**
     * Returns all orders
     *
     * @return list of orders
     * @throws MilkroadServiceException if an error with database occurs
     */
    List<OrderDTO> getAllOrders() throws MilkroadServiceException;

    /**
     * Creates new order
     *
     * @param orderDTO order details
     * @return created order info
     * @throws MilkroadServiceException if customer or address doesn't exist,
     *                                  if product doesn't exist or isn't enough,
     *                                  if an error with database occurs
     */
    OrderDTO createOrder(final OrderDTO orderDTO) throws MilkroadServiceException;

    /**
     * Returns all order by certain user
     *
     * @param userDTO user, id field is required
     * @return list of orders
     * @throws MilkroadServiceException if customer or an error with database occurs
     */
    List<OrderDTO> getOrdersByUser(final UserDTO userDTO) throws MilkroadServiceException;

    /**
     * Updates order details
     *
     * @param orderDTO order details, id field is required
     * @return updated order info
     * @throws MilkroadServiceException if order doesn't exist or an error with database occurs
     */
    OrderDTO updateOrder(final OrderDTO orderDTO) throws MilkroadServiceException;
}
