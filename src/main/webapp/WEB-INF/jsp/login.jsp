<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Login"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="account">
        <div class="account-pass">
            <div class="col-md-8 account-top">
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                        <span class="error">${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
                    </c:if>
                    <div>
                        <span>Email</span>
                        <input name="email" type="text" value="">
                    </div>
                    <div>
                        <span>Password</span>
                        <input name="pass" type="password">
                    </div>
                    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                    <input type="submit" value="Login">
                </form>
            </div>
            <div class="col-md-4 left-account ">
                <img class="img-responsive" src="<c:url value="/images/discount.jpg"/>" alt="">
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