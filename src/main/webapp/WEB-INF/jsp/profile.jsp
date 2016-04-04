<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Profile"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="user" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>
<%--@elvariable id="orders" type="java.util.List<com.tsystems.javaschool.milkroad.dto.OrderDTO>"--%>
<%--@elvariable id="errors" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="input" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="user-info">
        <form action="${pageContext.request.contextPath}/profile/edit" method="post">
            <div class="col-md-6 profile-top-grid">
                <h3>Personal infomation</h3>
                <div>
                    <span>First Name</span>
                    <c:if test="${not empty errors && errors.containsKey('firstName')}">
                        <span class="error">${errors.get('firstName')}</span>
                    </c:if>
                    <input name="firstName" type="text" value="${user.firstName}">
                </div>
                <div>
                    <span>Last Name</span>
                    <c:if test="${not empty errors && errors.containsKey('lastName')}">
                        <span class="error">${errors.get('lastName')}</span>
                    </c:if>
                    <input name="lastName" type="text" value="${user.lastName}">
                </div>
                <div>
                    <span>Date of birth (YYYY-MM-DD)</span>
                    <c:if test="${not empty errors && errors.containsKey('birthday')}">
                        <span class="error">${errors.get('birthday')}</span>
                    </c:if>
                    <input name="birthday" type="text" value="${user.birthday}">
                </div>
            </div>
            <div class="col-md-6 profile-bottom-grid">
                <h3>Login information</h3>
                <div>
                    <span>Email Address</span>
                    <input type="text" value="${user.email}" readonly>
                </div>
                <div>
                    <span>New Password (Leave blank to keep old pass)</span>
                    <c:if test="${not empty errors && errors.containsKey('pass')}">
                        <span class="error">${errors.get('pass')}</span>
                    </c:if>
                    <input name="pass" type="password">
                </div>
                <div>
                    <span>Confirm Password</span>
                    <input type="password">
                </div>
                <input type="submit" value="Update">
            </div>
            <div class="clearfix"></div>
        </form>
        <%-- TODO Compare user type without string constants --%>
        <c:if test="${not (user.userType eq 'ADMIN') and not (user.userType eq 'SELLER')}">
            <div class="col-md-12 user-addresses">
                <h3 class="milkroad-h3">Addresses</h3>
                <c:if test="${not empty errors}">
                    <c:forEach items="${errors.values()}" var="error">
                        <span class="error">${error}</span>
                    </c:forEach>
                </c:if>
                <div class="table-responsive">
                    <table class="table" border=3>
                        <thead>
                        <tr>
                            <th>Country</th>
                            <th>City</th>
                            <th>Postcode</th>
                            <th>Street</th>
                            <th>Building</th>
                            <th>Apartment</th>
                            <th>Update</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${user.addresses}" var="address">
                            <tr>
                                <td><input id="country_${address.id}" type="text" class="form-control"
                                           value="${address.country}"/></td>
                                <td><input id="city_${address.id}" type="text" class="form-control"
                                           value="${address.city}"/></td>
                                <td><input id="postcode_${address.id}" type="text" class="form-control"
                                           value="${address.postcode}"/></td>
                                <td><input id="street_${address.id}" type="text" class="form-control"
                                           value="${address.street}"/></td>
                                <td><input id="building_${address.id}" type="text" class="form-control"
                                           value="${address.building}"/></td>
                                <td><input id="apartment_${address.id}" type="text" class="form-control"
                                           value="${address.apartment}"/></td>
                                <td><a class="btn btn-success" href="javascript:;"
                                       onclick="updateAddress(${address.id});">
                                    <i class="fa fa-pencil-square-o"></i> </a></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td><input id="country_new" type="text" class="form-control"
                                       value="${input.get('country')}"/></td>
                            <td><input id="city_new" type="text" class="form-control"
                                       value="${input.get('city')}"/></td>
                            <td><input id="postcode_new" type="text" class="form-control"
                                       value="${input.get('postcode')}"/></td>
                            <td><input id="street_new" type="text" class="form-control"
                                       value="${input.get('street')}"/></td>
                            <td><input id="building_new" type="text" class="form-control"
                                       value="${input.get('building')}"/></td>
                            <td><input id="apartment_new" type="text" class="form-control"
                                       value="${input.get('apartment')}"/></td>
                            <td><a class="btn btn-success" href="javascript:;"
                                   onclick="createAddress();">
                                <i class="fa fa-plus"></i></a></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-12 profile-orders">
                <h3>Orders</h3>
                <div class="table-responsive">
                    <table class="table" border=3>
                        <thead>
                        <tr>
                            <th>Order ID</th>
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
                            <c:set var="details_count">${fn:length(order.details) + 1}</c:set>
                            <tr>
                            <td rowspan=${details_count}>${order.id}</td>
                            <td rowspan=${details_count}>${order.paymentMethod}</td>
                            <td rowspan=${details_count}>${order.paymentStatus}</td>
                            <td rowspan=${details_count}>${order.shippingMethod}</td>
                            <td rowspan=${details_count}>${order.shippingStatus}</td>
                            <c:forEach items="${order.details}" var="detail" varStatus="status">
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
                </div>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>
    $(".nav li#nav_profile").addClass("active");
</script>
