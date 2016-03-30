package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
public interface UserService {
    List<UserDTO> getAllUsers() throws MilkroadServiceException;

    UserDTO getUserByEmail(final String email) throws MilkroadServiceException;

    @Deprecated
    UserDTO getUserByEmailAndPass(final String email, final String pass) throws MilkroadServiceException;

    UserDTO addNewUser(final UserDTO userDTO, final String pass) throws MilkroadServiceException;

    UserDTO updateUserInfo(final UserDTO userDTO) throws MilkroadServiceException;

    UserDTO updateUserPass(final UserDTO userDTO, final String pass) throws MilkroadServiceException;
}
