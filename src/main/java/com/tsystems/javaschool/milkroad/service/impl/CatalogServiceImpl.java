package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.CategoryDAO;
import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.model.ProductCategoryEntity;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
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

    private final CategoryDAO<ProductCategoryEntity, Long> categoryDAO;
    private final ProductDAO<ProductEntity, Long> productDAO;

    public CatalogServiceImpl(final EntityManager entityManager,
                              final CategoryDAO<ProductCategoryEntity, Long> categoryDAO,
                              final ProductDAO<ProductEntity, Long> productDAO) {
        super(entityManager);
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() throws MilkroadServiceException {
        final List<CategoryDTO> categoryDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<ProductCategoryEntity> categoryEntities = categoryDAO.getAll();
            for (final ProductCategoryEntity categoryEntity : categoryEntities) {
                categoryDTOs.add(new CategoryDTO(categoryEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading product categories");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return categoryDTOs;
    }

    @Override
    public List<ProductDTO> getAllProducts() throws MilkroadServiceException {
        final List<ProductDTO> productDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<ProductEntity> productEntities = productDAO.getAll();
            for (final ProductEntity productEntity : productEntities) {
                productDTOs.add(new ProductDTO(productEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return productDTOs;
    }

    @Override
    public List<ProductDTO> getAllProductsByCategory(final String category) throws MilkroadServiceException {
        final List<ProductDTO> productDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<ProductEntity> productEntities = productDAO.getAllByCategory(category);
            for (final ProductEntity productEntity : productEntities) {
                productDTOs.add(new ProductDTO(productEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading products");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return productDTOs;
    }

    @Override
    public ProductDTO getProductByArticle(final String article) throws MilkroadServiceException {
        try {
            final ProductEntity productEntity;
            entityManager.getTransaction().begin();
            productEntity = productDAO.getByID(Long.valueOf(article));
            entityManager.getTransaction().commit();
            if (productEntity != null) {
                return new ProductDTO(productEntity);
            } else {
                LOGGER.warn("Product with article = " + article + " doesn't exist");
                throw new MilkroadServiceException(MilkroadServiceException.Type.PRODUCT_NOT_EXISTS);
            }
        } catch (final NumberFormatException | MilkroadDAOException e) {
            LOGGER.error("Error while loading product with article = " + article);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }
}
