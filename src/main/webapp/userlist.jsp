<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--@elvariable id="users" type="java.util.List<com.tsystems.javaschool.milkroad.dto.UserDTO>"--%>
<table border=3>
    <thead>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Birth date</th>
        <th>E-mail</th>
        <th>Type</th>
        <th>Country</th>
        <th>City</th>
        <th>Postcode</th>
        <th>Street</th>
        <th>Building</th>
        <th>Apartment</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <c:set var="address_count">${fn:length(user.addresses)}</c:set>
        <tr>
        <td rowspan=${address_count}>${user.firstName}</td>
        <td rowspan=${address_count}>${user.lastName}</td>
        <td rowspan=${address_count}>${user.birthday}</td>
        <td rowspan=${address_count}>${user.email}</td>
        <td rowspan=${address_count}>${user.userType}</td>
        <c:forEach items="${user.addresses}" var="address">
            <td>${address.country}</td>
            <td>${address.city}</td>
            <td>${address.postcode}</td>
            <td>${address.street}</td>
            <td>${address.building}</td>
            <td>${address.apartment}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>