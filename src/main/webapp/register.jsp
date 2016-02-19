<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Welcome"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class=" container">
    <div class=" register">
        <%--<h1>Register</h1>--%>
        <form>
            <div class="col-md-6 register-top-grid">
                <h3>Personal infomation</h3>
                <div>
                    <span>First Name</span>
                    <input type="text">
                </div>
                <div>
                    <span>Last Name</span>
                    <input type="text">
                </div>
                <div>
                    <span>Date of birth</span>
                    <input type="text">
                </div>
            </div>
            <div class="col-md-6 register-bottom-grid">
                <h3>Login information</h3>
                <div>
                    <span>Email Address</span>
                    <input type="text">
                </div>
                <div>
                    <span>Password</span>
                    <input type="password">
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
