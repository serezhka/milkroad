<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="category" type="com.tsystems.javaschool.milkroad.dto.CategoryDTO"--%>
<%--@elvariable id="categories" type="java.util.List<com.tsystems.javaschool.milkroad.dto.CategoryDTO>"--%>
<%--@elvariable id="attributes" type="java.util.List<com.tsystems.javaschool.milkroad.dto.AttributeDTO>"--%>
<%--@elvariable id="errors" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="filter" type="com.tsystems.javaschool.milkroad.controller.form.FilterForm"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
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
    <div class="filter">
        <div class="catalog-left-header">
            <h3>Filter</h3>
        </div>
        <form action="${requestScope['javax.servlet.forward.servlet_path']}" method="GET">
            <c:if test="${not empty filter.category}">
                <input type="hidden" name="category" value="${filter.category}"/>
            </c:if>
            <c:forEach items="${errors}" var="error">
                <span class="error">${error.value}</span>
            </c:forEach>
            <ul class="catalog-left-menu">
                <li>
                    <div>Min price <input name="minPrice" type="text" value="${filter.minPrice}"></div>
                </li>
                <li>
                    <div>Max price <input name="maxPrice" type="text" value="${filter.maxPrice}"></div>
                </li>
                <c:forEach items="${attributes}" var="attribute" varStatus="status">
                    <li><label><input type="checkbox" name="attributes" value="${attribute.id}"
                                      <c:if test="${not empty filter.attributes && filter.attributes.contains(attribute.id)}">checked</c:if>
                    />${attribute.name}</label></li>
                </c:forEach>
            </ul>
            <input type="submit" value="Apply"/>
        </form>
    </div>
</div>
