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

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="col-md-6 product-categories">
        <h3 class="milkroad-h3">Categories</h3>
        <span class="error" id="category_name_error"></span>
        <span class="error" id="category_description_error"></span>
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
                    <tr id="category_${category.id}">
                        <td><input id="categoryName_${category.id}" type="text" class="form-control"
                                   value="${category.name}"/></td>
                        <td><input id="categoryDesc_${category.id}" type="text" class="form-control"
                                   value="${category.description}"/></td>
                        <td><a class="btn btn-success" href="javascript:;"
                               onclick="updateCategoryDetails(${category.id});">
                            <i class="fa fa-pencil-square-o"></i> </a></td>
                    </tr>
                </c:forEach>
                <tr id="category_new">
                    <td><input id="categoryNameNew" type="text" class="form-control"
                               value=""/></td>
                    <td><input id="categoryDescNew" type="text" class="form-control"
                               value=""/></td>
                    <td><a class="btn btn-success" href="javascript:;"
                           onclick="createCategory();">
                        <i class="fa fa-plus"></i></a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-6 product-attributes">
        <h3 class="milkroad-h3">Attributes</h3>
        <span class="error" id="attribute_name_error"></span>
        <span class="error" id="attribute_description_error"></span>
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
                    <tr id="attribute_${attribute.id}">
                        <td><input id="attributeName_${attribute.id}" type="text" class="form-control"
                                   value="${attribute.name}"/></td>
                        <td><input id="attributeDesc_${attribute.id}" type="text" class="form-control"
                                   value="${attribute.description}"/></td>
                        <td><a class="btn btn-success" href="javascript:;"
                               onclick="updateAttributeDetails(${attribute.id});">
                            <i class="fa fa-pencil-square-o"></i> </a></td>
                    </tr>
                </c:forEach>
                <tr id="attribute_new">
                    <td><input id="attributeNameNew" type="text" class="form-control"
                               value=""/></td>
                    <td><input id="attributeDescNew" type="text" class="form-control"
                               value=""/></td>
                    <td><a class="btn btn-success" href="javascript:;"
                           onclick="createAttribute();">
                        <i class="fa fa-plus"></i></a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>