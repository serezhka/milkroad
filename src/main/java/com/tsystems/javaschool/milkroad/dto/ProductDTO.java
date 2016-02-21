package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.ProductCategoryEntity;
import com.tsystems.javaschool.milkroad.model.ProductEntity;
import com.tsystems.javaschool.milkroad.model.ProductParameterEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 14.02.2016.
 */
public class ProductDTO {
    private Long article;
    private UserDTO seller;
    private String name;
    private Category category;
    private BigDecimal price;
    private Integer count;
    private String description;
    private List<Parameter> parameters;

    public ProductDTO() {
        parameters = new ArrayList<>();
    }

    public ProductDTO(final Long article, final UserDTO seller, final String name, final Category category,
                      final BigDecimal price, final Integer count, final String description,
                      final List<Parameter> parameters) {
        this();
        this.article = article;
        this.seller = seller;
        this.name = name;
        this.category = category;
        this.price = price;
        this.count = count;
        this.description = description;
        this.parameters = parameters;
    }

    public ProductDTO(final ProductEntity productEntity) {
        this();
        this.article = productEntity.getId();
        this.seller = new UserDTO(productEntity.getSeller());
        this.name = productEntity.getProductName();
        this.category = new Category(productEntity.getCategory());
        this.price = productEntity.getProductPrice();
        this.count = productEntity.getRemainCount();
        this.description = productEntity.getDescription();
        for (final ProductParameterEntity parameterEntity : productEntity.getParameters()) {
            parameters.add(new Parameter(parameterEntity));
        }
    }

    public Long getArticle() {
        return article;
    }

    public void setArticle(final Long article) {
        this.article = article;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
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
        private String name;
        private String description;

        public Category() {
        }

        public Category(final String name, final String description) {
            this.name = name;
            this.description = description;
        }

        public Category(final ProductCategoryEntity categoryEntity) {
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
        private String name;
        private String value;

        public Parameter() {
        }

        public Parameter(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        public Parameter(final ProductParameterEntity parameterEntity) {
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
