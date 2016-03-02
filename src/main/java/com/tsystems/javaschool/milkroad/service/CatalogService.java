package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public interface CatalogService {
    List<AttributeDTO> getAllAttributes() throws MilkroadServiceException;

    List<CategoryDTO> getAllCategories() throws  MilkroadServiceException;

    List<ProductDTO> getAllProducts() throws MilkroadServiceException;

    List<ProductDTO> getAllProductsByCategory(final String category) throws MilkroadServiceException;

    ProductDTO getProductByArticle(final String article) throws MilkroadServiceException;

    CategoryDTO updateCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException;

    CategoryDTO createCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException;

    AttributeDTO updateAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException;

    AttributeDTO createAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException;
}
