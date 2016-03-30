<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Management"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="products" type="java.util.Map<com.tsystems.javaschool.milkroad.dto.ProductDTO, java.lang.Integer>"--%>
<%--@elvariable id="users" type="java.util.Map<com.tsystems.javaschool.milkroad.dto.UserDTO, java.math.BigDecimal>"--%>
<%--@elvariable id="totalCash" type="java.math.BigDecimal"--%>
<%--@elvariable id="totalCashThisMonth" type="java.math.BigDecimal"--%>
<%--@elvariable id="totalCashLast7Days" type="java.math.BigDecimal"--%>

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
                    <th>Total cash</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Birth date</th>
                    <th>E-mail</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.value}</td>
                        <td>${user.key.firstName}</td>
                        <td>${user.key.lastName}</td>
                        <td>${user.key.birthday}</td>
                        <td>${user.key.email}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-4">
        <h3 class="milkroad-h3">Total cash</h3>
        <h3>${totalCash}</h3>
    </div>
    <div class="col-md-4">
        <h3 class="milkroad-h3">Total cash this month</h3>
        <h3>${totalCashThisMonth}</h3>
    </div>
    <div class="col-md-4">
        <h3 class="milkroad-h3">Total cash last 7 days</h3>
        <h3>${totalCashLast7Days}</h3>
    </div>
</div>

<jsp:include page="footer.jsp"/>