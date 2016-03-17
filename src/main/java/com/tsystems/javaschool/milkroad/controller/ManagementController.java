package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.dto.*;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.StatisticsService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import javafx.util.Pair;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
public class ManagementController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private StatisticsService statisticsService;

    /*@InitBinder
    private void initBinder(final WebDataBinder binder) throws ServletException {
        binder.registerCustomEditor(PaymentMethodEnum.class, new EnumConverter<>(PaymentMethodEnum.class));
        binder.registerCustomEditor(PaymentStatusEnum.class, new EnumConverter<>(PaymentStatusEnum.class));
        binder.registerCustomEditor(ShippingMethodEnum.class, new EnumConverter<>(ShippingMethodEnum.class));
        binder.registerCustomEditor(ShippingStatusEnum.class, new EnumConverter<>(ShippingStatusEnum.class));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, false));
    }*/

    @RequestMapping(value = "/management/editOrders", method = RequestMethod.GET)
    public ModelAndView editOrdersPage() {
        final List<OrderDTO> orders;
        try {
            orders = orderService.getAllOrders();
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("orderlist").addObject("orders", orders);
    }

    @RequestMapping(value = "/management/editCategories", method = RequestMethod.GET)
    public ModelAndView editCategoriesPage() {
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        try {
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("categories-attributes")
                .addObject("categories", categories)
                .addObject("attributes", attributes);
    }

    @RequestMapping(value = "/management/editProducts", method = RequestMethod.GET)
    public ModelAndView editProductsPage() {
        final List<ProductDTO> products;
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        try {
            products = catalogService.getAllProducts();
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("productlist")
                .addObject("products", products)
                .addObject("categories", categories)
                .addObject("attributes", attributes);
    }

    @RequestMapping(value = "/management/addProduct", method = RequestMethod.GET)
    public ModelAndView addProductPage() {
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        try {
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("product-edit")
                .addObject("categories", categories)
                .addObject("attributes", attributes);
    }

    @RequestMapping(value = "/management/statistics", method = RequestMethod.GET)
    public ModelAndView statisticsPage() {
        final List<Pair<ProductDTO, Integer>> products;
        final List<Pair<UserDTO, BigDecimal>> users;
        final BigDecimal totalCash;
        final BigDecimal totalCashThisMonth;
        final BigDecimal totalCashLast7Days;
        final Calendar calendar = Calendar.getInstance();
        final long currentDay = calendar.getTime().getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        final long monthFirstDay = calendar.getTime().getTime();
        final long sevenDaysAgo = currentDay - 7 * 1000 * 60 * 60 * 24;
        try {
            products = statisticsService.getTopProducts(10);
            users = statisticsService.getTopCustomers(10);
            totalCash = statisticsService.getTotalCash();
            totalCashThisMonth = statisticsService.getTotalCashByPeriod(new Date(monthFirstDay), new Date(currentDay));
            totalCashLast7Days = statisticsService.getTotalCashByPeriod(new Date(sevenDaysAgo), new Date(currentDay));
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("statistics")
                .addObject("products", products)
                .addObject("users", users)
                .addObject("totalCash", totalCash)
                .addObject("totalCashThisMonth", totalCashThisMonth)
                .addObject("totalCashLast7Days", totalCashLast7Days);
    }

    @RequestMapping(value = "/management/updateOrder", method = RequestMethod.POST)
    public ModelAndView updateOrder(
            @ModelAttribute final OrderDTO orderDTO) {
        try {
            orderService.updateOrder(orderDTO);
        } catch (final MilkroadServiceException e) {
            // TODO Catch ORDER_NOT_EXISTS error
            return new ModelAndView(new MappingJackson2JsonView())
                    .addObject("errors", Collections.singletonList(e.getType()));
        }
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @RequestMapping(value = "/management/updateCategory", method = RequestMethod.POST)
    public ModelAndView updateCategory(
            @ModelAttribute final CategoryDTO categoryDTO,
            // TODO Validate
            final BindingResult bindingResult) {
        try {
            catalogService.updateCategory(categoryDTO);
        } catch (final MilkroadServiceException e) {
            return new ModelAndView(new MappingJackson2JsonView())
                    .addObject("errors", Collections.singletonList(e.getType()));
        }
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @RequestMapping(value = "/management/updateAttribute", method = RequestMethod.POST)
    public ModelAndView updateAttribute(
            @ModelAttribute final AttributeDTO attributeDTO,
            // TODO Validate
            final BindingResult bindingResult) {
        try {
            catalogService.updateAttribute(attributeDTO);
        } catch (final MilkroadServiceException e) {
            return new ModelAndView(new MappingJackson2JsonView())
                    .addObject("errors", Collections.singletonList(e.getType()));
        }
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @RequestMapping(value = "/management/createCategory", method = RequestMethod.POST)
    public ModelAndView createCategory(
            @ModelAttribute @Valid final CategoryDTO categoryDTO,
            final BindingResult bindingResult) {
        final Set<String> errors = getErrors(bindingResult);
        try {
            if (errors.isEmpty()) catalogService.createCategory(categoryDTO);
        } catch (final MilkroadServiceException e) {
            errors.add(e.getType().name());
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @RequestMapping(value = "/management/createAttribute", method = RequestMethod.POST)
    public ModelAndView createAttribute(
            @ModelAttribute final AttributeDTO attributeDTO,
            // TODO Validate
            final BindingResult bindingResult) {
        try {
            catalogService.createAttribute(attributeDTO);
        } catch (final MilkroadServiceException e) {
            return new ModelAndView(new MappingJackson2JsonView())
                    .addObject("errors", Collections.singletonList(e.getType()));
        }
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public void handleIOException(final TypeMismatchException e, final HttpServletRequest request) {
        // TODO Implement me
    }

    private Set<String> getErrors(final BindingResult bindingResult) {
        final Set<String> errors = new HashSet<>();
        for (final ObjectError error : bindingResult.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return errors;
    }
}
