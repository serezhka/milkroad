<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Cart"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="cart" type="java.util.Map<com.tsystems.javaschool.milkroad.dto.ProductDTO, java.lang.Integer>"--%>
<%--@elvariable id="cartTotal" type="java.math.BigDecimal"--%>

<div class="container">
    <c:choose>
        <c:when test="${not empty cart}">
            <div class="col-md-9 cart-items">
                <c:forEach items="${cart}" var="cartItem">
                    <div class="cart-item">
                        <div class="cart-item-remove">
                            <a href="javascript:;"
                               onclick="removeProductFromCart(${cartItem.key.article});"></a>
                        </div>
                        <div class="btn-group btn-group-justified cart-item-count" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-default"
                                        onclick="removeProductOnceFromCart(${cartItem.key.article});">-
                                </button>
                            </div>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-default">${cartItem.value}</button>
                            </div>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-default"
                                        onclick="addProductToCart(${cartItem.key.article});">+
                                </button>
                            </div>
                        </div>
                        <div class="cart-item-image">
                            <c:choose>
                                <c:when test="${cartItem.key.article < 11}">
                                    <img class="img-responsive" src="images/product/product_${cartItem.key.article}.jpg"
                                         alt="">
                                </c:when>
                                <c:otherwise>
                                    <img class="img-responsive" src="images/product-item-image.png" alt="">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="cart-item-info">
                            <c:url value="/catalog" var="productURL">
                                <c:param name="article" value="${cartItem.key.article}"/>
                            </c:url>
                            <h3><a href="${productURL}">${cartItem.key.name}</a><span>${cartItem.key.description}</span>
                            </h3>
                            <ul class="cart-item-details">
                                <c:forEach items="${cartItem.key.parameters}" var="parameter">
                                    <li><p>${parameter.name} : ${parameter.value}</p></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-3 cart-total">
                <a class="cart-continue" href="${pageContext.request.contextPath}/catalog">Back to catalog</a>
                <div class="price-details">
                    <h3>Price Details</h3>
                    <span>Total</span>
                    <span>${cartTotal}</span>
                    <span>Discount</span>
                    <span>---</span>
                    <span>Delivery Charges</span>
                    <span>---</span>
                    <div class="clearfix"></div>
                </div>
                <ul class="cart-total-price">
                    <li><h4>TOTAL</h4></li>
                    <li><span>${cartTotal}</span></li>
                </ul>
                <div class="clearfix"></div>
                <a class="cart-order" href="${pageContext.request.contextPath}/checkout">Order</a>
            </div>
        </c:when>
        <c:otherwise>
            <h1>The cart is empty</h1>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp"/>
