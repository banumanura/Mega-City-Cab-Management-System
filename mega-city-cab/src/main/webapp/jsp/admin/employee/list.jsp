<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employees - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-list.css">
    <script>
        function confirmDelete(id, name) {
            return confirm("Are you sure you want to delete employee '" + name + "'?");
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Employees</h1>
        
        
        
        <div class="actions">
            <a href="${pageContext.request.contextPath}/admin/employee/add" class="btn">Add New Employee</a>
        </div>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Role</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Status</th>
                        <th>Last Login</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${employees}" var="employee">
                        <tr class="${employee.status == 'INACTIVE' ? 'inactive' : ''}">
                            <td>${employee.employeeId}</td>
                            <td>${employee.username}</td>
                            <td>${employee.fullName}</td>
                            <td>${employee.role}</td>
                            <td>${employee.email}</td>
                            <td>${employee.phone}</td>
                            <td>
                                <span class="status-badge ${employee.status}">${employee.status}</span>
                            </td>
                            <td>
                                <c:if test="${not empty employee.lastLogin}">
                                    <fmt:formatDate value="${employee.lastLogin}" pattern="yyyy-MM-dd HH:mm" />
                                </c:if>
                                <c:if test="${empty employee.lastLogin}">
                                    Never
                                </c:if>
                            </td>
                            <td class="actions">
                                
                                <a href="${pageContext.request.contextPath}/admin/employee/edit?id=${employee.employeeId}" class="btn-action edit" title="Edit">Edit</a>
                                <c:if test="${sessionScope.employee.employeeId != employee.employeeId}">
                                    <a href="#" onclick="if(confirmDelete(${employee.employeeId}, '${employee.fullName}')) { window.location='${pageContext.request.contextPath}/admin/employee/delete?id=${employee.employeeId}'; } return false;" class="btn-action delete" title="Delete">Delete</a>
                                </c:if>
                                <a href="#" onclick="window.location='${pageContext.request.contextPath}/admin/employee/change-password?id=${employee.employeeId}';" class="btn-action password" title="Change Password">Password</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <c:if test="${empty employees}">
            <div class="no-records">
                <p>No employees found.</p>
            </div>
        </c:if>
    </div>
</body>
</html>