<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="AUTHED_USER" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>

<%-- Top header (search, login, logout, cart) --%>
<div class="header">
    <div class="header-top">
        <div class="container">
            <div class="search">
                <form>
                    <%--suppress HtmlFormInputWithoutLabel --%>
                    <input type="text" value="Search " onfocus="this.value = '';"
                           onblur="if (this.value == '') {this.value = 'Search';}">
                    <input type="submit" value="Go">
                </form>
            </div>
            <div class="header-left">
                <ul>
                    <c:choose>
                        <c:when test="${not empty AUTHED_USER}">
                            <li><a href="${pageContext.request.contextPath}/account">Hi, ${AUTHED_USER.firstName}</a></li>
                            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                            <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <div class="cart">
                    <a href="${pageContext.request.contextPath}/cart">
                        <div class="total">
                            <span class="header_cart_total">$100.00</span> (<span id="header_cart_quantity"
                                                                                  class="header_cart_quantity"></span>3
                            items)
                        </div>
                        <img src="images/cart.png" alt=""/>
                    </a>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
