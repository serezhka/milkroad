<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Management"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="AUTHED_USER" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>
<%--@elvariable id="product" type="com.tsystems.javaschool.milkroad.dto.ProductDTO"--%>
<%--@elvariable id="categories" type="java.util.List<com.tsystems.javaschool.milkroad.dto.CategoryDTO>"--%>
<%--@elvariable id="attributes" type="java.util.List<com.tsystems.javaschool.milkroad.dto.AttributeDTO>"--%>

<c:set var="user" value="${AUTHED_USER}"/>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <c:url var="action" value="/management/createProduct"/>
    <c:if test="${not empty product.article}">
        <c:url var="action" value="/management/updateProduct"/>
    </c:if>
    <form id="product-data-form" action="${action}" method="post"
          enctype="multipart/form-data">
        <div class="col-md-6 product-edit-info-div">
            <input name="seller.id" type="hidden" value="${user.id}">
            <input name="article" type="hidden" value="${product.article}">
            <h3>Product info</h3>
            <div>
                <span>Name</span>
                <span class="error" id="name_error"></span>
                <input name="name" type="text" value="${product.name}">
            </div>
            <div>
                <span>Description</span>
                <span class="error" id="description_error"></span>
                <input name="description" type="text" value="${product.description}">
            </div>
            <div class="dropup">
                <span>Category</span>
                <span class="error" id="category_error"></span>
                <select name="category.id" class="selectpicker no-radius" data-width="100%">
                    <option data-hidden="true"></option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}" <c:if test="${category.name eq product.category.name}">
                            selected</c:if>>${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <span>Price</span>
                <span class="error" id="price_error"></span>
                <input name="price" type="text" value="${product.price}">
            </div>
            <div>
                <span>Remain count</span>
                <span class="error" id="count_error"></span>
                <input name="count" type="text" value="${product.count}">
            </div>
            <input name="article" type="hidden" value="${product.article}"/>
            <input name="seller.id" type="hidden" value="${product.seller.id}"/>
            <c:set var="btnText" value="Add product"/>
            <c:if test="${not empty product.article}">
                <c:set var="btnText" value="Edit product"/>
            </c:if>
            <input id="submit" type="submit" value="${btnText}">
        </div>
        <div class="col-md-6 product-edit-image-div">
            <h3>Product image</h3>
            <span class="error" id="image_error"></span>
            <c:url value="/images/product/default.png" var="defaultImageURL"/>
            <c:url value="/images/product/product_${product.article}.png" var="productImageURL"/>
            <img class="img-responsive" src="${productImageURL}"
                 onerror="this.onerror=null;this.src='${defaultImageURL}';" alt="">
        <span class="file-input btn btn-block btn-primary btn-file">
                Browse&hellip;<input type="file" id="image" name="image" accept="image/jpeg"></span>
        </div>
        <div class="col-md-6 product-edit-details-div">
            <h3>Product parameters</h3>
            <c:forEach items="${product.parameters}" var="parameter" varStatus="status">
                <div class="dropup product-add-detail-div">
                    <select name="parameters[${status.index}].attribute.id" id="parameter_${status.index}"
                            class="selectpicker no-radius"
                            data-width="100%">
                        <option data-hidden="true"></option>
                        <c:forEach items="${attributes}" var="attribute">
                            <option value="${attribute.id}" <c:if
                                    test="${attribute.id eq parameter.attribute.id}">
                                selected</c:if>>${attribute.name}</option>
                        </c:forEach>
                    </select>
                    <input name="parameters[${status.index}].value" type="text" value="${parameter.value}">
                    <a class="btn btn-danger" href="javascript:;"><i class="fa fa-close"></i> </a>
                </div>
            </c:forEach>
            <c:set value="0" var="paramsCount"/>
            <c:if test="${not empty product}">
                <c:set value="${product.parameters.size()}" var="paramsCount"/>
            </c:if>
            <div class="dropup product-add-detail-div">
                <select id="parameter_${paramsCount}" class="selectpicker no-radius"
                        data-width="100%">
                    <option data-hidden="true"></option>
                    <c:forEach items="${attributes}" var="attribute">
                        <option value="${attribute.id}">${attribute.name}</option>
                    </c:forEach>
                </select>
                <input type="text" value="">
                <a class="btn btn-danger" href="javascript:;"><i class="fa fa-close"></i> </a>
            </div>
        </div>
    </form>
</div>

<jsp:include page="footer.jsp"/>