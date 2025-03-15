<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookings - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-list.css">
</head>
<body>
    <div class="container">
        <h1>Bookings</h1>
        <a href="${pageContext.request.contextPath}/booking/add" class="btn">Add New Booking</a>
        <table>
            <thead>
                <tr>
                    <th>Booking ID</th>
                    <th>Customer</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${bookings}" var="booking">
                    <tr>
                        <td>${booking.bookingId}</td>
                        <td>${booking.customerName}</td>
                        <td>${booking.bookingDate}</td>
                        <td>${booking.status}</td>
                        <td>
                            
                            <a href="${pageContext.request.contextPath}/booking/edit?id=${booking.bookingId}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>