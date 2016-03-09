package com.tsystems.javaschool.milkroad.util;

import com.tsystems.javaschool.milkroad.dto.*;
import com.tsystems.javaschool.milkroad.model.*;

/**
 * Created by Sergey on 09.03.2016.
 */
public class EntityDTOConverter {
    /**
     * Converts ProductAttributeEntity -> AttributeDTO
     *
     * @param attributeEntity AttributeEntity
     * @return AttributeDTO
     */
    public static AttributeDTO attributeDTO(final ProductAttributeEntity attributeEntity) {
        return new AttributeDTO(attributeEntity.getId(), attributeEntity.getAttributeName(), attributeEntity.getDescription());
    }

    /**
     * Converts ProductCategoryEntity -> CategoryDTO
     *
     * @param categoryEntity CategoryEntity
     * @return CategoryDTO
     */
    public static CategoryDTO categoryDTO(final ProductCategoryEntity categoryEntity) {
        return new CategoryDTO(categoryEntity.getId(), categoryEntity.getCategoryName(), categoryEntity.getDescription());
    }

    /**
     * Converts AddressEntity -> AddressDTO
     *
     * @param addressEntity AddressEntity
     * @return AddressDTO
     */
    public static AddressDTO addressDTO(final AddressEntity addressEntity) {
        return new AddressDTO(
                addressEntity.getId(),
                addressEntity.getCountry(),
                addressEntity.getCity(),
                addressEntity.getPostcode(),
                addressEntity.getStreet(),
                addressEntity.getBuilding(),
                addressEntity.getApartment()
        );
    }

    /**
     * Converts UserEntity -> UserDTO
     *
     * @param userEntity UserEntity
     * @return UserDTO
     */
    public static UserDTO userDTO(final UserEntity userEntity) {
        final UserDTO userDTO = new UserDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getBirthday(),
                userEntity.getEmail(),
                userEntity.getUserType()
        );
        for (final AddressEntity addressEntity : userEntity.getAdresses()) {
            userDTO.addAddress(addressDTO(addressEntity));
        }
        return userDTO;
    }

    /**
     * Converts ProductEntity -> ProductDTO
     *
     * @param productEntity ProductEntity
     * @return ProductDTO
     */
    public static ProductDTO productDTO(final ProductEntity productEntity) {
        final ProductDTO productDTO = new ProductDTO(
                productEntity.getId(),
                userDTO(productEntity.getSeller()),
                productEntity.getProductName(),
                categoryDTO(productEntity.getCategory()),
                productEntity.getProductPrice(),
                productEntity.getRemainCount(),
                productEntity.getDescription()
        );
        for (final ProductParameterEntity parameterEntity : productEntity.getParameters()) {
            productDTO.addParameter(
                    parameterEntity.getAttribute().getAttributeName(),
                    parameterEntity.getAttributeValue()
            );
        }
        return productDTO;
    }

    /**
     * Converts OrderEntity -> OrderDTO
     *
     * @param orderEntity OrderEntity
     * @return OrderDTO
     */
    public static OrderDTO orderDTO(final OrderEntity orderEntity) {
        final OrderDTO orderDTO = new OrderDTO(
                orderEntity.getId(),
                userDTO(orderEntity.getCustomer()),
                addressDTO(orderEntity.getAddress()),
                orderEntity.getPriceTotal(),
                orderEntity.getPaymentMethod(),
                orderEntity.getPaymentStatus(),
                orderEntity.getShippingMethod(),
                orderEntity.getShippingStatus(),
                orderEntity.getDate(),
                orderEntity.getNote()
        );
        for (final OrderDetailEntity detailEntity : orderEntity.getOrderDetails()) {
            orderDTO.addDetail(
                    productDTO(detailEntity.getProduct()),
                    detailEntity.getProductCount(),
                    detailEntity.getPriceTotal()
            );
        }
        return orderDTO;
    }
}
