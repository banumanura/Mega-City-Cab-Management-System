<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicles - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-list.css">
</head>
<body>
    <div class="container">
        <h1>Vehicles</h1>
        
        
        
        <a href="${pageContext.request.contextPath}/vehicle/add" class="btn">Add New Vehicle</a>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Registration</th>
                    <th>Make/Model</th>
                    <th>Year</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${vehicles}" var="vehicle">
                    <tr>
                        <td>${vehicle.vehicleId}</td>
                        <td>${vehicle.registrationNumber}</td>
                        <td>${vehicle.make} ${vehicle.model}</td>
                        <td>${vehicle.year}</td>
                        <td>${vehicle.vehicleType}</td>
                        <td>${vehicle.status}</td>
                        <td>
                            
                            <a href="${pageContext.request.contextPath}/vehicle/edit?id=${vehicle.vehicleId}">Edit</a>
                            
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <script>
        function confirmDelete(vehicleId) {
            if (confirm("Are you sure you want to delete this vehicle?")) {
                window.location.href = "${pageContext.request.contextPath}/vehicle/delete?id=" + vehicleId;
            }
        }
    </script>
</body>
</html>