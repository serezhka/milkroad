<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Login"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="account">
        <%--<h1>Sign in</h1>--%>
        <div class="account-pass">
            <div class="col-md-8 account-top">
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div>
                        <span>Email</span>
                        <input name="email" type="text">
                    </div>
                    <div>
                        <span>Password</span>
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