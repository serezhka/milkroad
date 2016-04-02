package com.tsystems.javaschool.milkroad.service;

import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import java.util.List;

/**
 * User service interface.
 * Provides user review and management features
 * <p>
 * Created by Sergey on 10.02.2016.
 */
public interface UserService {

    /**
     * Returns all users
     *
     * @return list of users
     * @throws MilkroadServiceException if an error with database occurs
     */
    List<UserDTO> getAllUsers() throws MilkroadServiceException;

    /**
     * Returns user by email
     *
     * @param email email
     * @return user info
     * @throws MilkroadServiceException if user doesn't exist or an error with database occurs
     */
    UserDTO getUserByEmail(final String email) throws MilkroadServiceException;

    /**
     * Returns user by email and pass
     *
     * @param email email
     * @param pass  pass
     * @return user info
     * @throws MilkroadServiceException if user doesn't exist, if pass invalid, if pass util or db error occurs
     */
    @Deprecated
    UserDTO getUserByEmailAndPass(final String email, final String pass) throws MilkroadServiceException;

    /**
     * Creates new user
     *
     * @param userDTO user details
     * @param pass    pass
     * @return created user info
     * @throws MilkroadServiceException if user with email already exists, if pass util or db error occurs
     */
    UserDTO addNewUser(final UserDTO userDTO, final String pass) throws MilkroadServiceException;

    /**
     * Updates user details
     *
     * @param userDTO user details, id field is required
     * @return updated user info
     * @throws MilkroadServiceException if user doesn't exist or db error occurs
     */
    UserDTO updateUserInfo(final UserDTO userDTO) throws MilkroadServiceException;

    /**
     * Updates user pass
     *
     * @param userDTO user details, id field is required
     * @param pass    new pass
     * @return updated user info
     * @throws MilkroadServiceException if user doesn't exist, if pass util or db error occurs
     */
    UserDTO updateUserPass(final UserDTO userDTO, final String pass) throws MilkroadServiceException;
}
