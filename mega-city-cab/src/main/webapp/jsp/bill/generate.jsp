<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Bill - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
</head>
<body>
    <div class="container">
        <h1>Generate Bill</h1>

       

        <form action="${pageContext.request.contextPath}/bill/generate" method="post">
            <input type="hidden" name="bookingId" value="${booking.bookingId}">

            <div class="form-group">
                <label>Customer Name:</label>
                <input type="text" value="${customer.firstName} ${customer.lastName}" readonly>
            </div>

            <div class="form-group">
                <label>Waiting Hours:</label>
                <input type="number" name="waitingHours" step="0.1" required>
            </div>

            <div class="form-group">
                <label>Night Trip:</label>
                <input type="checkbox" name="isNightTrip">
            </div>

            <div class="form-group">
                <label>Discount Amount:</label>
                <input type="number" name="discountAmount" step="0.01">
            </div>

            <div class="form-group">
                <label>Discount Reason:</label>
                <input type="text" name="discountReason">
            </div>

            <div class="form-group">
                <label>Payment Method:</label>
                <select name="paymentMethod" required>
                    <option value="CASH">Cash</option>
                    <option value="CREDIT_CARD">Credit Card</option>
                    <option value="DEBIT_CARD">Debit Card</option>
                    <option value="MOBILE_PAYMENT">Mobile Payment</option>
                    <option value="CORPORATE_ACCOUNT">Corporate Account</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn">Generate Bill</button>
                <a href="${pageContext.request.contextPath}/bill" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
