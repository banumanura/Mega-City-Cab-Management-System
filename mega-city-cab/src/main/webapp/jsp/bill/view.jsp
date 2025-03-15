<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Bill - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bill-view.css">
</head>
<body>
    <div class="container">
        <h1>Bill Details</h1>

        <p><strong>Bill Number:</strong> ${bill.billNumber}</p>
        <p><strong>Customer:</strong> ${customer.firstName} ${customer.lastName}</p>
        <p><strong>Total Amount:</strong> ${bill.totalAmount}</p>
        <p><strong>Payment Method:</strong> ${bill.paymentMethod}</p>
        <p><strong>Payment Status:</strong> ${bill.paymentStatus}</p>

        <a href="${pageContext.request.contextPath}/bill" class="btn">Back to Bills</a>
    </div>
</body>
</html>
