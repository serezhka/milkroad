package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.AddressDAO;
import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.AddressEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.service.AddressService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.DTOEntityConverter;
import com.tsystems.javaschool.milkroad.util.EntityDTOConverter;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

/**
 * Created by Sergey on 20.02.2016.
 */
public class AddressServiceImpl extends AbstractService implements AddressService {
    private static final Logger LOGGER = Logger.getLogger(AddressServiceImpl.class);

    private final AddressDAO<AddressEntity, Long> addressDAO;
    private final UserDAO<UserEntity, Long> userDAO;

    public AddressServiceImpl(final EntityManager entityManager,
                              final AddressDAO<AddressEntity, Long> addressDAO, final UserDAO<UserEntity, Long> userDAO) {
        super(entityManager);
        this.addressDAO = addressDAO;
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO addAddressToUser(final UserDTO userDTO, final AddressDTO addressDTO) throws MilkroadServiceException {
        final UserEntity userEntity;
        final AddressEntity addressEntity = DTOEntityConverter.addressEntity(addressDTO);
        try {
            entityManager.getTransaction().begin();
            userEntity = userDAO.getByID(userDTO.getId());
            if (userEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.USER_NOT_EXISTS);
            }
            userEntity.addAddress(addressEntity);
            userDAO.merge(userEntity);
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while adding address for user with email = " + userDTO.getEmail());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return EntityDTOConverter.userDTO(userEntity);
    }

    @Override
    public AddressDTO updateAddress(final AddressDTO addressDTO) throws MilkroadServiceException {
        final AddressEntity addressEntity;
        try {

            entityManager.getTransaction().begin();
            addressEntity = addressDAO.getByID(addressDTO.getId());
            if (addressEntity == null) {
                throw new MilkroadServiceException(MilkroadServiceException.Type.ADDRESS_NOT_EXISTS);
            }
            addressEntity.setCountry(addressDTO.getCountry());
            addressEntity.setCity(addressDTO.getCity());
            addressEntity.setPostcode(addressDTO.getPostcode());
            addressEntity.setStreet(addressDTO.getStreet());
            addressEntity.setBuilding(addressDTO.getBuilding());
            addressEntity.setApartment(addressDTO.getApartment());
            addressDAO.merge(addressEntity);
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while updating address with id = " + addressDTO.getId());
            throw new MilkroadServiceException(e, MilkroadServiceException.Type.DAO_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return EntityDTOConverter.addressDTO(addressEntity);
    }
}
