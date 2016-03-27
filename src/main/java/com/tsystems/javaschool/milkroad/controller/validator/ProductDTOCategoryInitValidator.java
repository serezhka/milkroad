package com.tsystems.javaschool.milkroad.controller.validator;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Sergey on 22.03.2016.
 */
@Component
public class ProductDTOCategoryInitValidator implements Validator {
    @Override
    public boolean supports(final Class<?> clazz) {
        return ProductDTO.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final ProductDTO productDTO = (ProductDTO) target;
        if (productDTO.getCategory() == null || productDTO.getCategory().getId() == null) {
            errors.rejectValue("category", "NotNull", "Category is required field");
        }
    }
}
