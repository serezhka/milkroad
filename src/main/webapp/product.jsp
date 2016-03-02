<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="product" type="com.tsystems.javaschool.milkroad.dto.ProductDTO"--%>

<div class="col-md-9 product-info">
    <div class="col-md-5 product-image">
        <img class="img-responsive" src="images/product-item-image.png" alt="">
    </div>
    <div class="col-md-7 product-details">
        <h4>${product.name}</h4>
        <h5>${product.price}</h5>
        <p>${product.description}</p>
        <ul class="product-attributes">
            <c:forEach items="${product.parameters}" var="parameter">
                <li><span>${parameter.name}</span><span>: ${parameter.value}</span></li>
            </c:forEach>
        </ul>
        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="article" value="${product.article}"/>
            <a href="javascript:;" class="product-add-cart" onclick="parentNode.submit();">ADD TO CART</a>
        </form>
    </div>
</div>
