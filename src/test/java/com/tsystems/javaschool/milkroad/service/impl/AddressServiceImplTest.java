package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.AddressDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.AddressEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Created by Sergey on 20.02.2016.
 */
public class AddressServiceImplTest extends AbstractServiceTest {
    @InjectMocks
    private AddressServiceImpl mockedAddressService;
    @Mock
    private AddressDAO<AddressEntity, Long> mockAddressDAO;
    @Mock
    private UserDAO<UserEntity, Long> mockUserDAO;

    private UserEntity userEntity;
    private AddressEntity addressEntity;
    private AddressDTO addressDTO;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        userEntity = createUserEntity(0);
        addressDTO = new AddressDTO(0L, "Russia", "Moscow", 127001, "Arbat", "7A", "8B");
        addressEntity = new AddressEntity(addressDTO);
    }

    @Test
    public void testAddAddressToUserPositive() throws Exception {
        Mockito.when(mockUserDAO.getByID(userEntity.getId())).thenReturn(userEntity);
        Mockito.doNothing().when(mockUserDAO).merge(Mockito.any(UserEntity.class));
        Assert.assertEquals(new UserDTO(userEntity),
                mockedAddressService.addAddressToUser(new UserDTO(userEntity), new AddressDTO(addressEntity)));
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(userEntity.getId());
        Mockito.verify(mockUserDAO, Mockito.times(1)).merge(Mockito.any(UserEntity.class));
    }

    @Test
    public void testAddAddressToUserNegative() throws Exception {
        Mockito.when(mockUserDAO.getByID(userEntity.getId())).thenReturn(null);
        try {
            mockedAddressService.addAddressToUser(new UserDTO(userEntity), new AddressDTO(addressEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.USER_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockUserDAO, Mockito.times(1)).getByID(userEntity.getId());
        Mockito.verify(mockUserDAO, Mockito.times(0)).merge(Mockito.any(UserEntity.class));
    }

    @Test
    public void testUpdateAddressPositive() throws Exception {
        Mockito.when(mockAddressDAO.getByID(addressDTO.getId())).thenReturn(addressEntity);
        Mockito.doNothing().when(mockAddressDAO).merge(Mockito.any(AddressEntity.class));
        Assert.assertEquals(addressDTO, mockedAddressService.updateAddress(addressDTO));
        Mockito.verify(mockAddressDAO, Mockito.times(1)).getByID(addressDTO.getId());
        Mockito.verify(mockAddressDAO, Mockito.times(1)).merge(Mockito.any(AddressEntity.class));
    }

    @Test
    public void testUpdateAddressNegative() throws Exception {
        Mockito.when(mockAddressDAO.getByID(addressDTO.getId())).thenReturn(null);
        try {
            mockedAddressService.updateAddress(new AddressDTO(addressEntity));
            Assert.fail("Failed to assert: No exception thrown");
        } catch (final MilkroadServiceException e) {
            Assert.assertEquals(MilkroadServiceException.Type.ADDRESS_NOT_EXISTS, e.getType());
        }
        Mockito.verify(mockAddressDAO, Mockito.times(1)).getByID(addressDTO.getId());
        Mockito.verify(mockAddressDAO, Mockito.times(0)).merge(Mockito.any(AddressEntity.class));
    }
}
