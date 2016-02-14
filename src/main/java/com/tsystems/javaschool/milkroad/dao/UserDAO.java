package com.tsystems.javaschool.milkroad.dao;

import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.MrUserEntity;

/**
 * Created by Sergey on 10.02.2016.
 */
public interface UserDAO extends DAO<MrUserEntity, Long> {
    MrUserEntity getByEmail(final String email) throws MilkroadDAOException;
}
