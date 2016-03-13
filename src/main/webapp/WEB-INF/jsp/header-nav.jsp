<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="AUTHED_USER" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>

<c:set var="user" value="${AUTHED_USER}"/>

<nav class="navbar navbar-inverse" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="<c:url value="/images/milkroad-logo.png"/>" alt="" width="150" height="50">
            </a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/catalog">Catalog</a>
                </li>
                <c:choose>
                    <c:when test="${(user.userType eq 'ADMIN') or (user.userType eq 'SELLER')}">
                        <li>
                            <c:url value="/management" var="ordersURL">
                                <c:param name="action" value="editOrders"/>
                            </c:url>
                            <a href="${ordersURL}">Orders</a>
                        </li>
                        <li>
                            <c:url value="/management" var="categoriesURL">
                                <c:param name="action" value="editCategories"/>
                            </c:url>
                            <a href="${categoriesURL}">Categories & attributes</a>
                        </li>
                        <li>
                            <c:url value="/management" var="productsURL">
                                <c:param name="action" value="editProducts"/>
                            </c:url>
                            <a href="${productsURL}">Products</a>
                        </li>
                        <li>
                            <c:url value="/management" var="productsURL">
                                <c:param name="action" value="addProduct"/>
                            </c:url>
                            <a href="${productsURL}">Add product</a>
                        </li>
                        <li>
                            <c:url value="/management" var="statisticsURL">
                                <c:param name="action" value="viewStatistics"/>
                            </c:url>
                            <a href="${statisticsURL}">Statistics</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
                <li>
                    <a href="${pageContext.request.contextPath}/profile">Profile</a>
                </li>
            </ul>
        </div>
    </div>
</nav>