<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Customer - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
    
    <script>
        function validateForm() {
            var firstName = document.getElementById("firstName").value;
            var lastName = document.getElementById("lastName").value;
            var address = document.getElementById("address").value;
            var city = document.getElementById("city").value;
            var telephone = document.getElementById("telephone").value;
            var email = document.getElementById("email").value;
            var nic = document.getElementById("nic").value;
            
            if (firstName == "") {
                alert("First Name must be filled out");
                return false;
            }
            
            if (lastName == "") {
                alert("Last Name must be filled out");
                return false;
            }
            
            if (address == "") {
                alert("Address must be filled out");
                return false;
            }
            
            if (city == "") {
                alert("City must be filled out");
                return false;
            }
            
            if (telephone == "") {
                alert("Telephone must be filled out");
                return false;
            }
            
            // Validate telephone format (numbers only)
            var telephoneRegex = /^[0-9]+$/;
            if (!telephoneRegex.test(telephone)) {
                alert("Telephone must contain only numbers");
                return false;
            }
            
            if (email == "") {
                alert("Email must be filled out");
                return false;
            }
            
            // Validate email format
            var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                alert("Invalid email format");
                return false;
            }
            
            if (nic == "") {
                alert("NIC must be filled out");
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Edit Customer</h1>
        
        
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/customer/edit" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="id" value="${customer.customerId}">
                
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" value="${customer.firstName}" required>
                </div>
                
                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value="${customer.lastName}" required>
                </div>
                
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" value="${customer.address}" required>
                </div>
                
                <div class="form-group">
                    <label for="city">City:</label>
                    <input type="text" id="city" name="city" value="${customer.city}" required>
                </div>
                
                <div class="form-group">
                    <label for="telephone">Telephone:</label>
                    <input type="text" id="telephone" name="telephone" value="${customer.telephone}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${customer.email}" required>
                </div>
                
                <div class="form-group">
                    <label for="nic">NIC:</label>
                    <input type="text" id="nic" name="nic" value="${customer.nic}" required>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Update Customer</button>
                    <a href="${pageContext.request.contextPath}/customer" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>