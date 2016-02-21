package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 20.02.2016.
 */
public interface AddressService {
    List<AddressDTO> getAllAddresses() throws MilkroadServiceException;

    List<AddressDTO> getAddressesByUser(final UserDTO userDTO) throws  MilkroadServiceException;
}
