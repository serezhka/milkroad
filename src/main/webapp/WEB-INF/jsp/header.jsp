<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Milkroad e-shop | ${param.pageName}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <%-- Favicon --%>
    <link rel="icon" type="image/gif" href="<c:url value="/favicon.ico"/>">

    <%-- Stylesheets --%>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" media="all"/>
    <%--<link href="<c:url value="/css/bootstrap-theme.min.css"/>" rel="stylesheet" type="text/css" media="all"/>--%>
    <link href="<c:url value="/css/jquery-ui.min.css"/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<c:url value="/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<c:url value="/css/bootstrap-select.min.css"/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<c:url value="/css/milkroad-theme.css"/>" rel="stylesheet" type="text/css" media="all"/>

    <%-- Fonts --%>
    <link href="http://fonts.googleapis.com/css?family=Cookie" rel="stylesheet" type="text/css">
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>

    <%-- Javascript --%>
    <script src="<c:url value="/js/jquery-2.2.0.min.js"/>"></script>
    <script src="<c:url value="/js/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-dropdown.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-select.min.js"/>"></script>
    <script src="<c:url value="/js/milkroad.js"/>"></script>

    <%-- Hide url bar --%>
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(function () {
            window.scrollTo(0, 1);
        }, 0);
    }, false);
    </script>
</head>
<body>
