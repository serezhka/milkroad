package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public interface ProductService {
    List<ProductDTO> getAllProducts() throws MilkroadServiceException;
}
