package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.PassHash;
import com.tsystems.javaschool.milkroad.util.PassUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 09.02.2016.
 */
public class UserServiceImpl extends AbstractService implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDAO<UserEntity, Long> userDAO;

    public UserServiceImpl(final EntityManager entityManager,
                           final UserDAO<UserEntity, Long> userDAO) {
        super(entityManager);
        this.userDAO = userDAO;
    }

    @Override
    public List<UserDTO> getAllUsers() throws MilkroadServiceException {
        final List<UserDTO> userDTOs = new ArrayList<>();
        try {
            final List<UserEntity> userEntities = userDAO.getAll();
            for (final UserEntity userEntity : userEntities) {
                entityManager.refresh(userEntity);
                userDTOs.add(new UserDTO(userEntity));
            }
            return userDTOs;
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading users");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }

    @Override
    public UserDTO getUserByEmail(final String email) throws MilkroadServiceException {
        final UserEntity userEntity = getUserEntityByEmail(email);
        if (userEntity != null) {
            return new UserDTO(userEntity);
        } else {
            LOGGER.warn("User with email = " + email + " doesn't exist");
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
        }
    }

    @Override
    public UserDTO getUserByEmailAndPass(final String email, final String pass) throws MilkroadServiceException {
        final UserEntity userEntity = getUserEntityByEmail(email);
        if (userEntity != null) {
            try {
                if (PassUtil.verifyPass(pass, userEntity.getPassHash(), userEntity.getPassSalt())) {
                    return new UserDTO(userEntity);
                } else {
                    LOGGER.warn("Pass is invalid. Email = " + email + ", pass =" + pass);
                    throw new MilkroadServiceException(MilkroadServiceException.Type.PASS_INVALID);
                }
            } catch (final NoSuchAlgorithmException e) {
                LOGGER.error("PassUtils error. Email = " + email + ", pass =" + pass);
                throw new MilkroadServiceException(MilkroadServiceException.Type.PASS_UTILS_ERROR);
            }
        } else {
            LOGGER.warn("User with email = " + email + " doesn't exist");
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
        }
    }

    @Override
    public UserDTO addNewUser(final UserDTO userDTO, final String pass) throws MilkroadServiceException {
        if (getUserEntityByEmail(userDTO.getEmail()) != null) {
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_EMAIL_ALREADY_EXISTS);
        }
        final UserEntity userEntity = new UserEntity(userDTO);
        try {
            final PassHash passHash = PassUtil.createPassHash(pass);
            userEntity.setPassHash(passHash.getHash());
            userEntity.setPassSalt(passHash.getSalt());
            entityManager.getTransaction().begin();
            userDAO.persist(userEntity);
            entityManager.getTransaction().commit();
            return new UserDTO(userEntity);
        } catch (final NoSuchAlgorithmException e) {
            LOGGER.error("PassUtils Error while adding new user");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.PASS_UTILS_ERROR);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while adding new user");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public UserDTO updateUserInfo(final UserDTO userDTO) throws MilkroadServiceException {
        final UserEntity userEntity = getUserEntityByEmail(userDTO.getEmail());
        if (userEntity == null) {
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
        }
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setBirthday(userDTO.getBirthday());
        try {
            entityManager.getTransaction().begin();
            userDAO.merge(userEntity);
            entityManager.getTransaction().commit();
            return new UserDTO(userEntity);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating user info with email = " + userDTO.getEmail());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public UserDTO updateUserPass(final UserDTO userDTO, final String pass) throws MilkroadServiceException {
        final UserEntity userEntity = getUserEntityByEmail(userDTO.getEmail());
        if (userEntity == null) {
            throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
        }
        final PassHash passHash;
        try {
            passHash = PassUtil.createPassHash(pass);
            userEntity.setPassHash(passHash.getHash());
            userEntity.setPassSalt(passHash.getSalt());
            entityManager.getTransaction().begin();
            userDAO.persist(userEntity);
            entityManager.getTransaction().commit();
            return new UserDTO(userEntity);
        } catch (final NoSuchAlgorithmException e) {
            LOGGER.error("PassUtils Error while updating user pass");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.PASS_UTILS_ERROR);
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating user pass");
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    private UserEntity getUserEntityByEmail(final String email) throws MilkroadServiceException {
        final UserEntity userEntity;
        try {
            userEntity = userDAO.getByEmail(email);
            entityManager.refresh(userEntity);
            return userEntity;
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading user with email = " + email);
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        }
    }
}
