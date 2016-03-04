<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="AUTHED_USER" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>
<%--@elvariable id="cartTotal" type="java.math.BigDecimal"--%>

<c:set var="user" value="${AUTHED_USER}"/>

<%-- Top header (search, login, logout, cart) --%>
<div class="header">
    <div class="header-top">
        <div class="container">
            <div class="search">
                <form action="${pageContext.request.contextPath}/catalog" method="get">
                    <%--suppress HtmlFormInputWithoutLabel --%>
                    <input name="search" type="text" value="Search " onfocus="this.value = '';"
                           onblur="if (this.value == '') {this.value = 'Search';}">
                    <input type="submit" value="Go">
                </form>
            </div>
            <div class="header-left">
                <ul>
                    <c:choose>
                        <c:when test="${not empty user}">
                            <li><a href="${pageContext.request.contextPath}/profile">Hi, ${user.firstName}</a>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                            <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <%-- TODO Compare user type without string constants --%>
                <c:if test="${empty user or (not empty user and (user.userType eq 'CUSTOMER'))}">
                    <div class="cart">
                        <a href="${pageContext.request.contextPath}/cart">
                            <div class="total">
                            <span class="header_cart_total">
                                <c:choose>
                                    <c:when test="${not empty cartTotal}">
                                        <c:out value="$${cartTotal}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="$0.00"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            </div>
                            <img src="images/cart.png" alt=""/>
                        </a>
                    </div>
                </c:if>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
