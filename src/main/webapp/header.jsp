<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Milkroad e-shop | ${param.pageName}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <%-- Stylesheets --%>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="css/milkroad-style.css" rel="stylesheet" type="text/css" media="all"/>

    <%-- Fonts --%>
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>

    <%-- Javascript --%>
    <script src="js/jquery-2.2.0.js"></script>
    <script src="js/bootstrap.js"></script>

    <%-- Hide url bar --%>
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(function () {
            window.scrollTo(0, 1);
        }, 0);
    }, false);
    </script>
</head>
<body>

<%-- Header --%>
<div class="header">
    <div class="header-top">
        <div class="container">
            <div class="search">
                <form>
                    <input type="text" value="Search " onfocus="this.value = '';"
                           onblur="if (this.value == '') {this.value = 'Search';}">
                    <input type="submit" value="Go">
                </form>
            </div>
            <div class="header-left">
                <ul>
                    <li><a href="/login">Login</a></li>
                    <li><a href="/register">Register</a></li>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
