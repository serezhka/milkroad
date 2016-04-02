package com.tsystems.javaschool.milkroad.controller.security;

import com.tsystems.javaschool.milkroad.dao.UserDAO;
import com.tsystems.javaschool.milkroad.dao.exception.MilkroadDAOException;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.util.PassUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 29.03.2016.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = Logger.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserDAO<UserEntity, Long> userDAO;

    @Autowired
    private String dbErrorMessage;

    @Override
    @Transactional
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String email = authentication.getName();
        final String password = (String) authentication.getCredentials();
        final UserEntity userEntity;
        try {
            userEntity = userDAO.getByEmail(email);
        } catch (final MilkroadDAOException e) {
            LOGGER.error(e);
            throw new AuthenticationServiceException(dbErrorMessage);
        }
        if (userEntity == null) {
            throw new BadCredentialsException("Username not found");
        }
        try {
            if (!PassUtil.verifyPass(password, userEntity.getPassHash(), userEntity.getPassSalt())) {
                throw new BadCredentialsException("Wrong password");
            }
        } catch (final NoSuchAlgorithmException e) {
            LOGGER.error(e);
            throw new AuthenticationServiceException("Pass utils error");
        }
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userEntity.getUserType().name()));
        return new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
