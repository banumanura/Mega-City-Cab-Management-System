<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Drivers - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-list.css">
</head>
<body>
    <div class="container">
        <h1>Drivers</h1>
        <a href="${pageContext.request.contextPath}/driver/add" class="btn">Add New Driver</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>License Number</th>
                    <th>Telephone</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${drivers}" var="driver">
                    <tr>
                        <td>${driver.driverId}</td>
                        <td>${driver.firstName} ${driver.lastName}</td>
                        <td>${driver.licenseNumber}</td>
                        <td>${driver.telephone}</td>
                        <td>
                            <span class="status-badge status-${driver.status.toLowerCase()}">${driver.status.replace('_', ' ')}</span>
                        </td>
                        <td>
                            
                            <a href="${pageContext.request.contextPath}/driver/edit?id=${driver.driverId}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
