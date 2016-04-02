package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.AddressDAO;
import com.tsystems.javaschool.milkroad.dao.OrderDAO;
import com.tsystems.javaschool.milkroad.dao.ProductDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.model.*;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 09.03.2016.
 */
public class OrderServiceTest extends AbstractServiceTest {
    @InjectMocks
    private OrderServiceImpl mockedOrderService;
    @Mock
    private OrderDAO<OrderEntity, Long> mockOrderDAO;
    @Mock
    private UserDAO<UserEntity, Long> mockUserDAO;
    @Mock
    private AddressDAO<AddressEntity, Long> mockAddressDAO;
    @Mock
    private ProductDAO<ProductEntity, Long> mockProductDAO;

    private List<OrderDTO> orderDTOs;
    private List<UserEntity> userEntities;
    private List<AddressEntity> addressEntities;
    private List<OrderEntity> orderEntities;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        final UserEntity userEntity = createUserEntity(0);
        final AddressEntity addressEntity = new AddressEntity(0L, "Russia", "Moscow", 127001, "Arbat", "7A", "8B");
        addressEntities = Collections.singletonList(addressEntity);
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(0L);
        orderEntity.setCustomer(userEntity);
        orderEntity.setAddress(addressEntity);
        orderEntity.setPriceTotal(BigDecimal.TEN);
        orderEntity.setPaymentMethod(randomEnum(PaymentMethodEnum.class));
        orderEntity.setPaymentStatus(randomEnum(PaymentStatusEnum.class));
        orderEntity.setShippingMethod(randomEnum(ShippingMethodEnum.class));
        orderEntity.setShippingStatus(randomEnum(ShippingStatusEnum.class));
        orderEntities = Collections.singletonList(orderEntity);
        orderDTOs = Collections.singletonList(EntityDTOConverter.orderDTO(orderEntity));
        userEntity.setOrders(orderEntities);
        userEntities = Collections.singletonList(userEntity);
    }

    @Test
    public void testGetAllOrders() throws Exception {
        Assert.assertTrue(mockedOrderService.getAllOrders().isEmpty());
        Mockito.when(mockOrderDAO.getAll()).thenReturn(orderEntities);
        Assert.assertArrayEquals(orderDTOs.toArray(), mockedOrderService.getAllOrders().toArray());
        Mockito.verify(mockOrderDAO, Mockito.times(2)).getAll();
    }

    @Test
    public void testCreateOrderPositive() throws Exception {
        final OrderEntity orderEntity = orderEntities.get(0);
        final OrderDTO orderDTO = EntityDTOConverter.orderDTO(orderEntity);
        Mockito.when(mockUserDAO.getByID(orderDTO.getCustomer().getId())).thenReturn(userEntities.get(0));
        Mockito.when(mockAddressDAO.getByID(orderDTO.getAddress().getId())).thenReturn(addressEntities.get(0));
        Assert.assertEquals(orderDTO, mockedOrderService.createOrder(orderDTO));
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(orderDTO.getCustomer().getId());
        Mockito.verify(mockAddressDAO, Mockito.times(1)).getByID(orderDTO.getAddress().getId());
        Mockito.verify(mockOrderDAO, Mockito.times(1)).persist(orderEntity);
    }

    @Test
    public void testCreateOrderNegative1() throws Exception {
        final OrderEntity orderEntity = orderEntities.get(0);
        final OrderDTO orderDTO = EntityDTOConverter.orderDTO(orderEntity);
        Mockito.when(mockUserDAO.getByID(orderDTO.getCustomer().getId())).thenReturn(null);
        Mockito.when(mockAddressDAO.getByID(orderDTO.getAddress().getId())).thenReturn(orderEntity.getAddress());
        try {
            mockedOrderService.createOrder(orderDTO);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(orderEntity.getAddress().getId());
        Mockito.verify(mockAddressDAO, Mockito.times(0)).getByID(Mockito.anyLong());
    }

    @Test
    public void testCreateOrderNegative2() throws Exception {
        final OrderEntity orderEntity = orderEntities.get(0);
        final OrderDTO orderDTO = EntityDTOConverter.orderDTO(orderEntity);
        Mockito.when(mockUserDAO.getByID(orderDTO.getCustomer().getId())).thenReturn(orderEntity.getCustomer());
        Mockito.when(mockAddressDAO.getByID(orderDTO.getAddress().getId())).thenReturn(null);
        try {
            mockedOrderService.createOrder(orderDTO);
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.ADDRESS_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(orderEntity.getAddress().getId());
        Mockito.verify(mockAddressDAO, Mockito.times(1)).getByID(orderEntity.getAddress().getId());
    }


    @Test
    public void testGetOrdersByUserPositive() throws Exception {
        Mockito.when(mockUserDAO.getByID(orderDTOs.get(0).getCustomer().getId()))
                .thenReturn(orderEntities.get(0).getCustomer());
        Assert.assertArrayEquals(orderDTOs.toArray(),
                mockedOrderService.getOrdersByUser(orderDTOs.get(0).getCustomer()).toArray());
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(orderDTOs.get(0).getCustomer().getId());
    }

    @Test
    public void testGetOrdersByUserNegative() throws Exception {
        try {
            mockedOrderService.getOrdersByUser(orderDTOs.get(0).getCustomer());
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
    }
}
