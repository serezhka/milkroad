package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.*;
import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.*;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 07.03.2016.
 */
public class CatalogServiceTest extends AbstractServiceTest {
    @InjectMocks
    private CatalogServiceImpl mockedCatalogService;
    @Mock
    private AttributeDAO<ProductAttributeEntity, Long> mockAttributeDAO;
    @Mock
    private CategoryDAO<ProductCategoryEntity, Long> mockCategoryDAO;
    @Mock
    private ParameterDAO<ProductParameterEntity, Long> mockParameterDAO;
    @Mock
    private ProductDAO<ProductEntity, Long> mockProductDAO;
    @Mock
    private UserDAO<UserEntity, Long> mockUserDAO;

    private List<AttributeDTO> attributeDTOs;
    private List<CategoryDTO> categoryDTOs;
    private List<ProductDTO> productDTOs;
    private List<ProductAttributeEntity> attributeEntities;
    private List<ProductCategoryEntity> categoryEntities;
    private List<ProductEntity> productEntities;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        attributeDTOs = Collections.singletonList(new AttributeDTO(0L, "attribute", "desc"));
        attributeEntities = new ArrayList<>();
        for (final AttributeDTO attributeDTO : attributeDTOs) {
            attributeEntities.add(new ProductAttributeEntity(attributeDTO));
        }
        categoryDTOs = Collections.singletonList(new CategoryDTO(0L, "category", "desc"));
        categoryEntities = new ArrayList<>();
        for (final CategoryDTO categoryDTO : categoryDTOs) {
            categoryEntities.add(new ProductCategoryEntity(categoryDTO));
        }
        final ProductEntity productEntity =
                new ProductEntity(0L, createUserEntity(0), categoryEntities.get(0), "product", null, 0, "");
        productDTOs = Collections.singletonList(new ProductDTO(productEntity));
        productEntities = Collections.singletonList(productEntity);
    }

    @Test
    public void testGetAllAttributes() throws Exception {
        Assert.assertTrue(mockedCatalogService.getAllAttributes().isEmpty());
        Mockito.when(mockAttributeDAO.getAll()).thenReturn(attributeEntities);
        Assert.assertArrayEquals(attributeDTOs.toArray(), mockedCatalogService.getAllAttributes().toArray());
        Mockito.verify(mockAttributeDAO, Mockito.times(2)).getAll();
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Assert.assertTrue(mockedCatalogService.getAllCategories().isEmpty());
        Mockito.when(mockCategoryDAO.getAll()).thenReturn(categoryEntities);
        Assert.assertArrayEquals(categoryDTOs.toArray(), mockedCatalogService.getAllCategories().toArray());
        Mockito.verify(mockCategoryDAO, Mockito.times(2)).getAll();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Assert.assertTrue(mockedCatalogService.getAllProducts().isEmpty());
        Mockito.when(mockProductDAO.getAll()).thenReturn(productEntities);
        Assert.assertArrayEquals(productDTOs.toArray(), mockedCatalogService.getAllProducts().toArray());
        Mockito.verify(mockProductDAO, Mockito.times(2)).getAll();
    }

    @Test
    public void testGetAllProductsByCategory() throws Exception {
        Mockito.when(mockProductDAO.getAllByCategory(Mockito.anyString())).thenReturn(Collections.emptyList());
        Assert.assertTrue(mockedCatalogService.getAllProductsByCategory(Mockito.anyString()).isEmpty());
        Mockito.when(mockProductDAO.getAllByCategory(Mockito.anyString())).thenReturn(productEntities);
        Assert.assertArrayEquals(productDTOs.toArray(),
                mockedCatalogService.getAllProductsByCategory(Mockito.anyString()).toArray());
        Mockito.verify(mockProductDAO, Mockito.times(2)).getAllByCategory(Mockito.anyString());
    }

    @Test
    public void testGetProductByArticlePositive() throws Exception {
        final ProductEntity productEntity = productEntities.get(0);
        Mockito.when(mockProductDAO.getByID(productEntity.getId())).thenReturn(productEntity);
        Assert.assertEquals(new ProductDTO(productEntity), mockedCatalogService.getProductByArticle(productEntity.getId()));
        Mockito.verify(mockProductDAO, Mockito.times(1)).getByID(productEntity.getId());
    }

    @Test
    public void testGetProductByArticleNegative() throws Exception {
        Mockito.when(mockProductDAO.getByID(Mockito.anyLong())).thenReturn(null);
        try {
            mockedCatalogService.getProductByArticle(Mockito.anyLong());
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.PRODUCT_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockProductDAO, Mockito.times(1)).getByID(Mockito.anyLong());
    }

    @Test
    public void testUpdateCategoryPositive() throws Exception {
        final ProductCategoryEntity categoryEntity = categoryEntities.get(0);
        Mockito.when(mockCategoryDAO.getByID(categoryEntity.getId())).thenReturn(categoryEntity);
        Mockito.doNothing().when(mockCategoryDAO).merge(Mockito.any(ProductCategoryEntity.class));
        Assert.assertEquals(new CategoryDTO(categoryEntity),
                mockedCatalogService.updateCategory(new CategoryDTO(categoryEntity)));
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByID(categoryEntity.getId());
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByName(categoryEntity.getCategoryName());
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).merge(categoryEntity);
    }

    @Test
    public void testUpdateCategoryNegative() throws Exception {
        final ProductCategoryEntity categoryEntity = categoryEntities.get(0);
        final ProductCategoryEntity duplicateEntity = new ProductCategoryEntity();
        duplicateEntity.setId(categoryEntity.getId() + 1);
        Mockito.when(mockCategoryDAO.getByName(categoryEntity.getCategoryName())).thenReturn(duplicateEntity);
        try {
            mockedCatalogService.updateCategory(new CategoryDTO(categoryEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS, e.getType());
        }
        Mockito.when(mockCategoryDAO.getByName(categoryEntity.getCategoryName())).thenReturn(categoryEntity);
        try {
            mockedCatalogService.updateCategory(new CategoryDTO(categoryEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.CATEGORY_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockCategoryDAO, Mockito.times(2)).getByName(categoryEntity.getCategoryName());
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByID(categoryEntity.getId());
        Mockito.verify(mockCategoryDAO, Mockito.times(0)).merge(Mockito.any(ProductCategoryEntity.class));
    }

    @Test
    public void testCreateCategoryPositive() throws Exception {
        final ProductCategoryEntity categoryEntity = categoryEntities.get(0);
        Assert.assertEquals(new CategoryDTO(categoryEntity),
                mockedCatalogService.createCategory(new CategoryDTO(categoryEntity)));
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByName(categoryEntity.getCategoryName());
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).persist(categoryEntity);
    }

    @Test
    public void testCreateCategoryNegative() throws Exception {
        final ProductCategoryEntity categoryEntity = categoryEntities.get(0);
        final ProductCategoryEntity duplicateEntity = new ProductCategoryEntity();
        duplicateEntity.setId(categoryEntity.getId() + 1);
        Mockito.when(mockCategoryDAO.getByName(categoryEntity.getCategoryName())).thenReturn(duplicateEntity);
        try {
            mockedCatalogService.updateCategory(new CategoryDTO(categoryEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS, e.getType());
        }
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByName(categoryEntity.getCategoryName());
        Mockito.verify(mockCategoryDAO, Mockito.times(0)).persist(Mockito.any(ProductCategoryEntity.class));
    }

    @Test
    public void testUpdateAttributeEntityPositive() throws Exception {
        final ProductAttributeEntity attributeEntity = attributeEntities.get(0);
        Mockito.when(mockAttributeDAO.getByID(attributeEntity.getId())).thenReturn(attributeEntity);
        Assert.assertEquals(new AttributeDTO(attributeEntity),
                mockedCatalogService.updateAttribute(new AttributeDTO(attributeEntity)));
        Mockito.verify(mockAttributeDAO, Mockito.times(1)).getByID(attributeEntity.getId());
        Mockito.verify(mockAttributeDAO, Mockito.times(1)).getByName(attributeEntity.getAttributeName());
        Mockito.verify(mockAttributeDAO, Mockito.times(1)).merge(attributeEntity);
    }

    @Test
    public void testUpdateAttributeNegative() throws Exception {
        final ProductAttributeEntity attributeEntity = attributeEntities.get(0);
        final ProductAttributeEntity duplicateEntity = new ProductAttributeEntity();
        duplicateEntity.setId(attributeEntity.getId() + 1);
        Mockito.when(mockAttributeDAO.getByName(attributeEntity.getAttributeName())).thenReturn(duplicateEntity);
        try {
            mockedCatalogService.updateAttribute(new AttributeDTO(attributeEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS, e.getType());
        }
        Mockito.when(mockAttributeDAO.getByName(attributeEntity.getAttributeName())).thenReturn(attributeEntity);
        try {
            mockedCatalogService.updateAttribute(new AttributeDTO(attributeEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.ATTRIBUTE_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockAttributeDAO, Mockito.times(2)).getByName(attributeEntity.getAttributeName());
        Mockito.verify(mockAttributeDAO, Mockito.times(1)).getByID(attributeEntity.getId());
        Mockito.verify(mockAttributeDAO, Mockito.times(0)).merge(Mockito.any(ProductAttributeEntity.class));
    }

    @Test
    public void testCreateAttributePositive() throws Exception {
        final ProductAttributeEntity attributeEntity = attributeEntities.get(0);
        Assert.assertEquals(new AttributeDTO(attributeEntity),
                mockedCatalogService.createAttribute(new AttributeDTO(attributeEntity)));
        Mockito.verify(mockAttributeDAO, Mockito.times(1)).getByName(attributeEntity.getAttributeName());
        Mockito.verify(mockAttributeDAO, Mockito.times(1)).persist(attributeEntity);
    }

    @Test
    public void testCreateAttributeNegative() throws Exception {
        final ProductAttributeEntity attributeEntity = attributeEntities.get(0);
        final ProductAttributeEntity duplicateEntity = new ProductAttributeEntity();
        duplicateEntity.setId(attributeEntity.getId() + 1);
        Mockito.when(mockAttributeDAO.getByName(attributeEntity.getAttributeName())).thenReturn(duplicateEntity);
        try {
            mockedCatalogService.updateAttribute(new AttributeDTO(attributeEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS, e.getType());
        }
        Mockito.verify(mockAttributeDAO, Mockito.times(1)).getByName(attributeEntity.getAttributeName());
        Mockito.verify(mockAttributeDAO, Mockito.times(0)).persist(Mockito.any(ProductAttributeEntity.class));
    }

    @Test
    public void testUpdateProductPositive() throws Exception {
        final ProductEntity productEntity = productEntities.get(0);
        final Long newCategoryID = productEntity.getCategory().getId();
        Mockito.when(mockCategoryDAO.getByID(newCategoryID)).thenReturn(productEntity.getCategory());
        Mockito.when(mockProductDAO.getByID(productEntity.getId())).thenReturn(productEntity);
        Assert.assertEquals(new ProductDTO(productEntity),
                mockedCatalogService.updateProduct(new ProductDTO(productEntity), newCategoryID, null));
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByID(newCategoryID);
        Mockito.verify(mockProductDAO, Mockito.times(1)).getByID(productEntity.getId());
        Mockito.verify(mockProductDAO, Mockito.times(1)).merge(productEntity);
    }

    @Test
    public void testUpdateProductNegative() throws Exception {
        final ProductEntity productEntity = productEntities.get(0);
        final Long newCategoryID = -1L;
        Mockito.when(mockProductDAO.getByID(productEntity.getId())).thenReturn(null);
        try {
            mockedCatalogService.updateProduct(new ProductDTO(productEntity), newCategoryID, null);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.PRODUCT_NOT_EXISTS, e.getType());
        }
        Mockito.when(mockProductDAO.getByID(productEntity.getId())).thenReturn(productEntity);
        Mockito.when(mockCategoryDAO.getByID(newCategoryID)).thenReturn(null);
        try {
            mockedCatalogService.updateProduct(new ProductDTO(productEntity), newCategoryID, null);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.CATEGORY_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockProductDAO, Mockito.times(2)).getByID(productEntity.getId());
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByID(newCategoryID);
        Mockito.verify(mockProductDAO, Mockito.times(0)).merge(Mockito.any(ProductEntity.class));
    }

    @Test
    public void testCreateProductPositive() throws Exception {
        final ProductEntity productEntity = productEntities.get(0);
        final Long newCategoryID = productEntity.getCategory().getId();
        Mockito.when(mockUserDAO.getByID(productEntity.getSeller().getId())).thenReturn(productEntity.getSeller());
        Mockito.when(mockCategoryDAO.getByID(newCategoryID)).thenReturn(productEntity.getCategory());
        Assert.assertEquals(new ProductDTO(productEntity), mockedCatalogService.createProduct(
                new UserDTO(productEntity.getSeller()),
                new ProductDTO(productEntity),
                newCategoryID, null));
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(productEntity.getSeller().getId());
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByID(newCategoryID);
        Mockito.verify(mockProductDAO, Mockito.times(1)).persist(productEntity);
    }

    @Test
    public void testCreateProductNegative() throws Exception {
        final ProductEntity productEntity = productEntities.get(0);
        final Long newCategoryID = -1L;
        try {
            mockedCatalogService.createProduct(
                    new UserDTO(productEntity.getSeller()),
                    new ProductDTO(productEntity),
                    newCategoryID, null);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
        Mockito.when(mockUserDAO.getByID(productEntity.getSeller().getId())).thenReturn(productEntity.getSeller());
        try {
            mockedCatalogService.createProduct(
                    new UserDTO(productEntity.getSeller()),
                    new ProductDTO(productEntity),
                    newCategoryID, null);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.CATEGORY_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(2)).getByID(productEntity.getSeller().getId());
        Mockito.verify(mockCategoryDAO, Mockito.times(1)).getByID(newCategoryID);
        Mockito.verify(mockProductDAO, Mockito.times(0)).persist(Mockito.any(ProductEntity.class));
    }

    @Test
    public void testSearchProductByName() throws Exception {
        Assert.assertTrue(mockedCatalogService.searchProductByName(Mockito.anyString()).isEmpty());
        Mockito.when(mockProductDAO.searchByName(Mockito.anyString())).thenReturn(productEntities);
        Assert.assertArrayEquals(productDTOs.toArray(),
                mockedCatalogService.searchProductByName(Mockito.anyString()).toArray());
    }
}
