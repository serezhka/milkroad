<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="categories" type="java.util.List<com.tsystems.javaschool.milkroad.dto.CategoryDTO>"--%>

<div class="col-md-3 catalog-left">
    <div class="categories">
        <div class="catalog-left-header">
            <h3>Categories</h3>
        </div>
        <ul class="catalog-left-menu">
            <li><a href="<c:url value="/catalog"/>">All</a></li>
            <c:forEach items="${categories}" var="category">
                <c:url value="/catalog" var="categoryURL">
                    <c:param name="category" value="${category.name}"/>
                </c:url>
                <li><a href="${categoryURL}">${category.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>
