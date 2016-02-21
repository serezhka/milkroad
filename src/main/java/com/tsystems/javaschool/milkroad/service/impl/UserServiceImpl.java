package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.service.exception.ServiceExceptionType;
import com.tsystems.javaschool.milkroad.util.PassHash;
import com.tsystems.javaschool.milkroad.util.PassUtil;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

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

    public UserServiceImpl(final EntityManager entityManager, final UserDAO<UserEntity, Long> userDAO) {
        super(entityManager);
        this.userDAO = userDAO;
    }

    @Override
    public List<UserDTO> getAllUsers() throws MilkroadServiceException {
        final List<UserDTO> userDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<UserEntity> userEntities = userDAO.getAll();
            // Don't forget that addresses have FetchType.LAZY
            for (final UserEntity userEntity : userEntities) {
                // Non-obvious addresses fetch
                userDTOs.add(new UserDTO(userEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading users");
            throw new MilkroadServiceException(e, ServiceExceptionType.UNKNOWN_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return userDTOs;
    }

    @Override
    public UserDTO getUserByEmail(final String email) throws MilkroadServiceException {
        final UserDTO userDTO;
        final UserEntity userEntity;
        try {
            entityManager.getTransaction().begin();
            userEntity = userDAO.getByEmail(email);
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading user with email = " + email);
            throw new MilkroadServiceException(e, ServiceExceptionType.UNKNOWN_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        if (userEntity != null) {
            userDTO = new UserDTO(userEntity);
        } else {
            LOGGER.warn("User with email = " + email + " doesn't exist");
            userDTO = null;
        }
        return userDTO;
    }

    @Override
    public UserDTO addNewUser(final UserDTO userDTO, final String pass) throws MilkroadServiceException {
        final UserEntity userEntity = new UserEntity(userDTO);
        try {
            final PassHash passHash = PassUtil.createPassHash(pass);
            userEntity.setPassHash(passHash.getHash());
            userEntity.setPassSalt(passHash.getSalt());
            entityManager.getTransaction().begin();
            userDAO.persist(userEntity);
            entityManager.getTransaction().commit();
        } catch (final NoSuchAlgorithmException e) {
            LOGGER.error("Error while adding new user");
            throw new MilkroadServiceException(e, ServiceExceptionType.UNKNOWN_ERROR);
        } catch (final MilkroadDAOException e) {
            // TODO THIS IS DOG-NAIL, SPIKE-NAIL, CROOKED NAIL
            try {
                if (((ConstraintViolationException) e.getCause().getCause()).getSQLException().getMessage().contains("Duplicate")) {
                    throw new MilkroadServiceException(e, ServiceExceptionType.USER_EMAIL_ALREADY_EXISTS);
                }
            } catch (final ClassCastException ignored) {
            }
            throw new MilkroadServiceException(e, ServiceExceptionType.UNKNOWN_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return new UserDTO(userEntity);
    }
}
