package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.MrProductCategoryEntity;
import com.tsystems.javaschool.milkroad.model.MrProductEntity;
import com.tsystems.javaschool.milkroad.model.MrProductParameterEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public class ProductDTO {
    public UserDTO seller;
    public String name;
    public Category category;
    public BigDecimal price;
    public int count;
    public String description;
    public List<Parameter> parameters;

    public ProductDTO() {
        parameters = new ArrayList<>();
    }

    public ProductDTO(final UserDTO seller, final String name, final Category category, final BigDecimal price,
                      final int count, final String description, final List<Parameter> parameters) {
        this();
        this.seller = seller;
        this.name = name;
        this.category = category;
        this.price = price;
        this.count = count;
        this.description = description;
        this.parameters = parameters;
    }

    public ProductDTO(final MrProductEntity productEntity) {
        this();
        this.seller = new UserDTO(productEntity.getSeller());
        this.name = productEntity.getProductName();
        this.category = new Category(productEntity.getCategory());
        this.price = productEntity.getProductPrice();
        this.count = productEntity.getRemainCount();
        this.description = productEntity.getDescription();
        for (final MrProductParameterEntity parameterEntity : productEntity.getParameters()) {
            parameters.add(new Parameter(parameterEntity));
        }
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(final UserDTO seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(final List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public class Category {
        public String name;
        public String description;

        public Category() {
        }

        public Category(final String name, final String description) {
            this.name = name;
            this.description = description;
        }

        public Category(final MrProductCategoryEntity categoryEntity) {
            this.name = categoryEntity.getCategoryName();
            this.description = categoryEntity.getDescription();
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }
    }

    public class Parameter {
        public String name;
        public String value;

        public Parameter() {
        }

        public Parameter(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        public Parameter(final MrProductParameterEntity parameterEntity) {
            this.name = parameterEntity.getAttribute().getAttributeName();
            this.value = parameterEntity.getAttributeValue();
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }
}
