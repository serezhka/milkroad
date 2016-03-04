package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by Sergey on 03.03.2016.
 */
public interface StatisticsService {
    List<Pair<ProductDTO, Integer>> getTopProducts(final int count) throws MilkroadServiceException;

    List<UserDTO> getTopCustomers(final int count) throws MilkroadServiceException;
}
