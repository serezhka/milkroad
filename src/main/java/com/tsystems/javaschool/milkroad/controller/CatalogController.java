package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.form.FilterForm;
import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ParameterDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 14.03.2016.
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    /*@RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String catalogAll(final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        final List<ProductDTO> products;
        try {
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
            products = catalogService.getAllProducts();
            request.setAttribute("categories", categories);
            request.setAttribute("attributes", attributes);
            request.setAttribute("products", products);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "catalog";
    }*/

    /*@RequestMapping(value = "/catalog", params = "category", method = RequestMethod.GET)
    public String catalogByCategory(
            @RequestParam final String category,
            final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        final List<ProductDTO> products;
        try {
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
            products = catalogService.getAllProductsByCategory(category);
            request.setAttribute("category", category);
            request.setAttribute("categories", categories);
            request.setAttribute("attributes", attributes);
            request.setAttribute("products", products);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "catalog";
    }*/

    @RequestMapping(method = RequestMethod.GET)
    public String catalogByFilter(
            @ModelAttribute final FilterForm filterForm,
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
                    /* All attributes exists check */
                    if (filterForm.getAttributes() != null && !filterForm.getAttributes().isEmpty()) {
                        for (final Long attributeID : filterForm.getAttributes()) {
                            boolean attrExists = false;
                            for (final ParameterDTO parameterDTO : product.getParameters()) {
                                if (parameterDTO.getAttribute().getId().equals(attributeID)) {
                                    attrExists = true;
                                    break;
                                }
                            }
                            if (!attrExists) {
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
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
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
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
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
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "catalog";
    }
}
