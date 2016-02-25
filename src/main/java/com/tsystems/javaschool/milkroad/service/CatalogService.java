package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public interface CatalogService {
    List<CategoryDTO> getAllCategories() throws  MilkroadServiceException;

    List<ProductDTO> getAllProducts() throws MilkroadServiceException;

    List<ProductDTO> getAllProductsByCategory(final String category) throws MilkroadServiceException;

    ProductDTO getProductByArticle(final String article) throws MilkroadServiceException;
}
