<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="products" type="java.util.List<com.tsystems.javaschool.milkroad.dto.ProductDTO>"--%>

<div class="col-md-9 catalog-goods">
    <c:forEach items="${products}" var="product">
        <div class="col-md-4 product-item">
            <div class="product-preview">
                <c:url value="/catalog" var="productURL">
                    <c:param name="article" value="${product.article}"/>
                </c:url>
                <a href="${productURL}"><img class="img-responsive" src="images/product-item-image.png" alt=""></a>
            </div>
            <p class="product-name">${product.name}</p>
            <a href="#" class="product-add"><p class="product-price"><i> </i>${product.price}</p></a>
        </div>
    </c:forEach>
</div>
