package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Sergey on 14.03.2016.
 */
@Controller
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String catalogAll(final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final List<ProductDTO> products;
        try {
            categories = catalogService.getAllCategories();
            request.setAttribute("categories", categories);
            products = catalogService.getAllProducts();
            request.setAttribute("products", products);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "catalog";
    }

    @RequestMapping(value = "/catalog", params = "category", method = RequestMethod.GET)
    public String catalogByCategory(
            @RequestParam final String category,
            final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final List<ProductDTO> products;
        try {
            categories = catalogService.getAllCategories();
            request.setAttribute("categories", categories);
            products = catalogService.getAllProductsByCategory(category);
            request.setAttribute("products", products);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "catalog";
    }

    @RequestMapping(value = "/catalog", params = "search", method = RequestMethod.GET)
    public String catalogBySearch(
            @RequestParam final String search,
            final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final List<ProductDTO> products;
        try {
            categories = catalogService.getAllCategories();
            request.setAttribute("categories", categories);
            products = catalogService.searchProductByName(search);
            request.setAttribute("products", products);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "catalog";
    }

    @RequestMapping(value = "/catalog", params = "article", method = RequestMethod.GET)
    public String catalogByArticle(
            @RequestParam final String article,
            final HttpServletRequest request) {
        final List<CategoryDTO> categories;
        final ProductDTO product;
        try {
            categories = catalogService.getAllCategories();
            request.setAttribute("categories", categories);
            // TODO Validate article
            product = catalogService.getProductByArticle(Long.valueOf(article));
            request.setAttribute("product", product);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "catalog";
    }
}
