<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Welcome"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%-- TODO Add _jspx_page_context.findAttribute("errors") to watches to debug :) --%>
<%--@elvariable id="errors" type="java.util.Set<java.lang.String>"--%>
<%--@elvariable id="input" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="register">
        <form action="${pageContext.request.contextPath}/register" method="post">
            <input type="hidden" name="formName" value="registerForm"/>
            <div class="col-md-6 register-top-grid">
                <h3>Personal infomation</h3>
                <div>
                    <span>First Name</span>
                    <c:if test="${errors.contains('FIRST_NAME_ERROR')}">
                        <span class="error">Incorrect first name</span>
                    </c:if>
                    <input name="firstname" type="text" value="${input.get('firstname')}">
                </div>
                <div>
                    <span>Last Name</span>
                    <c:if test="${errors.contains('LAST_NAME_ERROR')}">
                        <span class="error">Incorrect last name</span>
                    </c:if>
                    <input name="lastname" type="text" value="${input.get('lastname')}">
                </div>
                <div>
                    <span>Date of birth (YYYY-MM-DD)</span>
                    <c:if test="${errors.contains('DATE_ERROR')}">
                        <span class="error">Incorrect date</span>
                    </c:if>
                    <input name="birthday" type="text" value="${input.get('birthday')}">
                </div>
            </div>
            <div class="col-md-6 register-bottom-grid">
                <h3>Login information</h3>
                <div>
                    <span>Email Address</span>
                    <c:if test="${errors.contains('USER_EMAIL_ALREADY_EXISTS')}">
                        <span class="error">Email is used</span>
                    </c:if>
                    <c:if test="${errors.contains('EMAIL_ERROR')}">
                        <span class="error">Incorrect email</span>
                    </c:if>
                    <input name="email" type="text" value="${input.get('email')}">
                </div>
                <div>
                    <span>Password</span>
                    <c:if test="${errors.contains('PASS_ERROR')}">
                        <span class="error">Incorrect pass</span>
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
