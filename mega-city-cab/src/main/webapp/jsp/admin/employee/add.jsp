<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Employee - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
    
    <script>
        function validateForm() {
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var fullName = document.getElementById("fullName").value;
            var role = document.getElementById("role").value;
            var email = document.getElementById("email").value;
            var phone = document.getElementById("phone").value;
            
            if (username == "") {
                alert("Username must be filled out");
                return false;
            }
            
            if (password == "") {
                alert("Password must be filled out");
                return false;
            }
            
            if (password.length < 8) {
                alert("Password must be at least 8 characters long");
                return false;
            }
            
            if (password != confirmPassword) {
                alert("Passwords do not match");
                return false;
            }
            
            if (fullName == "") {
                alert("Full Name must be filled out");
                return false;
            }
            
            if (role == "") {
                alert("Role must be selected");
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
            
            if (phone == "") {
                alert("Phone must be filled out");
                return false;
            }
            
            // Validate phone format (numbers only)
            var phoneRegex = /^[0-9]+$/;
            if (!phoneRegex.test(phone)) {
                alert("Phone must contain only numbers");
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Add New Employee</h1>
        
        
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/employee/add" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                    <small class="form-text">Password must be at least 8 characters long</small>
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                
                <div class="form-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" name="fullName" required>
                </div>
                
                <div class="form-group">
                    <label for="role">Role:</label>
                    <select id="role" name="role" required>
                        <option value="">--Select Role--</option>
                        <option value="ADMIN">Administrator</option>
                        <option value="MANAGER">Manager</option>
                        <option value="RECEPTIONIST">Receptionist</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="phone">Phone:</label>
                    <input type="text" id="phone" name="phone" required>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Add Employee</button>
                    <a href="${pageContext.request.contextPath}/admin/employee" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>