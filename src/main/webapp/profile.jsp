<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Welcome"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="AUTHED_USER" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>
<%--@elvariable id="orders" type="java.util.List<com.tsystems.javaschool.milkroad.dto.OrderDTO>"--%>
<%--@elvariable id="errors" type="java.util.Set<java.lang.String>"--%>
<%--@elvariable id="input" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>

<c:set var="user" value="${AUTHED_USER}"/>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="user-info">
        <form action="${pageContext.request.contextPath}/profile" method="post">
            <input type="hidden" name="formName" value="profileUpdateForm"/>
            <div class="col-md-6 profile-top-grid">
                <h3>Personal infomation</h3>
                <div>
                    <span>First Name</span>
                    <c:if test="${errors.contains('FIRST_NAME_ERROR')}">
                        <span class="error">Incorrect first name</span>
                    </c:if>
                    <input name="firstname" type="text" value="${user.firstName}">
                </div>
                <div>
                    <span>Last Name</span>
                    <c:if test="${errors.contains('LAST_NAME_ERROR')}">
                        <span class="error">Incorrect last name</span>
                    </c:if>
                    <input name="lastname" type="text" value="${user.lastName}">
                </div>
                <div>
                    <span>Date of birth (YYYY-MM-DD)</span>
                    <c:if test="${errors.contains('DATE_ERROR')}">
                        <span class="error">Incorrect date</span>
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
                    <c:if test="${errors.contains('PASS_ERROR')}">
                        <span class="error">Incorrect pass</span>
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
            <div class="col-md-6 profile-addresses">
                <h3>Addresses</h3>
                <table class="table">
                    <tbody>
                    <c:forEach items="${user.addresses}" var="address">
                        <tr>
                            <td>${address}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-md-6 profile-add-address">
                <h3>Add address</h3>
                <form action="${pageContext.request.contextPath}/profile" method="post">
                    <input type="hidden" name="formName" value="addAddressForm"/>
                    <div>
                        <span>Country</span>
                        <c:if test="${errors.contains('COUNTRY_ERROR')}">
                            <span class="error">Incorrect country name</span>
                        </c:if>
                        <input name="country" type="text" value="${input.get('country')}">
                    </div>
                    <div>
                        <span>City</span>
                        <c:if test="${errors.contains('CITY_ERROR')}">
                            <span class="error">Incorrect city name</span>
                        </c:if>
                        <input name="city" type="text" value="${input.get('city')}">
                    </div>
                    <div>
                        <span>Postcode</span>
                        <c:if test="${errors.contains('POSTCODE_ERROR')}">
                            <span class="error">Incorrect poscode</span>
                        </c:if>
                        <input name="postcode" type="text" value="${input.get('postcode')}">
                    </div>
                    <div>
                        <span>Street</span>
                        <c:if test="${errors.contains('STREET_ERROR')}">
                            <span class="error">Incorrect street name</span>
                        </c:if>
                        <input name="street" type="text" value="${input.get('street')}">
                    </div>
                    <div>
                        <span>Building</span>
                        <c:if test="${errors.contains('BUILDING_ERROR')}">
                            <span class="error">Incorrect building value</span>
                        </c:if>
                        <input name="building" type="text" value="${input.get('building')}">
                    </div>
                    <div>
                        <span>Apartment</span>
                        <c:if test="${errors.contains('APARTMENT_ERROR')}">
                            <span class="error">Incorrect apartment value</span>
                        </c:if>
                        <input name="apartment" type="text" value="${input.get('apartment')}">
                    </div>
                    <input type="submit" value="Add">
                </form>
            </div>
            <div class="col-md-6 profile-orders">
                <h3>Orders</h3>
                <table class="table">
                    <tbody>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td>${order}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp"/>