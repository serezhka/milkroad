package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.MrUserEntity;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 09.02.2016.
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final EntityManager entityManager;
    private final UserDAO userDAO;

    public UserServiceImpl(final EntityManager entityManager, final UserDAO userDAO) {
        this.entityManager = entityManager;
        this.userDAO = userDAO;
    }

    @Override
    public List<UserDTO> getAllUsers() throws MilkroadServiceException {
        final List<UserDTO> userDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<MrUserEntity> userEntities = userDAO.getAll();
            for (final MrUserEntity userEntity : userEntities) {
                userDTOs.add(new UserDTO(userEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading users");
            throw new MilkroadServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return userDTOs;
    }
}
