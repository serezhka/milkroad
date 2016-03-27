<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Management"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="products" type="java.util.List<com.tsystems.javaschool.milkroad.dto.ProductDTO>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="col-md-12">
        <h3 class="milkroad-h3">Products</h3>
        <div class="table-responsive">
            <table class="table" border=3>
                <thead>
                <tr>
                    <th>Article</th>
                    <%--<th>Seller email</th>--%>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Count</th>
                    <th>Edit</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="product">
                    <tr>
                        <td>${product.article}</td>
                        <%--<td>${product.seller.email}</td>--%>
                        <td>${product.name}</td>
                        <td>${product.category.name}</td>
                        <td>${product.price}</td>
                        <td>${product.count}</td>
                        <td>
                            <c:url value="/management/editProduct" var="productURL">
                                <c:param name="article" value="${product.article}"/>
                            </c:url>
                            <a class="btn btn-success" href="${productURL}">
                                <i class="fa fa-pencil-square-o"></i> </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>