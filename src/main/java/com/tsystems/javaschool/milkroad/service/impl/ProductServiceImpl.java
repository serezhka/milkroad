package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.model.MrProductEntity;
import com.tsystems.javaschool.milkroad.service.ProductService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public class ProductServiceImpl extends AbstractService implements ProductService {
    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

    private final ProductDAO<MrProductEntity, Long> productDAO;

    public ProductServiceImpl(final EntityManager entityManager, final ProductDAO<MrProductEntity, Long> productDAO) {
        super(entityManager);
        this.productDAO = productDAO;
    }

    @Override
    public List<ProductDTO> getAllProducts() throws MilkroadServiceException {
        final List<ProductDTO> productDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<MrProductEntity> productEntities = productDAO.getAll();
            for (final MrProductEntity productEntity : productEntities) {
                productDTOs.add(new ProductDTO(productEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading products");
            throw new MilkroadServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return productDTOs;
    }
}
