package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.controller.validator.ProductDTOCategoryInitValidator;
import com.tsystems.javaschool.milkroad.dto.*;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.StatisticsService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
@RequestMapping("/management")
public class ManagementController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private ProductDTOCategoryInitValidator categoryInitValidator;

    @Value("#{servletContext.getRealPath('/images/product')}")
    private String productImagesPath;

    /*@InitBinder
    public void initBinder(final WebDataBinder binder) {
        // TODO You must call spring validator manually to avoid conflict with hibernate
        binder.setValidator(categoryInitValidator);
    }*/

    @RequestMapping(value = "/editOrders", method = RequestMethod.GET)
    public ModelAndView editOrdersPage() {
        final List<OrderDTO> orders;
        try {
            orders = orderService.getAllOrders();
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("orderlist").addObject("orders", orders);
    }

    @RequestMapping(value = "/editCategories", method = RequestMethod.GET)
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

    @RequestMapping(value = "/editProducts", method = RequestMethod.GET)
    public ModelAndView editProductsPage() {
        final List<ProductDTO> products;
        try {
            products = catalogService.getAllProducts();
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("productlist")
                .addObject("products", products);
    }

    @RequestMapping(value = "/editProduct", params = {"article"}, method = RequestMethod.GET)
    public ModelAndView editProductPage(@RequestParam final Long article) {
        final ProductDTO product;
        final List<CategoryDTO> categories;
        final List<AttributeDTO> attributes;
        try {
            product = catalogService.getProductByArticle(article);
            categories = catalogService.getAllCategories();
            attributes = catalogService.getAllAttributes();
        } catch (final MilkroadServiceException e) {
            return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
        }
        return new ModelAndView("product-edit")
                .addObject("product", product)
                .addObject("categories", categories)
                .addObject("attributes", attributes);
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
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

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ModelAndView statisticsPage() {
        final Map<ProductDTO, Integer> products;
        final Map<UserDTO, BigDecimal> users;
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

    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public ModelAndView updateOrder(
            @ModelAttribute @Valid final OrderDTO orderDTO,
            final BindingResult bindingResult) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        try {
            if (errors.isEmpty()) orderService.updateOrder(orderDTO);
        } catch (final MilkroadServiceException e) {
            errors.put("milkroad", e.getType().name());
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public ModelAndView updateCategory(
            @ModelAttribute @Valid final CategoryDTO categoryDTO,
            final BindingResult bindingResult) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        try {
            if (errors.isEmpty()) catalogService.updateCategory(categoryDTO);
        } catch (final MilkroadServiceException e) {
            errors.put("milkroad", e.getType().name());
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @RequestMapping(value = "/updateAttribute", method = RequestMethod.POST)
    public ModelAndView updateAttribute(
            @ModelAttribute @Valid final AttributeDTO attributeDTO,
            final BindingResult bindingResult) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        try {
            if (errors.isEmpty()) catalogService.updateAttribute(attributeDTO);
        } catch (final MilkroadServiceException e) {
            errors.put("milkroad", e.getType().name());
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    public ModelAndView createCategory(
            @ModelAttribute @Valid final CategoryDTO categoryDTO,
            final BindingResult bindingResult) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        try {
            if (errors.isEmpty()) catalogService.createCategory(categoryDTO);
        } catch (final MilkroadServiceException e) {
            errors.put("milkroad", e.getType().name());
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @RequestMapping(value = "/createAttribute", method = RequestMethod.POST)
    public ModelAndView createAttribute(
            @ModelAttribute @Valid final AttributeDTO attributeDTO,
            final BindingResult bindingResult) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        try {
            if (errors.isEmpty()) catalogService.createAttribute(attributeDTO);
        } catch (final MilkroadServiceException e) {
            errors.put("milkroad", e.getType().name());
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public ModelAndView createProduct(
            @ModelAttribute @Valid final ProductDTO productDTO,
            final BindingResult bindingResult,
            @RequestParam(value = "image", required = false) final MultipartFile image,
            final HttpSession session) {
        categoryInitValidator.validate(productDTO, bindingResult);
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.isEmpty()) {
            try {
                //productDTO.setSeller(AuthUtil.getAuthedUser(session));
                final Long article = catalogService.createProduct(productDTO).getArticle();
                if (!image.isEmpty()) {
                    saveProductImage(image, article, errors);
                }
            } catch (final MilkroadServiceException e) {
                errors.put("milkroad", e.getType().name());
            }
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public ModelAndView updateProduct(
            @ModelAttribute @Valid final ProductDTO productDTO,
            final BindingResult bindingResult,
            @RequestParam(value = "image", required = false) final MultipartFile image) {
        categoryInitValidator.validate(productDTO, bindingResult);
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.isEmpty()) {
            try {
                final Long article = catalogService.updateProduct(productDTO).getArticle();
                if (!image.isEmpty()) {
                    saveProductImage(image, article, errors);
                }
            } catch (final MilkroadServiceException e) {
                errors.put("milkroad", e.getType().name());
            }
        }
        return new ModelAndView(new MappingJackson2JsonView()).addObject("errors", errors);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public void handleIOException(final TypeMismatchException e, final HttpServletRequest request) {
        System.out.println("debug");
    }

    /**
     * Saves product image to productImagesPath
     * Image file name is "product_[article].png"
     *
     * @param image   MultipartFile image
     * @param article product article
     * @param errors  errors map
     */
    private void saveProductImage(final MultipartFile image, final Long article, final Map<String, String> errors) {
        final String imageExt = "png"; // FilenameUtils.getExtension(image.getOriginalFilename());
        final File imageFile = new File(productImagesPath, "product_" + article + "." + imageExt);
        try {
            final BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            ImageIO.write(bufferedImage, imageExt, imageFile);
        } catch (final IOException e) {
            errors.put("image", "Can't load product image");
        }
    }
}
