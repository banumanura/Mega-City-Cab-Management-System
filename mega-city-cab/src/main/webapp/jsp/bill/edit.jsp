<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Bill - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
</head>
<body>
    <div class="container">
        <h1>Edit Bill</h1>

        <form action="${pageContext.request.contextPath}/bill/edit" method="post">
            <input type="hidden" name="id" value="${bill.billId}">

            <div class="form-group">
                <label>Payment Method:</label>
                <select name="paymentMethod" required>
                    <option value="CASH" ${bill.paymentMethod == 'CASH' ? 'selected' : ''}>Cash</option>
                    <option value="CREDIT_CARD" ${bill.paymentMethod == 'CREDIT_CARD' ? 'selected' : ''}>Credit Card</option>
                    <option value="DEBIT_CARD" ${bill.paymentMethod == 'DEBIT_CARD' ? 'selected' : ''}>Debit Card</option>
                    <option value="MOBILE_PAYMENT" ${bill.paymentMethod == 'MOBILE_PAYMENT' ? 'selected' : ''}>Mobile Payment</option>
                </select>
            </div>

            <div class="form-group">
                <label>Payment Status:</label>
                <select name="paymentStatus" required>
                    <option value="PENDING" ${bill.paymentStatus == 'PENDING' ? 'selected' : ''}>Pending</option>
                    <option value="PAID" ${bill.paymentStatus == 'PAID' ? 'selected' : ''}>Paid</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn">Update Bill</button>
                <a href="${pageContext.request.contextPath}/bill" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
