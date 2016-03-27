<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Welcome"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%-- TODO Add _jspx_page_context.findAttribute("errors") to watches to debug :) --%>
<%--@elvariable id="errors" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="input" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="register">
        <form action="${pageContext.request.contextPath}/register" method="post">
            <input type="hidden" name="formName" value="registerForm"/>
            <div class="col-md-6 register-top-grid">
                <h3>Personal infomation</h3>
                <div>
                    <span>First Name</span>
                    <c:if test="${not empty errors && errors.containsKey('firstName')}">
                        <span class="error">${errors.get('firstName')}</span>
                    </c:if>
                    <input name="firstName" type="text" value="${input.firstName}">
                </div>
                <div>
                    <span>Last Name</span>
                    <c:if test="${not empty errors && errors.containsKey('lastName')}">
                        <span class="error">${errors.get('lastName')}</span>
                    </c:if>
                    <input name="lastName" type="text" value="${input.lastName}">
                </div>
                <div>
                    <span>Date of birth (YYYY-MM-DD)</span>
                    <c:if test="${not empty errors && errors.containsKey('birthday')}">
                        <span class="error">${errors.get('birthday')}</span>
                    </c:if>
                    <input name="birthday" type="text" value="${input.birthday}">
                </div>
            </div>
            <div class="col-md-6 register-bottom-grid">
                <h3>Login information</h3>
                <div>
                    <span>Email Address</span>
                    <c:if test="${not empty errors && errors.containsKey('email')}">
                        <span class="error">${errors.get('email')}</span>
                    </c:if>
                    <input name="email" type="text" value="${input.email}">
                </div>
                <div>
                    <span>Password</span>
                    <c:if test="${not empty errors && errors.containsKey('pass')}">
                        <span class="error">${errors.get('pass')}</span>
                    </c:if>
                    <input name="pass" type="password">
                </div>
                <div>
                    <span>Confirm Password</span>
                    <input type="password">
                </div>
                <input type="submit" value="Register">
            </div>
            <div class="clearfix"></div>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>
