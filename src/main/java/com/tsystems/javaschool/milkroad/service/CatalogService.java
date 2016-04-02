package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Catalog service interface.
 * Provides interaction with product catalog and it's management
 * <p>
 * Created by Sergey on 14.02.2016.
 */
public interface CatalogService {

    /**
     * Returns all available product attributes
     *
     * @return list of attributes
     * @throws MilkroadServiceException if an error with database occurs
     */
    List<AttributeDTO> getAllAttributes() throws MilkroadServiceException;

    /**
     * Returns all available product categories
     *
     * @return list of categories
     * @throws MilkroadServiceException if an error with database occurs
     */
    List<CategoryDTO> getAllCategories() throws MilkroadServiceException;

    /**
     * Returns all products
     *
     * @return list of products
     * @throws MilkroadServiceException if an error with database occurs
     */
    List<ProductDTO> getAllProducts() throws MilkroadServiceException;

    /**
     * Returns all products in certain category
     *
     * @param category product category
     * @return list of products
     * @throws MilkroadServiceException if category doesn't exist or an error with database occurs
     */
    // TODO Replace with CategoryDTO and search it by category ID
    List<ProductDTO> getAllProductsByCategory(final String category) throws MilkroadServiceException;

    /**
     * Returns products by it's article
     *
     * @param article product article
     * @return product
     * @throws MilkroadServiceException if product doesn't exist or an error with database occurs
     */
    ProductDTO getProductByArticle(final Long article) throws MilkroadServiceException;

    /**
     * Updates category details
     *
     * @param categoryDTO category details, id field is required
     * @return updated category info
     * @throws MilkroadServiceException if category doesn't exist or an error with database occurs
     */
    CategoryDTO updateCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException;

    /**
     * Creates new category
     *
     * @param categoryDTO category details
     * @return created category info
     * @throws MilkroadServiceException if category already exists or an error with database occurs
     */
    CategoryDTO createCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException;

    /**
     * Updates attribute details
     *
     * @param attributeDTO attribute details, id field is required
     * @return updated attribute info
     * @throws MilkroadServiceException if attribute doesn't exist or an error with database occurs
     */
    AttributeDTO updateAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException;

    /**
     * Creates new attribute
     *
     * @param attributeDTO attribute details
     * @return created attribute info
     * @throws MilkroadServiceException if attribute already exists or an error with database occurs
     */
    AttributeDTO createAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException;

    /**
     * Updates product details
     *
     * @param productDTO product details, id field is required
     * @return updated product info
     * @throws MilkroadServiceException if product, category or one of attributes doesn't exist
     *                                  or an error with database occurs
     */
    ProductDTO updateProduct(final ProductDTO productDTO) throws MilkroadServiceException;

    /**
     * Creates new product
     *
     * @param productDTO product details
     * @return created product info
     * @throws MilkroadServiceException if seller, category or one of attributes doesn't exist
     *                                  or an error with database occurs
     */
    ProductDTO createProduct(final ProductDTO productDTO) throws MilkroadServiceException;

    /**
     * Returns products by name pattern
     *
     * @param pattern part of product name
     * @return list of matched products
     * @throws MilkroadServiceException if an error with database occurs
     */
    List<ProductDTO> searchProductByName(final String pattern) throws MilkroadServiceException;
}
