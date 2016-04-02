package com.tsystems.javaschool.milkroad.util;

import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.AddressEntity;
import com.tsystems.javaschool.milkroad.model.ProductAttributeEntity;
import com.tsystems.javaschool.milkroad.model.ProductCategoryEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;

/**
 * Created by Sergey on 09.03.2016.
 */
public class DTOEntityConverter {

    private DTOEntityConverter() {
        // DTOEntityConverter is stateless
    }

    /**
     * Converts AttributeDTO -> ProductAttributeEntity
     *
     * @param attributeDTO AttributeDTO
     * @return ProductAttributeEntity
     */
    public static ProductAttributeEntity productAttributeEntity(final AttributeDTO attributeDTO) {
        return new ProductAttributeEntity(attributeDTO.getId(), attributeDTO.getName(), attributeDTO.getDescription());
    }

    /**
     * Converts CategoryDTO -> ProductCategoryEntity
     *
     * @param categoryDTO CategoryDTO
     * @return ProductCategoryEntity
     */
    public static ProductCategoryEntity productCategoryEntity(final CategoryDTO categoryDTO) {
        return new ProductCategoryEntity(categoryDTO.getId(), categoryDTO.getName(), categoryDTO.getDescription());
    }

    /**
     * Converts AddressDTO -> AddressEntity
     *
     * @param addressDTO AddressDTO
     * @return AddressEntity
     */
    public static AddressEntity addressEntity(final AddressDTO addressDTO) {
        return new AddressEntity(
                addressDTO.getId(),
                addressDTO.getCountry(),
                addressDTO.getCity(),
                addressDTO.getPostcode(),
                addressDTO.getStreet(),
                addressDTO.getBuilding(),
                addressDTO.getApartment()
        );
    }

    /**
     * Converts UserDTO -> UserEntity
     *
     * @param userDTO UserDTO
     * @return UserEntity, where pass hash & hash salt are {@code null}
     */
    public static UserEntity userEntity(final UserDTO userDTO) {
        return new UserEntity(
                userDTO.getId(),
                userDTO.getUserType(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getBirthday(),
                userDTO.getEmail(),
                null, // Pass hash
                null // Hash salt
        );
    }
}
