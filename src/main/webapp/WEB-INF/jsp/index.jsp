<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Welcome"/>
</jsp:include>
<jsp:include page="header-top.jsp"/>
<jsp:include page="header-nav.jsp"/>

<div class="banner">
    <div class="container">
        <div class="banner-text">
            <h3>MilkRoad shop</h3>
            <p>Online payment. Free worldwide shipping. Quality guarantee</p>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>
    $(".nav li#nav_home").addClass("active");
</script>
