<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Welcome"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<%--@elvariable id="message" type="java.lang.String>"--%>
<div class="container">
    <h1>${message}</h1>
</div>

<jsp:include page="footer.jsp"/>