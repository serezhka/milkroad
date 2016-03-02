<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Management"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="categories" type="java.util.List<com.tsystems.javaschool.milkroad.dto.CategoryDTO>"--%>
<%--@elvariable id="attributes" type="java.util.List<com.tsystems.javaschool.milkroad.dto.AttributeDTO>"--%>
<%--@elvariable id="errors" type="java.util.Set<java.lang.String>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="col-md-6 product-categories">
        <h3 class="milkroad-h3">Categories</h3>
        <c:if test="${errors.contains('CATEGORY_NAME_ERROR')}">
            <span class="error">Incorrect category name</span>
        </c:if>
        <c:if test="${errors.contains('CATEGORY_DUPLICATE_ERROR')}">
            <span class="error">Duplicate category name</span>
        </c:if>
        <div class="table-responsive">
            <table class="table" border=3>
                <thead>
                <tr>
                    <th>Category name</th>
                    <th>Category decription</th>
                    <th>Update</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${categories}" var="category">
                    <tr>
                        <td><input id="categoryName_${category.id}" type="text" class="form-control"
                                   value="${category.name}"/></td>
                        <td><input id="categoryDesc_${category.id}" type="text" class="form-control"
                                   value="${category.description}"/></td>
                        <td><a class="btn btn-success" href="javascript:;"
                               onclick="updateCategoryDetails(${category.id})">
                            <i class="fa fa-pencil-square-o"></i> </a></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><input id="categoryNameNew" type="text" class="form-control"
                               value=""/></td>
                    <td><input id="categoryDescNew" type="text" class="form-control"
                               value=""/></td>
                    <td><a class="btn btn-success" href="javascript:;"
                           onclick="addCategory()">
                        <i class="fa fa-plus"></i></a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-6 product-attributes">
        <h3 class="milkroad-h3">Attributes</h3>
        <c:if test="${errors.contains('ATTRIBUTE_NAME_ERROR')}">
            <span class="error">Incorrect attribute name</span>
        </c:if>
        <c:if test="${errors.contains('ATTRIBUTE_DUPLICATE_ERROR')}">
            <span class="error">Duplicate attribute name</span>
        </c:if>
        <div class="table-responsive">
            <table class="table" border=3>
                <thead>
                <tr>
                    <th>Attribute name</th>
                    <th>Attribute decription</th>
                    <th>Update</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${attributes}" var="attribute">
                    <tr>
                        <td><input id="attributeName_${attribute.id}" type="text" class="form-control"
                                   value="${attribute.name}"/></td>
                        <td><input id="attributeDesc_${attribute.id}" type="text" class="form-control"
                                   value="${attribute.description}"/></td>
                        <td><a class="btn btn-success" href="javascript:;"
                               onclick="updateAttributeDetails(${attribute.id})">
                            <i class="fa fa-pencil-square-o"></i> </a></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><input id="attributeNameNew" type="text" class="form-control"
                               value=""/></td>
                    <td><input id="attributeDescNew" type="text" class="form-control"
                               value=""/></td>
                    <td><a class="btn btn-success" href="javascript:;"
                           onclick="addAttribute()">
                        <i class="fa fa-plus"></i></a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>