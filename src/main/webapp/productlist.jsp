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
<%--@elvariable id="products" type="java.util.List<com.tsystems.javaschool.milkroad.dto.ProductDTO>"--%>
<%--@elvariable id="input" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="errors" type="java.util.Set<java.lang.String>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="col-md-12">
        <h3 class="milkroad-h3">Products</h3>
        <c:if test="${errors.contains('PRODUCT_NAME_ERROR')}">
            <span class="error">Incorrect product name</span>
        </c:if>
        <c:if test="${errors.contains('PRODUCT_PRICE_ERROR')}">
            <span class="error">Incorrect product price</span>
        </c:if>
        <c:if test="${errors.contains('PRODUCT_COUNT_ERROR')}">
            <span class="error">Incorrect product count</span>
        </c:if>
        <c:if test="${errors.contains('PRODUCT_CATEGORY_ERROR')}">
            <span class="error">Choose product category</span>
        </c:if>
        <div class="table-responsive">
            <table class="table" border=3>
                <thead>
                <tr>
                    <th>Article</th>
                    <th>Seller email</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Count</th>
                    <th>Description</th>
                    <th>Param name</th>
                    <th>Param value</th>
                    <th>Update</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="product">
                    <c:set var="parameters_count">${fn:length(product.parameters) + 1}</c:set>
                    <tr>
                    <td rowspan=${parameters_count}>${product.article}</td>
                    <td rowspan=${parameters_count}>${product.seller.email}</td>
                    <td rowspan=${parameters_count}>
                        <input id="productName_${product.article}" type="text" class="form-control"
                               value="${product.name}"/></td>
                    <td rowspan=${parameters_count}>
                        <select id="productCategory_${product.article}" class="selectpicker" data-width="100%">
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.id}"
                                        <c:if test="${category.name eq product.category.name}">
                                            selected</c:if>>${category.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td rowspan=${parameters_count}>
                        <input id="productPrice_${product.article}" type="text" class="form-control"
                               value="${product.price}"/></td>
                    <td rowspan=${parameters_count}>
                        <input id="productCount_${product.article}" type="text" class="form-control"
                               value="${product.count}"/></td>
                    <td rowspan=${parameters_count}>
                        <input id="productDesc_${product.article}" type="text" class="form-control"
                               value="${product.description}"/></td>
                    <c:if test="${empty product.parameters}">
                        <td><select id="productAttrNewID_${product.article}" class="selectpicker"
                                    data-width="100%">
                            <option data-hidden="true"></option>
                            <c:forEach items="${attributes}" var="attribute">
                                <option value="${attribute.id}">${attribute.name}</option>
                            </c:forEach>
                        </select></td>
                        <td><input id="productAttrNewValue_${product.article}" type="text"
                                   class="form-control" value=""/></td>
                        <td><a class="btn btn-success" href="javascript:;" onclick="updateProduct(${product.article})">
                            <i class="fa fa-pencil-square-o"></i></a></td>
                        </tr>
                    </c:if>
                    <c:forEach items="${product.parameters}" var="parameter" varStatus="status">
                        <td><select id="productAttrID_${product.article}_${status.index}" class="selectpicker"
                                    data-width="100%" disabled>
                            <c:forEach items="${attributes}" var="attribute">
                                <option value="${attribute.id}"
                                        <c:if test="${attribute.name eq parameter.name}">
                                            selected</c:if>>${attribute.name}</option>
                            </c:forEach>
                        </select></td>
                        <td><input id="productAttrValue_${product.article}_${status.index}" type="text"
                                   class="form-control"
                                   value="${parameter.value}"/></td>
                        <c:if test="${status.first}">
                            <td rowspan=${parameters_count}>
                                <a class="btn btn-success" href="javascript:;"
                                   onclick="updateProduct(${product.article}, ${parameters_count - 1})">
                                    <i class="fa fa-pencil-square-o"></i></a>
                            </td>
                        </c:if>
                        </tr>
                        <c:if test="${status.last}">
                            <tr>
                                <td><select id="productAttrNewID_${product.article}" class="selectpicker"
                                            data-width="100%">
                                    <option data-hidden="true"></option>
                                    <c:forEach items="${attributes}" var="attribute">
                                        <option value="${attribute.id}">${attribute.name}</option>
                                    </c:forEach>
                                </select></td>
                                <td><input id="productAttrNewValue_${product.article}" type="text"
                                           class="form-control" value=""/></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td><input id="productNewName" type="text" class="form-control"
                               value="${input.get('productName')}"/></td>
                    <td><select id="productNewCategory" class="selectpicker" data-width="100%">
                        <option data-hidden="true"></option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}"
                                    <c:if test="${input.get('productCategoryID') eq category.id}">selected</c:if>>${category.name}</option>
                        </c:forEach>
                    </select></td>
                    <td><input id="productNewPrice" type="text" class="form-control"
                               value="${input.get('productPrice')}"/></td>
                    <td><input id="productNewCount" type="text" class="form-control"
                               value="${input.get('productCount')}"/></td>
                    <td><input id="productNewDesc" type="text" class="form-control"
                               value="${input.get('productDesc')}"/></td>
                    <td><select id="productNewAttrID" class="selectpicker"
                                data-width="100%">
                        <option data-hidden="true"></option>
                        <c:forEach items="${attributes}" var="attribute">
                            <option value="${attribute.id}">${attribute.name}</option>
                        </c:forEach>
                    </select></td>
                    <td><input id="productNewAttrValue" type="text"
                               class="form-control" value=""/></td>
                    <td><a class="btn btn-success" href="javascript:;" onclick="createProduct()">
                        <i class="fa fa-plus"></i></a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>