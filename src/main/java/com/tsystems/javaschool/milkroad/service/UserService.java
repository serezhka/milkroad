package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
public interface UserService {
    List<UserDTO> getAllUsers() throws MilkroadServiceException;
}
