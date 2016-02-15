<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--@elvariable id="products" type="java.util.List<com.tsystems.javaschool.milkroad.dto.ProductDTO>"--%>
<table border=3>
    <thead>
    <tr>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Count</th>
        <th>Description</th>
        <th>Seller email</th>
        <th>Param name</th>
        <th>Param value</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${products}" var="product">
        <c:set var="parameters_count">${fn:length(product.parameters)}</c:set>
        <tr>
        <td rowspan=${parameters_count}>${product.name}</td>
        <td rowspan=${parameters_count}>${product.category.name}</td>
        <td rowspan=${parameters_count}>${product.price}</td>
        <td rowspan=${parameters_count}>${product.count}</td>
        <td rowspan=${parameters_count}>${product.description}</td>
        <td rowspan=${parameters_count}>${product.seller.email}</td>
        <c:forEach items="${product.parameters}" var="parameter">
            <td>${parameter.name}</td>
            <td>${parameter.value}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>