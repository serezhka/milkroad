package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

/**
 * Created by Sergey on 20.02.2016.
 */
public interface AddressService {
    UserDTO addAddressToUser(final UserDTO userDTO, final AddressDTO addressDTO) throws MilkroadServiceException;

    AddressDTO updateAddress(final AddressDTO addressDTO) throws MilkroadServiceException;
}
