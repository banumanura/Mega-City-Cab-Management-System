<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Employee - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
    
    <script>
        function validateForm() {
            var fullName = document.getElementById("fullName").value;
            var role = document.getElementById("role").value;
            var email = document.getElementById("email").value;
            var phone = document.getElementById("phone").value;
            var status = document.getElementById("status").value;
            
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
            
            if (status == "") {
                alert("Status must be selected");
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Edit Employee</h1>
        
        
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/employee/edit" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="id" value="${employee.employeeId}">
                
                <div class="form-group">
                    <label>Username:</label>
                    <div class="form-field-readonly">${employee.username}</div>
                </div>
                
                <div class="form-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" name="fullName" value="${employee.fullName}" required>
                </div>
                
                <div class="form-group">
                    <label for="role">Role:</label>
                    <select id="role" name="role" required>
                        <option value="">--Select Role--</option>
                        <option value="ADMIN" <c:if test="${employee.role == 'ADMIN'}">selected</c:if>>Administrator</option>
                        <option value="MANAGER" <c:if test="${employee.role == 'MANAGER'}">selected</c:if>>Manager</option>
                        <option value="DISPATCHER" <c:if test="${employee.role == 'DISPATCHER'}">selected</c:if>>Dispatcher</option>
                        <option value="OPERATOR" <c:if test="${employee.role == 'OPERATOR'}">selected</c:if>>Operator</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${employee.email}" required>
                </div>
                
                <div class="form-group">
                    <label for="phone">Phone:</label>
                    <input type="text" id="phone" name="phone" value="${employee.phone}" required>
                </div>
                
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="ACTIVE" <c:if test="${employee.status == 'ACTIVE'}">selected</c:if>>Active</option>
                        <option value="INACTIVE" <c:if test="${employee.status == 'INACTIVE'}">selected</c:if>>Inactive</option>
                    </select>
                </div>
                
                <c:if test="${not empty employee.lastLogin}">
                    <div class="form-group">
                        <label>Last Login:</label>
                        <div class="form-field-readonly">
                            <fmt:formatDate value="${employee.lastLogin}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </div>
                    </div>
                </c:if>
                
                <div class="form-group">
                    <label>Created:</label>
                    <div class="form-field-readonly">
                        <fmt:formatDate value="${employee.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </div>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Update Employee</button>
                    <a href="${pageContext.request.contextPath}/admin/employee" class="btn btn-secondary">Cancel</a>
                    <c:if test="${sessionScope.employee.employeeId != employee.employeeId}">
                        <a href="#" onclick="if(confirm('Are you sure you want to delete this employee?')) { window.location='${pageContext.request.contextPath}/admin/employee/delete?id=${employee.employeeId}'; } return false;" class="btn btn-danger">Delete</a>
                    </c:if>
                </div>
            </form>
        </div>
        
        <div class="action-links">
            <a href="${pageContext.request.contextPath}/admin/employee/change-password?id=${employee.employeeId}" class="btn btn-secondary">Change Password</a>
        </div>
    </div>
</body>
</html>