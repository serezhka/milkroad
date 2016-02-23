<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Welcome"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="AUTHED_USER" type="com.tsystems.javaschool.milkroad.dto.UserDTO"--%>
<%--@elvariable id="errors" type="java.util.Set<java.lang.String>"--%>

<c:set var="user" value="${AUTHED_USER}"/>

<%--suppress HtmlFormInputWithoutLabel --%>
<div class="container">
    <div class="user-info">
        <form action="${pageContext.request.contextPath}/profile" method="post">
            <input type="hidden" name="formName" value="profileUpdateForm"/>
            <div class="col-md-6 profile-top-grid">
                <h3>Personal infomation</h3>
                <div>
                    <span>ID</span>
                    <input type="text" value="${user.id}" readonly>
                </div>
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
    </div>
</div>

<jsp:include page="footer.jsp"/>