<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Management"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="orders" type="java.util.List<com.tsystems.javaschool.milkroad.dto.OrderDTO>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container order-list table-responsive">
    <table class="table" border=3>
        <thead>
        <tr>
            <th>Order ID</th>
            <th>Customer email</th>
            <th>Payment method</th>
            <th>Payment status</th>
            <th>Shipping method</th>
            <th>Shipping status</th>
            <th>Product name</th>
            <th>Count</th>
            <th>Price</th>
            <th>Update</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <c:set var="details_count">${fn:length(order.details)+1}</c:set>
            <tr>
            <td rowspan=${details_count}>${order.id}</td>
            <td rowspan=${details_count}>${order.customer.email}</td>
            <%-- TODO Try to avoid string constants --%>
            <td rowspan=${details_count}>
                <select id="paymentMethod_${order.id}" class="selectpicker" data-width="100%">
                    <c:choose>
                        <c:when test="${order.paymentMethod eq 'ONLINE'}">
                            <option value="ONLINE" selected>ONLINE</option>
                            <option value="CASH">CASH</option>
                        </c:when>
                        <c:otherwise>
                            <option value="ONLINE">ONLINE</option>
                            <option value="CASH" selected>CASH</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </td>
            <td rowspan=${details_count}>
                <select id="paymentStatus_${order.id}" class="selectpicker" data-width="100%">
                    <c:choose>
                        <c:when test="${order.paymentStatus eq 'PAID'}">
                            <option value="PAID" selected>PAID</option>
                            <option value="AWAITING">AWAITING</option>
                        </c:when>
                        <c:otherwise>
                            <option value="PAID">PAID</option>
                            <option value="AWAITING" selected>AWAITING</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </td>
            <td rowspan=${details_count}>
                <select id="shippingMethod_${order.id}" class="selectpicker" data-width="100%">
                    <c:choose>
                        <c:when test="${order.shippingMethod eq 'POST'}">
                            <option value="POST" selected>POST</option>
                            <option value="PICKUP">PICKUP</option>
                        </c:when>
                        <c:otherwise>
                            <option value="POST">POST</option>
                            <option value="PICKUP" selected>PICKUP</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </td>
            <td rowspan=${details_count}>
                <select id="shippingStatus_${order.id}" class="selectpicker" data-width="100%">
                    <c:choose>
                        <c:when test="${order.shippingStatus eq 'SHIPPED'}">
                            <option value="SHIPPED" selected>SHIPPED</option>
                            <option value="AWAITING">AWAITING</option>
                        </c:when>
                        <c:otherwise>
                            <option value="SHIPPED">SHIPPED</option>
                            <option value="AWAITING" selected>AWAITING</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </td>
            <c:forEach items="${order.details}" var="detail" varStatus="status">
                <td>${detail.product.name}</td>
                <td>${detail.count}</td>
                <td>${detail.totalPrice}</td>
                <c:if test="${status.first}">
                    <td rowspan=${details_count}>
                        <a class="btn btn-success" href="javascript:;" onclick="updateOrderDetails(${order.id})">
                            <i class="fa fa-check"></i> </a>
                    </td>
                </c:if>
                </tr>
            </c:forEach>
            <td colspan=2><b>Total price</b></td>
            <td><b>${order.totalPrice}</b></td>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="footer.jsp"/>