<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="products" type="java.util.List<com.tsystems.javaschool.milkroad.dto.ProductDTO>"--%>

<div class="col-md-9 catalog-goods">
    <c:if test="${empty products}">
        <h3 class="milkroad-h3">No products found :(</h3>
    </c:if>
    <c:forEach items="${products}" var="product">
        <div class="col-md-4 product-item">
            <div class="product-preview">
                <c:url value="/catalog" var="productURL">
                    <c:param name="article" value="${product.article}"/>
                </c:url>
                <c:url value="/images/product/default.png" var="defaultImageURL"/>
                <c:url value="/images/product/product_${product.article}.png" var="productImageURL"/>
                <a href="${productURL}"><img class="img-responsive" src="${productImageURL}"
                                             onerror="this.onerror=null;this.src='${defaultImageURL}';" alt=""></a>
            </div>
            <p class="product-name">${product.name}</p>
            <c:url value="/cart" var="cartURL">
                <c:param name="article" value="${product.article}"/>
            </c:url>
            <a href="javascript:;" class="product-add" onclick="addProductToCart(${product.article});">
                <p class="product-price"><i> </i>${product.price}</p>
            </a>
        </div>
    </c:forEach>
</div>
