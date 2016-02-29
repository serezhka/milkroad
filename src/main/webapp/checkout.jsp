<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Catalog"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="AUTHED_USER" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>
<%--@elvariable id="cart" type="java.util.Map<com.tsystems.javaschool.milkroad.dto.ProductDTO, java.lang.Integer>"--%>
<%--@elvariable id="cartTotal" type="java.math.BigDecimal"--%>

<c:set var="user" value="${AUTHED_USER}"/>

<div class="container">
    <c:choose>
        <c:when test="${not empty cart}">
            <div class="col-md-9 cart-items">
                <div class="price-details">
                    <h3>Order Details</h3>
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
            </div>
            <div class="col-md-9 checkout-order-details">
                <form action="${pageContext.request.contextPath}/checkout" method="post">
                    <input type="hidden" name="formName" value="checkoutForm"/>
                    <h3>Payment method</h3>
                    <label class="radio-inline">
                        <input type="radio" name="payment" value="ONLINE" checked>Online
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="payment" value="CASH">Cash
                    </label>
                    <h3>Shipping method</h3>
                    <label class="radio-inline">
                        <input type="radio" name="shipping" value="POST" checked>Post
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="shipping" value="PICKUP">Pickup
                    </label>
                    <h3>Delivery address</h3>
                    <c:if test="${empty user.addresses}">
                        <span class="error">Add address first</span>
                    </c:if>
                    <c:forEach items="${user.addresses}" var="address">
                        <label class="radio">
                            <input type="radio" name="address" value="${address.id}" checked>${address}
                        </label>
                    </c:forEach>
                    <input type="submit" value="ORDER">
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <h1>The cart is empty</h1>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp"/>
