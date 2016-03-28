<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Catalog"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="product" type="com.tsystems.javaschool.milkroad.dto.ProductDTO"--%>

<div class="container">
    <jsp:include page="catalog-menu.jsp"/>
    <c:choose>
        <c:when test="${not empty product}">
            <jsp:include page="product.jsp"/>
        </c:when>
        <c:otherwise>
            <jsp:include page="goods.jsp"/>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp"/>
