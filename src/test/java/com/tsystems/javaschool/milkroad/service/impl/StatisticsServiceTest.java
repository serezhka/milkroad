package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.OrderEntity;
import com.tsystems.javaschool.milkroad.model.ProductCategoryEntity;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 02.04.2016.
 */
public class StatisticsServiceTest extends AbstractServiceTest {
    @InjectMocks
    private StatisticsServiceImpl mockedStatisticsService;
    @Mock
    private OrderDAO<OrderEntity, Long> mockOrderDAO;
    @Mock
    private UserDAO<UserEntity, Long> mockUserDAO;
    @Mock
    private ProductDAO<ProductEntity, Long> mockProductDAO;

    private List<UserDTO> userDTOs;
    private List<ProductDTO> productDTOs;
    private List<UserEntity> userEntities;
    private List<ProductEntity> productEntities;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        final UserEntity userEntity = createUserEntity(0);
        userEntities = Collections.singletonList(userEntity);
        userDTOs = Collections.singletonList(EntityDTOConverter.userDTO(userEntity));
        final ProductEntity productEntity =
                new ProductEntity(0L, createUserEntity(0), new ProductCategoryEntity(), "product", null, 0, "");
        productDTOs = Collections.singletonList(EntityDTOConverter.productDTO(productEntity));
        productEntities = Collections.singletonList(productEntity);
    }

    @Test
    public void testGetTopProducts() throws Exception {
        Assert.assertTrue(mockedStatisticsService.getTopProducts(1).isEmpty());
        Mockito.when(mockProductDAO.getTopProducts(1))
                .thenReturn(Collections.singletonMap(productEntities.get(0), Mockito.anyInt()));
        Assert.assertArrayEquals(productDTOs.toArray(), mockedStatisticsService.getTopProducts(1).keySet().toArray());
        Mockito.verify(mockProductDAO, Mockito.times(2)).getTopProducts(Mockito.anyInt());
    }

    @Test
    public void testGetTopCustomers() throws Exception {
        Assert.assertTrue(mockedStatisticsService.getTopCustomers(1).isEmpty());
        Mockito.when(mockUserDAO.getTopCustomers(1))
                .thenReturn(Collections.singletonMap(userEntities.get(0), Mockito.any(BigDecimal.class)));
        Assert.assertArrayEquals(userDTOs.toArray(), mockedStatisticsService.getTopCustomers(1).keySet().toArray());
        Mockito.verify(mockUserDAO, Mockito.times(2)).getTopCustomers(Mockito.anyInt());
    }

    @Test
    public void getTotalCash() throws Exception {
        Mockito.when(mockOrderDAO.getTotalCash()).thenReturn(BigDecimal.TEN);
        Assert.assertEquals(BigDecimal.TEN, mockedStatisticsService.getTotalCash());
        Mockito.verify(mockOrderDAO, Mockito.times(1)).getTotalCash();
    }

    @Test
    public void getTotalCashByPeriod() throws Exception {
        Mockito.when(mockOrderDAO.getTotalCashByPeriod(Mockito.any(Date.class), Mockito.any(Date.class)))
                .thenReturn(BigDecimal.TEN);
        Assert.assertEquals(BigDecimal.TEN,
                mockedStatisticsService.getTotalCashByPeriod(Mockito.any(Date.class), Mockito.any(Date.class)));
        Mockito.verify(mockOrderDAO,
                Mockito.times(1)).getTotalCashByPeriod(Mockito.any(Date.class), Mockito.any(Date.class));
    }
}
