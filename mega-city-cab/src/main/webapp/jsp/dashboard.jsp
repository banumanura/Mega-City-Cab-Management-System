<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Welcome to MegaCityCab Dashboard</h1>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/booking">Bookings</a></li>
                <li><a href="${pageContext.request.contextPath}/customer">Customers</a></li>
                <li><a href="${pageContext.request.contextPath}/vehicle">Vehicles</a></li>
                <li><a href="${pageContext.request.contextPath}/driver">Drivers</a></li>
                <li><a href="${pageContext.request.contextPath}/bill">Bills</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </nav>
    </div>
</body>
</html>