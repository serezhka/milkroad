package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.*;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.*;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public class CatalogServiceImpl extends AbstractService implements CatalogService {
    private static final Logger LOGGER = Logger.getLogger(CatalogServiceImpl.class);

    private final AttributeDAO<ProductAttributeEntity, Long> attributeDAO;
    private final CategoryDAO<ProductCategoryEntity, Long> categoryDAO;
    private final ParameterDAO<ProductParameterEntity, Long> parameterDAO;
    private final ProductDAO<ProductEntity, Long> productDAO;
    private final UserDAO<UserEntity, Long> userDAO;

    public CatalogServiceImpl(final EntityManager entityManager,
                              final AttributeDAO<ProductAttributeEntity, Long> attributeDAO,
                              final CategoryDAO<ProductCategoryEntity, Long> categoryDAO,
                              final ParameterDAO<ProductParameterEntity, Long> parameterDAO,
                              final ProductDAO<ProductEntity, Long> productDAO,
                              final UserDAO<UserEntity, Long> userDAO) {
        super(entityManager);
        this.attributeDAO = attributeDAO;
        this.categoryDAO = categoryDAO;
        this.parameterDAO = parameterDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<AttributeDTO> getAllAttributes() throws MilkroadServiceException {
        final List<AttributeDTO> attributeDTOs = new ArrayList<>();
        try {
            final List<ProductAttributeEntity> attributeEntities = attributeDAO.getAll();
            for (final ProductAttributeEntity attributeEntity : attributeEntities) {
                attributeDTOs.add(new AttributeDTO(attributeEntity));
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading product attributes");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return attributeDTOs;
    }

    @Override
    public List<CategoryDTO> getAllCategories() throws MilkroadServiceException {
        final List<CategoryDTO> categoryDTOs = new ArrayList<>();
        try {
            final List<ProductCategoryEntity> categoryEntities = categoryDAO.getAll();
            for (final ProductCategoryEntity categoryEntity : categoryEntities) {
                categoryDTOs.add(new CategoryDTO(categoryEntity));
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading product categories");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return categoryDTOs;
    }

    @Override
    public List<ProductDTO> getAllProducts() throws MilkroadServiceException {
        final List<ProductDTO> productDTOs = new ArrayList<>();
        try {
            final List<ProductEntity> productEntities = productDAO.getAll();
            for (final ProductEntity productEntity : productEntities) {
                entityManager.refresh(productEntity);
                productDTOs.add(new ProductDTO(productEntity));
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return productDTOs;
    }

    @Override
    public List<ProductDTO> getAllProductsByCategory(final String category) throws MilkroadServiceException {
        final List<ProductDTO> productDTOs = new ArrayList<>();
        try {
            final List<ProductEntity> productEntities = productDAO.getAllByCategory(category);
            for (final ProductEntity productEntity : productEntities) {
                productDTOs.add(new ProductDTO(productEntity));
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return productDTOs;
    }

    @Override
    public ProductDTO getProductByArticle(final Long article) throws MilkroadServiceException {
        try {
            final ProductEntity productEntity;
            productEntity = productDAO.getByID(article);
            if (productEntity != null) {
                return new ProductDTO(productEntity);
            } else {
                LOGGER.warn("Product with article = " + article + " doesn't exist");
                throw new MilkroadServiceException(MilkroadServiceException.Type.PRODUCT_NOT_EXISTS);
            }
        } catch (final NumberFormatException | MilkroadDAOException e) {
            LOGGER.error("Error while loading product with article = " + article);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }

    @Override
    public CategoryDTO updateCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException {
        try {
            entityManager.getTransaction().begin();
            // TODO throw exception if entity not exists
            final ProductCategoryEntity duplicate = categoryDAO.getByName(categoryDTO.getName());
            if (duplicate != null && !duplicate.getId().equals(categoryDTO.getId())) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS);
            } else {
                final ProductCategoryEntity categoryEntity = categoryDAO.getByID(categoryDTO.getId());
                categoryEntity.setCategoryName(categoryDTO.getName());
                categoryEntity.setDescription(categoryDTO.getDescription());
                categoryDAO.merge(categoryEntity);
                entityManager.getTransaction().commit();
                return new CategoryDTO(categoryEntity);
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating product category with id = " + categoryDTO.getId());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public CategoryDTO createCategory(final CategoryDTO categoryDTO) throws MilkroadServiceException {
        try {
            entityManager.getTransaction().begin();
            if (categoryDAO.getByName(categoryDTO.getName()) != null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS);
            }
            final ProductCategoryEntity categoryEntity = new ProductCategoryEntity(categoryDTO);
            categoryDAO.persist(categoryEntity);
            entityManager.getTransaction().commit();
            return new CategoryDTO(categoryEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating product category with name = " + categoryDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public AttributeDTO updateAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException {
        try {
            entityManager.getTransaction().begin();
            // TODO throw exception if entity not exists
            final ProductAttributeEntity duplicate = attributeDAO.getByName(attributeDTO.getName());
            if (duplicate != null && !duplicate.getId().equals(attributeDTO.getId())) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS);
            } else {
                final ProductAttributeEntity attributeEntity = attributeDAO.getByID(attributeDTO.getId());
                attributeEntity.setAttributeName(attributeDTO.getName());
                attributeEntity.setDescription(attributeDTO.getDescription());
                attributeDAO.merge(attributeEntity);
                entityManager.getTransaction().commit();
                return new AttributeDTO(attributeEntity);
            }
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating product attribute with id = " + attributeDTO.getId());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public AttributeDTO createAttribute(final AttributeDTO attributeDTO) throws MilkroadServiceException {
        try {
            entityManager.getTransaction().begin();
            if (attributeDAO.getByName(attributeDTO.getName()) != null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS);
            }
            final ProductAttributeEntity attributeEntity = new ProductAttributeEntity(attributeDTO);
            attributeDAO.persist(attributeEntity);
            entityManager.getTransaction().commit();
            return new AttributeDTO(attributeEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating product attribute with name = " + attributeDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public ProductDTO updateProduct(final ProductDTO productDTO, final Long categoryID, final String[] parameters) throws MilkroadServiceException {
        try {
            final ProductCategoryEntity categoryEntity = categoryDAO.getByID(categoryID);
            final ProductEntity productEntity = productDAO.getByID(productDTO.getArticle());
            productEntity.setCategory(categoryEntity);
            productEntity.setProductName(productDTO.getName());
            productEntity.setProductPrice(productDTO.getPrice());
            productEntity.setRemainCount(productDTO.getCount());
            productEntity.setDescription(productDTO.getDescription());
            entityManager.getTransaction().begin();
            if (parameters != null) {
                for (final String parameter : parameters) {
                    final Long attrID = Long.valueOf(parameter.split("\\|")[0]);
                    final String attrValue = parameter.split("\\|")[1];
                    // TODO Check if attribute Entity not null
                    final ProductAttributeEntity attributeEntity = attributeDAO.getByID(attrID);
                    ProductParameterEntity parameterEntity = parameterDAO.getByProductIDAndAttrID(productDTO.getArticle(), attrID);
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
            }
            productDAO.merge(productEntity);
            entityManager.getTransaction().commit();
            return new ProductDTO(productEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating product with article = " + productDTO.getArticle());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public ProductDTO createProduct(final UserDTO userDTO, final ProductDTO productDTO, final Long categoryID, final String[] parameters) throws MilkroadServiceException {
        try {
            final ProductCategoryEntity categoryEntity = categoryDAO.getByID(categoryID);
            final UserEntity userEntity = userDAO.getByID(userDTO.getId());
            final ProductEntity productEntity = new ProductEntity();
            productEntity.setSeller(userEntity);
            productEntity.setCategory(categoryEntity);
            productEntity.setProductName(productDTO.getName());
            productEntity.setProductPrice(productDTO.getPrice());
            productEntity.setRemainCount(productDTO.getCount());
            productEntity.setDescription(productDTO.getDescription());
            entityManager.getTransaction().begin();
            productDAO.persist(productEntity);
            if (parameters != null) {
                for (final String parameter : parameters) {
                    final Long attrID = Long.valueOf(parameter.split("\\|")[0]);
                    final String attrValue = parameter.split("\\|")[1];
                    // TODO Check if attribute Entity not null
                    final ProductAttributeEntity attributeEntity = attributeDAO.getByID(attrID);
                    final ProductParameterEntity parameterEntity = new ProductParameterEntity();
                    parameterEntity.setProduct(productEntity);
                    parameterEntity.setAttribute(attributeEntity);
                    parameterEntity.setAttributeValue(attrValue);
                    parameterDAO.persist(parameterEntity);
                }
            }
            entityManager.getTransaction().commit();
            return new ProductDTO(productEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while creating product with name = " + productDTO.getName());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }
}
