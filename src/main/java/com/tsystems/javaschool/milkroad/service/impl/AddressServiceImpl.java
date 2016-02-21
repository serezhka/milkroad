package com.tsystems.javaschool.milkroad.service.impl;

import com.tsystems.javaschool.milkroad.dao.AddressDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.AddressEntity;
import com.tsystems.javaschool.milkroad.service.AddressService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.service.exception.ServiceExceptionType;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 20.02.2016.
 */
public class AddressServiceImpl extends AbstractService implements AddressService {
    private static final Logger LOGGER = Logger.getLogger(AddressServiceImpl.class);

    private final AddressDAO<AddressEntity, Long> addressDAO;

    public AddressServiceImpl(final EntityManager entityManager, final AddressDAO<AddressEntity, Long> addressDAO) {
        super(entityManager);
        this.addressDAO = addressDAO;
    }

    @Override
    public List<AddressDTO> getAllAddresses() throws MilkroadServiceException {
        final List<AddressDTO> addressDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<AddressEntity> addressEntities = addressDAO.getAll();
            for (final AddressEntity addressEntity : addressEntities) {
                addressDTOs.add(new AddressDTO(addressEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading orders");
            throw new MilkroadServiceException(e, ServiceExceptionType.UNKNOWN_ERROR);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return addressDTOs;
    }

    @Override
    public List<AddressDTO> getAddressesByUser(final UserDTO userDTO) throws MilkroadServiceException {
        /*final List<AddressDTO> addressDTOs = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            final List<AddressEntity> addressEntities = addressDAO.getAll();
            for (final AddressEntity addressEntity : addressEntities) {
                addressDTOs.add(new AddressDTO(addressEntity));
            }
            entityManager.getTransaction().commit();
        } catch (final MilkroadDAOException e) {
            LOGGER.error("Error while loading orders");
            throw new MilkroadServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return addressDTOs;*/
        return null;
    }
}
