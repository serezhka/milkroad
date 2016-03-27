<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                <li><span>${parameter.attribute.name}</span><span>: ${parameter.value}</span></li>
            </c:forEach>
            <li><span>Remain count</span><span>: ${product.count}</span></li>
        </ul>
        <a href="javascript:;" class="product-add-cart" onclick="addProductToCart(${product.article});">ADD TO CART</a>
    </div>
</div>
