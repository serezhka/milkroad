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
<%--@elvariable id="product" type="com.tsystems.javaschool.milkroad.dto.ProductDTO"--%>
<%--@elvariable id="input" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="errors" type="java.util.Set<java.lang.String>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <form id="product-data-form" action="${pageContext.request.contextPath}/management" method="post"
          enctype="multipart/form-data">
        <input type="hidden" name="action" value="addOrEditProduct"/>
        <div class="col-md-6 product-edit-info-div">
            <h3>Product info</h3>
            <div>
                <span>Name</span>
                <input id="name" type="text" value="">
            </div>
            <div>
                <span>Description</span>
                <input id="description" type="text" value="">
            </div>
            <div class="dropup">
                <span>Category</span>
                <select id="category" class="selectpicker no-radius" data-width="100%">
                    <option data-hidden="true"></option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <span>Price</span>
                <input id="price" type="text" value="">
            </div>
            <div>
                <span>Remain count</span>
                <input id="remain" type="text" value="">
            </div>
            <input id="article" type="hidden" value="${product.article}"/>
            <input id="submit" type="submit" value="Add product">
        </div>
        <div class="col-md-6 product-edit-image-div">
            <h3>Product image</h3>
            <img class="img-responsive" src="../../images/product-item-image.png" alt="">
        <span class="file-input btn btn-block btn-primary btn-file">
                Browse&hellip;<input type="file" id="image" accept="image/jpeg"></span>
        </div>
        <div class="col-md-6 product-edit-details-div">
            <h3>Product parameters</h3>
            <div class="dropup product-add-detail-div">
                <select id="parameter_0" class="selectpicker no-radius" data-width="100%">
                    <option data-hidden="true"></option>
                    <c:forEach items="${attributes}" var="attribute">
                        <option value="${attribute.id}">${attribute.name}</option>
                    </c:forEach>
                    <option value="1">Test</option>
                    <option value="2">Test</option>
                    <option value="3">Test</option>
                </select>
                <input name="value" type="text" value="">
                <a class="btn btn-danger" href="javascript:;"><i class="fa fa-close"></i> </a>
            </div>
        </div>
    </form>
</div>

<jsp:include page="footer.jsp"/>