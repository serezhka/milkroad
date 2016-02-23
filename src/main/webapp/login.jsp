<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Login"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="errors" type="java.util.Set<java.lang.String>"--%>
<%--@elvariable id="input" type="java.util.HashMap<java.lang.String, java.lang.String>"--%>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="account">
        <div class="account-pass">
            <div class="col-md-8 account-top">
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <input type="hidden" name="formName" value="loginForm"/>
                    <div>
                        <span>Email</span>
                        <c:if test="${errors.contains('EMAIL_ERROR')}">
                            <span class="error">Incorrect email</span>
                        </c:if>
                        <c:if test="${errors.contains('USER_NOT_EXISTS')}">
                            <span class="error">User with email not exists</span>
                        </c:if>
                        <input name="email" type="text" value="${input.get('email')}">
                    </div>
                    <div>
                        <span>Password</span>
                        <c:if test="${errors.contains('PASS_ERROR')}">
                            <span class="error">Incorrect pass</span>
                        </c:if>
                        <c:if test="${errors.contains('PASS_INVALID')}">
                            <span class="error">Invalid pass</span>
                        </c:if>
                        <%-- TODO encrypt? --%>
                        <input name="pass" type="password">
                    </div>
                    <input type="submit" value="Login">
                </form>
            </div>
            <div class="col-md-4 left-account ">
                <img class="img-responsive" src="images/discount.jpg" alt="">
                <div class="five">
                    <h2>25% </h2><span>discount</span>
                </div>
                <a href="${pageContext.request.contextPath}/register" class="create">Create an account</a>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>