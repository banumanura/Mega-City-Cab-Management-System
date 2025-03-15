<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill List - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-list.css">
</head>
<body>
    <div class="container">
        <h1>Bill List</h1>
        
        
        
        <table class="table">
            <thead>
                <tr>
                    <th>Bill ID</th>
                    <th>Bill Number</th>
                    <th>Booking ID</th>
                    <th>Total Amount</th>
                    <th>Payment Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="bill" items="${bills}">
                    <tr>
                        <td>${bill.billId}</td>
                        <td>${bill.billNumber}</td>
                        <td>${bill.bookingId}</td>
                        <td>${bill.totalAmount}</td>
                        <td>${bill.paymentStatus}</td>
                        <td>
                            
                            <a href="${pageContext.request.contextPath}/bill/edit?id=${bill.billId}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
