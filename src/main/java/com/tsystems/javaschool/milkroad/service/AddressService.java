package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

/**
 * Address service interface.
 * Provides address management features for users
 * <p>
 * Created by Sergey on 20.02.2016.
 */
public interface AddressService {

    /**
     * Adds new address to user
     *
     * @param userDTO    user
     * @param addressDTO new address
     * @return updated user info
     * @throws MilkroadServiceException if user doesn't exist or an error with database occurs
     */
    UserDTO addAddressToUser(final UserDTO userDTO, final AddressDTO addressDTO) throws MilkroadServiceException;

    /**
     * Updates address details
     *
     * @param addressDTO address details, id field is required
     * @return updated address info
     * @throws MilkroadServiceException if address doesn't exist or an error with database occurs
     */
    AddressDTO updateAddress(final AddressDTO addressDTO) throws MilkroadServiceException;
}
