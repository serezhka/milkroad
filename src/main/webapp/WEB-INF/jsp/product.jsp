<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--@elvariable id="product" type="com.tsystems.javaschool.milkroad.dto.ProductDTO"--%>

<div class="col-md-9 product-info">
    <div class="col-md-5 product-image">
        <c:url value="/images/product/default.png" var="defaultImageURL"/>
        <c:url value="/images/product/product_${product.article}.png" var="productImageURL"/>
        <img class="img-responsive" src="${productImageURL}"
             onerror="this.onerror=null;this.src='${defaultImageURL}';" alt="">
    </div>
    <div class="col-md-7 product-details">
        <h4>${product.name}</h4>
        <h5>${product.price}</h5>
        <p>${product.description}</p>
        <ul class="product-attributes">
            <c:forEach items="${product.parameters}" var="parameter">
                <li>
                    <span>${parameter.attribute.name}</span><span>: ${parameter.value}${parameter.attribute.description}</span>
                </li>
            </c:forEach>
            <li><span>Remain count</span><span>: ${product.count}</span></li>
        </ul>
        <sec:authorize access="hasAnyAuthority('ADMIN', 'SELLER')">
            <c:url value="/management/editProduct" var="productEditURL">
                <c:param name="article" value="${product.article}"/>
            </c:url>
            <a href="${productEditURL}" class="product-add-cart">EDIT</a>
        </sec:authorize>
        <sec:authorize access="hasAnyAuthority('ANONYMOUS', 'CUSTOMER')">
            <a href="javascript:;" class="product-add-cart" onclick="addProductToCart(${product.article});">ADD TO
                CART</a>
        </sec:authorize>
    </div>
</div>
