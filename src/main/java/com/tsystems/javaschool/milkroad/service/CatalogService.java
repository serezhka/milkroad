package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public interface CatalogService {
    List<AttributeDTO> getAllAttributes() throws MilkroadServiceException;

    List<CategoryDTO> getAllCategories() throws MilkroadServiceException;

    List<ProductDTO> getAllProducts() throws MilkroadServiceException;

    List<ProductDTO> getAllProductsByCategory(final String category) throws MilkroadServiceException;

    ProductDTO getProductByArticle(final Long article) throws MilkroadServiceException;

    CategoryDTO updateCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException;

    CategoryDTO createCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException;

    AttributeDTO updateAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException;

    AttributeDTO createAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException;

    /**
     * @param parameters - product parameters in "attribute_id|attribute_value" format
     */
    ProductDTO updateProduct(final ProductDTO productDTO, final Long categoryID, final String[] parameters) throws MilkroadServiceException;

    /**
     * @param parameters - product parameters in "attribute_id|attribute_value" format
     */
    ProductDTO createProduct(final UserDTO userDTO, final ProductDTO productDTO, final Long categoryID, final String[] parameters) throws MilkroadServiceException;

    /**
     * @param pattern - full (or part of) product name
     */
    List<ProductDTO> searchProductByName(final String pattern) throws MilkroadServiceException;
}
