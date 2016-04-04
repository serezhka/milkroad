package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.form.FilterAttribute;
import com.tsystems.javaschool.milkroad.controller.form.FilterForm;
import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ParameterDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 14.03.2016.
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private static final Logger LOGGER = Logger.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private String dbErrorMessage;

    @RequestMapping(method = RequestMethod.GET)
    public String catalogByFilter(
            @ModelAttribute @Valid final FilterForm filterForm,
            final BindingResult bindingResult,
            final HttpServletRequest request) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        try {
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
            if (errors.isEmpty()) {
                final List<ProductDTO> products;
                // TODO Do it with criteria query in service
                if (filterForm.getCategory() != null) {
                    products = catalogService.getAllProductsByCategory(filterForm.getCategory());
                } else {
                    products = catalogService.getAllProducts();
                }
                for (final Iterator<ProductDTO> iterator = products.iterator(); iterator.hasNext(); ) {
                    final ProductDTO product = iterator.next();
                    /* Min price check */
                    if (filterForm.getMinPrice() != null && product.getPrice().compareTo(filterForm.getMinPrice()) < 0) {
                        iterator.remove();
                        continue;
                    }
                    /* Max price check */
                    if (filterForm.getMaxPrice() != null && product.getPrice().compareTo(filterForm.getMaxPrice()) > 0) {
                        iterator.remove();
                        continue;
                    }
                    /* All attributes check */
                    if (filterForm.getAttributes() != null && !filterForm.getAttributes().isEmpty()) {
                        for (final FilterAttribute attribute : filterForm.getAttributes()) {
                            if (attribute == null || attribute.getId() == null) {
                                continue;
                            }
                            boolean attrValid = false;
                            for (final ParameterDTO parameterDTO : product.getParameters()) {
                                if (parameterDTO.getAttribute().getId().equals(attribute.getId())) {
                                    boolean minBoundValid = true;
                                    boolean maxBoundValid = true;
                                    if (attribute.getMinValue() != null && !attribute.getMinValue().isEmpty()) {
                                        minBoundValid = parameterDTO.getValue().compareToIgnoreCase(attribute.getMinValue()) >= 0;
                                    }
                                    if (attribute.getMaxValue() != null && !attribute.getMaxValue().isEmpty()) {
                                        maxBoundValid = parameterDTO.getValue().compareToIgnoreCase(attribute.getMaxValue()) <= 0;
                                    }
                                    attrValid = minBoundValid && maxBoundValid;
                                    break;
                                }
                            }
                            if (!attrValid) {
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
                request.setAttribute("products", products);
            }
            request.setAttribute("filter", filterForm);
            request.setAttribute("categories", categories);
            request.setAttribute("attributes", attributes);
            request.setAttribute("errors", errors);
        } catch (final MilkroadServiceException e) {
            LOGGER.error(e);
            return dbErrorPage(request);
        }
        return "catalog";
    }

    @RequestMapping(params = "search", method = RequestMethod.GET)
    public String catalogBySearch(
            @RequestParam final String search,
            final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        final List<ProductDTO> products;
        try {
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
            products = catalogService.searchProductByName(search);
            request.setAttribute("categories", categories);
            request.setAttribute("attributes", attributes);
            request.setAttribute("products", products);
        } catch (final MilkroadServiceException e) {
            LOGGER.error(e);
            return dbErrorPage(request);
        }
        return "catalog";
    }

    @RequestMapping(params = "article", method = RequestMethod.GET)
    public String catalogByArticle(
            @RequestParam final Long article,
            final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        final ProductDTO product;
        try {
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
            product = catalogService.getProductByArticle(article);
            request.setAttribute("categories", categories);
            request.setAttribute("attributes", attributes);
            request.setAttribute("product", product);
        } catch (final MilkroadServiceException e) {
            LOGGER.error(e);
            return dbErrorPage(request);
        }
        return "catalog";
    }

    private String dbErrorPage(final HttpServletRequest request) {
        request.setAttribute("message", dbErrorMessage);
        return "single-message";
    }
}
