package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.*;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ParameterDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.model.*;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.DTOEntityConverter;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
@Service
public class CatalogServiceImpl implements CatalogService {
    private static final Logger LOGGER = Logger.getLogger(CatalogServiceImpl.class);

    private final AttributeDAO<ProductAttributeEntity, Long> attributeDAO;
    private final CategoryDAO<ProductCategoryEntity, Long> categoryDAO;
    private final ParameterDAO<ProductParameterEntity, Long> parameterDAO;
    private final ProductDAO<ProductEntity, Long> productDAO;
    private final UserDAO<UserEntity, Long> userDAO;

    @Autowired
    public CatalogServiceImpl(final AttributeDAO<ProductAttributeEntity, Long> attributeDAO,
                              final CategoryDAO<ProductCategoryEntity, Long> categoryDAO,
                              final ParameterDAO<ProductParameterEntity, Long> parameterDAO,
                              final ProductDAO<ProductEntity, Long> productDAO,
                              final UserDAO<UserEntity, Long> userDAO) {
        this.attributeDAO = attributeDAO;
        this.categoryDAO = categoryDAO;
        this.parameterDAO = parameterDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<AttributeDTO> getAllAttributes() throws MilkroadServiceException {
        final List<ProductAttributeEntity> attributeEntities;
        try {
            attributeEntities = attributeDAO.getAll();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading product attributes");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final List<AttributeDTO> attributeDTOs = new ArrayList<>();
        for (final ProductAttributeEntity attributeEntity : attributeEntities) {
            attributeDTOs.add(EntityDTOConverter.attributeDTO(attributeEntity));
        }
        return attributeDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<CategoryDTO> getAllCategories() throws MilkroadServiceException {
        final List<ProductCategoryEntity> categoryEntities;
        try {
            categoryEntities = categoryDAO.getAll();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading product categories");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (final ProductCategoryEntity categoryEntity : categoryEntities) {
            categoryDTOs.add(EntityDTOConverter.categoryDTO(categoryEntity));
        }
        return categoryDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ProductDTO> getAllProducts() throws MilkroadServiceException {
        final List<ProductEntity> productEntities;
        try {
            productEntities = productDAO.getAll();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final List<ProductDTO> productDTOs = new ArrayList<>();
        for (final ProductEntity productEntity : productEntities) {
            productDTOs.add(EntityDTOConverter.productDTO(productEntity));
        }
        return productDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ProductDTO> getAllProductsByCategory(final String category) throws MilkroadServiceException {
        final List<ProductDTO> productDTOs = new ArrayList<>();
        final List<ProductEntity> productEntities;
        try {
            productEntities = productDAO.getAllByCategory(category);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        for (final ProductEntity productEntity : productEntities) {
            productDTOs.add(EntityDTOConverter.productDTO(productEntity));
        }
        return productDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ProductDTO getProductByArticle(final Long article) throws MilkroadServiceException {
        final ProductEntity productEntity;
        try {
            productEntity = productDAO.getByID(article);
            if (productEntity == null) {
                LOGGER.warn("Product with article = " + article + " doesn't exist");
                throw new MilkroadServiceException(MilkroadServiceException.Type.PRODUCT_NOT_EXISTS);
            }
        } catch (final NumberFormatException | MilkroadDAOException e) {
            LOGGER.error("Error while loading product with article = " + article);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.productDTO(productEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CategoryDTO updateCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException {
        final ProductCategoryEntity categoryEntity;
        try {
            final ProductCategoryEntity duplicate = categoryDAO.getByName(categoryDTO.getName());
            if (duplicate != null && !duplicate.getId().equals(categoryDTO.getId())) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS);
            } else {
                categoryEntity = categoryDAO.getByID(categoryDTO.getId());
                if (categoryEntity == null) {
                    throw new MilkroadServiceException(MilkroadServiceException.Type.CATEGORY_NOT_EXISTS);
                }
                categoryEntity.setCategoryName(categoryDTO.getName());
                categoryEntity.setDescription(categoryDTO.getDescription());
                categoryDAO.merge(categoryEntity);
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating product category with name = " + categoryDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.categoryDTO(categoryEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CategoryDTO createCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException {
        final ProductCategoryEntity categoryEntity;
        try {
            if (categoryDAO.getByName(categoryDTO.getName()) != null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS);
            }
            categoryEntity = DTOEntityConverter.productCategoryEntity(categoryDTO);
            categoryDAO.persist(categoryEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating product category with name = " + categoryDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.categoryDTO(categoryEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AttributeDTO updateAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException {
        final ProductAttributeEntity attributeEntity;
        try {
            final ProductAttributeEntity duplicate = attributeDAO.getByName(attributeDTO.getName());
            if (duplicate != null && !duplicate.getId().equals(attributeDTO.getId())) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS);
            } else {
                attributeEntity = attributeDAO.getByID(attributeDTO.getId());
                if (attributeEntity == null) {
                    throw new MilkroadServiceException(MilkroadServiceException.Type.ATTRIBUTE_NOT_EXISTS);
                }
                attributeEntity.setAttributeName(attributeDTO.getName());
                attributeEntity.setDescription(attributeDTO.getDescription());
                attributeDAO.merge(attributeEntity);
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating product attribute with name = " + attributeDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.attributeDTO(attributeEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AttributeDTO createAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException {
        final ProductAttributeEntity attributeEntity;
        try {
            if (attributeDAO.getByName(attributeDTO.getName()) != null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS);
            }
            attributeEntity = DTOEntityConverter.productAttributeEntity(attributeDTO);
            attributeDAO.persist(attributeEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating product attribute with name = " + attributeDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.attributeDTO(attributeEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ProductDTO updateProduct(final ProductDTO productDTO) throws MilkroadServiceException {
        final ProductEntity productEntity;
        try {
            productEntity = productDAO.getByID(productDTO.getArticle());
            if (productEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.PRODUCT_NOT_EXISTS);
            }
            final ProductCategoryEntity categoryEntity = categoryDAO.getByID(productDTO.getCategory().getId());
            if (categoryEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.CATEGORY_NOT_EXISTS);
            }
            productEntity.setCategory(categoryEntity);
            productEntity.setProductName(productDTO.getName());
            productEntity.setProductPrice(productDTO.getPrice());
            productEntity.setRemainCount(productDTO.getCount());
            productEntity.setDescription(productDTO.getDescription());
            productDAO.merge(productEntity);
            for (final ParameterDTO parameterDTO : productDTO.getParameters()) {
                final Long attrID = parameterDTO.getAttribute().getId();
                final String attrValue = parameterDTO.getValue();
                final ProductAttributeEntity attributeEntity = attributeDAO.getByID(attrID);
                if (attributeEntity == null) {
                    throw new MilkroadServiceException(MilkroadServiceException.Type.ATTRIBUTE_NOT_EXISTS);
                }
                ProductParameterEntity parameterEntity = parameterDAO.getByProductIDAndAttrID(productDTO.getArticle(),
                        attributeEntity.getId());
                if (parameterEntity != null) {
                    parameterEntity.setAttributeValue(attrValue);
                    parameterDAO.merge(parameterEntity);
                } else {
                    parameterEntity = new ProductParameterEntity();
                    parameterEntity.setProduct(productEntity);
                    parameterEntity.setAttribute(attributeEntity);
                    parameterEntity.setAttributeValue(attrValue);
                    parameterDAO.persist(parameterEntity);
                }
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating product with article = " + productDTO.getArticle());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.productDTO(productEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ProductDTO createProduct(final ProductDTO productDTO) throws MilkroadServiceException {
        final ProductEntity productEntity;
        try {
            final UserEntity userEntity = userDAO.getByID(productDTO.getSeller().getId());
            if (userEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
            }
            final ProductCategoryEntity categoryEntity = categoryDAO.getByID(productDTO.getCategory().getId());
            if (categoryEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.CATEGORY_NOT_EXISTS);
            }
            productEntity = new ProductEntity();
            productEntity.setId(productDTO.getArticle());
            productEntity.setSeller(userEntity);
            productEntity.setCategory(categoryEntity);
            productEntity.setProductName(productDTO.getName());
            productEntity.setProductPrice(productDTO.getPrice());
            productEntity.setRemainCount(productDTO.getCount());
            productEntity.setDescription(productDTO.getDescription());
            productDAO.persist(productEntity);
            for (final ParameterDTO parameterDTO : productDTO.getParameters()) {
                final Long attrID = parameterDTO.getAttribute().getId();
                final String attrValue = parameterDTO.getValue();
                final ProductAttributeEntity attributeEntity = attributeDAO.getByID(attrID);
                if (attributeEntity == null) {
                    throw new MilkroadServiceException(MilkroadServiceException.Type.ATTRIBUTE_NOT_EXISTS);
                }
                final ProductParameterEntity parameterEntity = new ProductParameterEntity();
                parameterEntity.setProduct(productEntity);
                parameterEntity.setAttribute(attributeEntity);
                parameterEntity.setAttributeValue(attrValue);
                parameterDAO.persist(parameterEntity);
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating product with name = " + productDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.productDTO(productEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ProductDTO> searchProductByName(final String pattern) throws MilkroadServiceException {
        final List<ProductEntity> productEntities;
        try {
            productEntities = productDAO.searchByName(pattern);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while searching products by pattern = " + pattern);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final List<ProductDTO> productDTOs = new ArrayList<>();
        for (final ProductEntity productEntity : productEntities) {
            productDTOs.add(EntityDTOConverter.productDTO(productEntity));
        }
        return productDTOs;
    }
}
