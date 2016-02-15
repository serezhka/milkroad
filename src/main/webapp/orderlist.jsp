<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--@elvariable id="orders" type="java.util.List<com.tsystems.javaschool.milkroad.dto.OrderDTO>"--%>
<table border=3>
    <thead>
    <tr>
        <th>Order ID</th>
        <th>Customer email</th>
        <th>Country</th>
        <th>City</th>
        <th>Payment method</th>
        <th>Payment status</th>
        <th>Shipping method</th>
        <th>Shipping status</th>
        <th>Product name</th>
        <th>Count</th>
        <th>Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <c:set var="details_count">${fn:length(order.details)+1}</c:set>
        <tr>
        <td rowspan=${details_count}>${order.id}</td>
        <td rowspan=${details_count}>${order.customer.email}</td>
        <td rowspan=${details_count}>${order.address.country}</td>
        <td rowspan=${details_count}>${order.address.city}</td>
        <td rowspan=${details_count}>${order.paymentMethod}</td>
        <td rowspan=${details_count}>${order.paymentStatus}</td>
        <td rowspan=${details_count}>${order.shippingMethod}</td>
        <td rowspan=${details_count}>${order.shippingStatus}</td>
        <c:forEach items="${order.details}" var="detail">
            <td>${detail.product.name}</td>
            <td>${detail.count}</td>
            <td>${detail.totalPrice}</td>
            </tr>
        </c:forEach>
        <td colspan=2><b>Total price</b></td>
        <td><b>${order.totalPrice}</b></td>
    </c:forEach>
    </tbody>
</table>