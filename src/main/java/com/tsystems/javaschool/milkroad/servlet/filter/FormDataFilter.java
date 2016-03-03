package com.tsystems.javaschool.milkroad.servlet.filter;

import com.tsystems.javaschool.milkroad.model.PaymentMethodEnum;
import com.tsystems.javaschool.milkroad.model.ShippingMethodEnum;
import com.tsystems.javaschool.milkroad.util.FormDataValidator;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergey on 23.02.2016.
 */
public class FormDataFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final Set<String> errors;
        final Object errorsObj = request.getAttribute("errors");
        //noinspection unchecked
        errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
        final HashMap<String, String> input = new HashMap<>();
        final String formName = request.getParameter("formName");
        if (formName != null && !formName.trim().isEmpty()) {
            switch (formName) {
                case "registerForm": {
                    final String firstName = request.getParameter("firstname");
                    if (!FormDataValidator.validateName(firstName)) {
                        errors.add("FIRST_NAME_ERROR");
                    }
                    final String lastName = request.getParameter("lastname");
                    if (!FormDataValidator.validateName(lastName)) {
                        errors.add("LAST_NAME_ERROR");
                    }
                    final String birthday = request.getParameter("birthday");
                    if (!FormDataValidator.validateDate(birthday)) {
                        errors.add("DATE_ERROR");
                    }
                    final String email = request.getParameter("email");
                    if (!FormDataValidator.validateEmail(email)) {
                        errors.add("EMAIL_ERROR");
                    }
                    final String pass = request.getParameter("pass");
                    if (!FormDataValidator.validatePass(pass)) {
                        errors.add("PASS_ERROR");
                    }
                    input.put("firstname", firstName);
                    input.put("lastname", lastName);
                    input.put("birthday", birthday);
                    input.put("email", email);
                    break;
                }

                case "loginForm": {
                    final String email = request.getParameter("email");
                    if (!FormDataValidator.validateEmail(email)) {
                        errors.add("EMAIL_ERROR");
                    }
                    final String pass = request.getParameter("pass");
                    if (!FormDataValidator.validatePass(pass)) {
                        errors.add("PASS_ERROR");
                    }
                    input.put("email", email);
                    break;
                }

                case "checkoutForm": {
                    final String paymentMethod = request.getParameter("payment");
                    if (!FormDataValidator.validateEnum(PaymentMethodEnum.class, paymentMethod)) {
                        errors.add("PAYMENT_METHOD_ERROR");
                    }
                    final String shippingMethod = request.getParameter("shipping");
                    if (!FormDataValidator.validateEnum(ShippingMethodEnum.class, shippingMethod)) {
                        errors.add("SHIPPING_METHOD_ERROR");
                    }
                    final String addressId = request.getParameter("address");
                    if (!FormDataValidator.validateLong(addressId)) {
                        errors.add("ADDRESS_ERRROR");
                    }
                    // TODO Validate cart, cart total price
                    break;
                }
            }
        }
        final String actionName = request.getParameter("action");
        if (actionName != null && !actionName.isEmpty()) {
            switch (actionName) {
                case "updateOrder": {
                    // TODO Validate order id, payment method/status, shipping method/status, note
                }

                case "updateCategory": {
                    final String categoryID = request.getParameter("categoryID");
                    if (!FormDataValidator.validateLong(categoryID)) {
                        errors.add("CATEGORY_ID_ERROR");
                    }
                    final String categoryName = request.getParameter("categoryName");
                    if (!FormDataValidator.validateName(categoryName)) {
                        errors.add("CATEGORY_NAME_ERROR");
                    }
                    break;
                }

                case "createCategory": {
                    final String categoryName = request.getParameter("categoryName");
                    if (!FormDataValidator.validateName(categoryName)) {
                        errors.add("CATEGORY_NAME_ERROR");
                    }
                    break;
                }

                case "updateAttribute": {
                    final String attributeID = request.getParameter("attributeID");
                    if (!FormDataValidator.validateLong(attributeID)) {
                        errors.add("ATTRIBUTE_ID_ERROR");
                    }
                    final String attributeName = request.getParameter("attributeName");
                    if (!FormDataValidator.validateName(attributeName)) {
                        errors.add("ATTRIBUTE_NAME_ERROR");
                    }
                    break;
                }

                case "createAttribute": {
                    // TODO Validate desc
                    final String attributeName = request.getParameter("attributeName");
                    if (!FormDataValidator.validateName(attributeName)) {
                        errors.add("ATTRIBUTE_NAME_ERROR");
                    }
                    break;
                }

                case "updateProduct": {
                    final String productArticle = request.getParameter("productArticle");
                    if (!FormDataValidator.validateLong(productArticle)) {
                        errors.add("PRODUCT_ARTICLE_ERROR");
                    }
                    final String productName = request.getParameter("productName");
                    if (!FormDataValidator.validateName(productName)) {
                        errors.add("PRODUCT_NAME_ERROR");
                    }
                    final String productPrice = request.getParameter("productPrice");
                    if (!FormDataValidator.validateBigDecimal(productPrice)) {
                        errors.add("PRODUCT_PRICE_ERROR");
                    }
                    final String productCount = request.getParameter("productCount");
                    if (!FormDataValidator.validateInteger(productCount)) {
                        errors.add("PRODUCT_COUNT_ERROR");
                    }
                    final String productCategoryID = request.getParameter("productCategoryID");
                    if (!FormDataValidator.validateLong(productCategoryID)) {
                        errors.add("PRODUCT_CATEGORY_ERROR");
                    }
                    // TODO Validate parameters array
                    break;
                }

                case "createProduct": {
                    final String productName = request.getParameter("productName");
                    if (!FormDataValidator.validateName(productName)) {
                        errors.add("PRODUCT_NAME_ERROR");
                    }
                    final String productPrice = request.getParameter("productPrice");
                    if (!FormDataValidator.validateBigDecimal(productPrice)) {
                        errors.add("PRODUCT_PRICE_ERROR");
                    }
                    final String productCount = request.getParameter("productCount");
                    if (!FormDataValidator.validateInteger(productCount)) {
                        errors.add("PRODUCT_COUNT_ERROR");
                    }
                    final String productCategoryID = request.getParameter("productCategoryID");
                    if (!FormDataValidator.validateLong(productCategoryID)) {
                        errors.add("PRODUCT_CATEGORY_ERROR");
                    }
                    input.put("productName", productName);
                    input.put("productPrice", productPrice);
                    input.put("productCount", productCount);
                    input.put("productCategoryID", productCategoryID);
                    input.put("productDesc", request.getParameter("productDescription"));
                    // TODO Validate parameters array
                    break;
                }

                case "updateProfile": {
                    final String firstName = request.getParameter("firstname");
                    if (!FormDataValidator.validateName(firstName)) {
                        errors.add("FIRST_NAME_ERROR");
                    }
                    final String lastName = request.getParameter("lastname");
                    if (!FormDataValidator.validateName(lastName)) {
                        errors.add("LAST_NAME_ERROR");
                    }
                    final String birthday = request.getParameter("birthday");
                    if (!FormDataValidator.validateDate(birthday)) {
                        errors.add("DATE_ERROR");
                    }
                    final String pass = request.getParameter("pass");
                    if (!(pass != null && pass.isEmpty()) && !FormDataValidator.validatePass(pass)) {
                        errors.add("PASS_ERROR");
                    }
                    input.put("firstname", firstName);
                    input.put("lastname", lastName);
                    input.put("birthday", birthday);
                    break;
                }

                case "updateAddress": {
                    final String addressID = request.getParameter("addressID");
                    if (!FormDataValidator.validateLong(addressID)) {
                        errors.add("ADDRESS_ID_ERROR");
                    }
                    final String country = request.getParameter("country");
                    if (!FormDataValidator.validateName(country)) {
                        errors.add("COUNTRY_ERROR");
                    }
                    final String city = request.getParameter("city");
                    if (!FormDataValidator.validateName(city)) {
                        errors.add("CITY_ERROR");
                    }
                    final String postcode = request.getParameter("postcode");
                    if (!FormDataValidator.validateInteger(postcode)) {
                        errors.add("POSTCODE_ERROR");
                    }
                    final String street = request.getParameter("street");
                    if (!FormDataValidator.validateName(street)) {
                        errors.add("STREET_ERROR");
                    }
                    final String building = request.getParameter("building");
                    if (!FormDataValidator.validateName(building)) {
                        errors.add("BUILDING_ERROR");
                    }
                    final String apartment = request.getParameter("apartment");
                    if (!FormDataValidator.validateName(apartment)) {
                        errors.add("APARTMENT_ERROR");
                    }
                    break;
                }

                case "createAddress": {
                    final String country = request.getParameter("country");
                    if (!FormDataValidator.validateName(country)) {
                        errors.add("COUNTRY_ERROR");
                    }
                    final String city = request.getParameter("city");
                    if (!FormDataValidator.validateName(city)) {
                        errors.add("CITY_ERROR");
                    }
                    final String postcode = request.getParameter("postcode");
                    if (!FormDataValidator.validateInteger(postcode)) {
                        errors.add("POSTCODE_ERROR");
                    }
                    final String street = request.getParameter("street");
                    if (!FormDataValidator.validateName(street)) {
                        errors.add("STREET_ERROR");
                    }
                    final String building = request.getParameter("building");
                    if (!FormDataValidator.validateName(building)) {
                        errors.add("BUILDING_ERROR");
                    }
                    final String apartment = request.getParameter("apartment");
                    if (!FormDataValidator.validateName(apartment)) {
                        errors.add("APARTMENT_ERROR");
                    }
                    input.put("country", country);
                    input.put("city", city);
                    input.put("postcode", postcode);
                    input.put("street", street);
                    input.put("building", building);
                    input.put("apartment", apartment);
                    break;
                }
            }
        }
        request.setAttribute("action", (actionName == null) ? "" : actionName);
        request.setAttribute("input", input);
        request.setAttribute("errors", errors);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
