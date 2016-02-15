package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
public interface OrderService {
    List<OrderDTO> getAllOrders() throws MilkroadServiceException;
}
