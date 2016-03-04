<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Management"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="products" type="java.util.List<javafx.util.Pair<com.tsystems.javaschool.milkroad.dto.ProductDTO, java.lang.Integer>>"--%>
<%--@elvariable id="users" type="java.util.List<com.tsystems.javaschool.milkroad.dto.UserDTO>"--%>

<div class="container">
    <div class="col-md-12">
        <h3 class="milkroad-h3">Top products</h3>
        <div class="table-responsive">
            <table class="table" border=3>
                <thead>
                <tr>
                    <th>Sales count</th>
                    <th>Article</th>
                    <th>Seller email</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Remain</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="product">
                    <tr>
                        <td>${product.value}</td>
                        <td>${product.key.article}</td>
                        <td>${product.key.seller.email}</td>
                        <td>${product.key.name}</td>
                        <td>${product.key.category.name}</td>
                        <td>${product.key.price}</td>
                        <td>${product.key.count}</td>
                        <td>${product.key.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-12">
        <h3 class="milkroad-h3">Top customers</h3>
        <div class="table-responsive">
            <table class="table" border=3>
                <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Birth date</th>
                    <th>E-mail</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.birthday}</td>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>