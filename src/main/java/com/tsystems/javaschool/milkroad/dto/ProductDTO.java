package com.tsystems.javaschool.milkroad.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private CategoryDTO category;
    private BigDecimal price;
    private Integer count;
    private String description;
    private List<ParameterDTO> parameters;

    public ProductDTO() {
        parameters = new ArrayList<>();
    }

    public ProductDTO(final Long article, final UserDTO seller, final String name, final CategoryDTO category,
                      final BigDecimal price, final Integer count, final String description) {
        this();
        this.article = article;
        this.seller = seller;
        this.name = name;
        this.category = category;
        this.price = price;
        this.count = count;
        this.description = description;
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

    @Size(min = 1, max = 45, message = "Name must not be blank, not exceed 45 characters")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @NotNull(message = "Category is required field")
    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(final CategoryDTO category) {
        this.category = category;
    }

    @NotNull(message = "Price is required field")
    @Min(value = 0, message = "Price should be positive value")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = "Remain count is required field")
    @Min(value = 0, message = "Remain count should be positive value")
    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    @Size(max = 45, message = "Description must not exceed 45 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<ParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(final List<ParameterDTO> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(final ParameterDTO parameterDTO) {
        this.parameters.add(parameterDTO);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        final ProductDTO that = (ProductDTO) o;
        return article != null ? article.equals(that.article) : that.article == null;
    }

    @Override
    public int hashCode() {
        return article != null ? article.hashCode() : 0;
    }
}
