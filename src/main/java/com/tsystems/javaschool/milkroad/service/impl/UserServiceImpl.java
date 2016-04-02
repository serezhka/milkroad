package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.DTOEntityConverter;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import com.tsystems.javaschool.milkroad.util.PassHash;
import com.tsystems.javaschool.milkroad.util.PassUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 09.02.2016.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDAO<UserEntity, Long> userDAO;

    @Autowired
    public UserServiceImpl(final UserDAO<UserEntity, Long> userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<UserDTO> getAllUsers() throws MilkroadServiceException {
        final List<UserEntity> userEntities;
        try {
            userEntities = userDAO.getAll();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading users");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        final List<UserDTO> userDTOs = new ArrayList<>();
        for (final UserEntity userEntity : userEntities) {
            userDTOs.add(EntityDTOConverter.userDTO(userEntity));
        }
        return userDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDTO getUserByEmail(final String email) throws MilkroadServiceException {
        final UserEntity userEntity = getUserEntityByEmail(email);
        if (userEntity != null) {
            return EntityDTOConverter.userDTO(userEntity);
        } else {
            LOGGER.warn("User with email = " + email + " doesn't exist");
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDTO getUserByEmailAndPass(final String email, final String pass) throws MilkroadServiceException {
        final UserEntity userEntity = getUserEntityByEmail(email);
        if (userEntity != null) {
            try {
                if (PassUtil.verifyPass(pass, userEntity.getPassHash(), userEntity.getPassSalt())) {
                    return EntityDTOConverter.userDTO(userEntity);
                } else {
                    LOGGER.warn("Pass is invalid. Email = " + email + ", pass =" + pass);
                    throw new MilkroadServiceException(MilkroadServiceException.Type.PASS_INVALID);
                }
            } catch (final NoSuchAlgorithmException e) {
                LOGGER.error(e);
                LOGGER.error("PassUtils error. Email = " + email + ", pass =" + pass);
                throw new MilkroadServiceException(MilkroadServiceException.Type.PASS_UTILS_ERROR);
            }
        } else {
            LOGGER.warn("User with email = " + email + " doesn't exist");
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDTO addNewUser(final UserDTO userDTO, final String pass) throws MilkroadServiceException {
        if (getUserEntityByEmail(userDTO.getEmail()) != null) {
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_EMAIL_ALREADY_EXISTS);
        }
        final UserEntity userEntity = DTOEntityConverter.userEntity(userDTO);
        try {
            final PassHash passHash = PassUtil.createPassHash(pass);
            userEntity.setPassHash(passHash.getHash());
            userEntity.setPassSalt(passHash.getSalt());
            userDAO.persist(userEntity);
        } catch (final NoSuchAlgorithmException e) {
            LOGGER.error("PassUtils Error while adding new user");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.PASS_UTILS_ERROR);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while adding new user");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.userDTO(userEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDTO updateUserInfo(final UserDTO userDTO) throws MilkroadServiceException {
        final UserEntity userEntity;
        try {
            userEntity = userDAO.getByID(userDTO.getId());
            if (userEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
            }
            userEntity.setFirstName(userDTO.getFirstName());
            userEntity.setLastName(userDTO.getLastName());
            userEntity.setBirthday(userDTO.getBirthday());
            userDAO.merge(userEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating user info with email = " + userDTO.getEmail());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.userDTO(userEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDTO updateUserPass(final UserDTO userDTO, final String pass) throws MilkroadServiceException {
        final UserEntity userEntity;
        try {
            userEntity = userDAO.getByID(userDTO.getId());
            if (userEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
            }
            final PassHash passHash;
            passHash = PassUtil.createPassHash(pass);
            userEntity.setPassHash(passHash.getHash());
            userEntity.setPassSalt(passHash.getSalt());
            userDAO.merge(userEntity);
        } catch (final NoSuchAlgorithmException e) {
            LOGGER.error("PassUtils Error while updating user pass");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.PASS_UTILS_ERROR);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating user pass");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
        return EntityDTOConverter.userDTO(userEntity);
    }

    /**
     * Returns user entity by email
     *
     * @param email email
     * @return user entity
     * @throws MilkroadServiceException if db error occurs
     */
    private UserEntity getUserEntityByEmail(final String email) throws MilkroadServiceException {
        try {
            return userDAO.getByEmail(email);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading user with email = " + email);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }
}
