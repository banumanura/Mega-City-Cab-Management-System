<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MegaCityCab - Admin Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="page-wrapper">
        <!-- Top Navigation Bar -->
        <nav class="top-navbar">
            <div class="brand">
                <i class="fas fa-taxi"></i>
                <h1>MegaCityCab</h1>
            </div>
            
            <div class="navbar-actions">
                <div class="datetime">
                    <i class="far fa-calendar-alt"></i>
                    <span>March 15, 2025</span>
                </div>
                <div class="user-profile">
                    <div class="avatar">
                        <i class="fas fa-user"></i>
                    </div>
                    <div class="user-details">
                        <span class="username">${sessionScope.employeeName}</span>
                        <span class="role">Administrator</span>
                    </div>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/logout" class="logout">
                            <i class="fas fa-sign-out-alt"></i> Logout
                        </a>
                    </div>
                </div>
            </div>
        </nav>
        
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h2>Admin Console</h2>
            </div>
            
            <div class="sidebar-menu">
                <ul>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/admin/dashboard">
                            <i class="fas fa-th-large"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/employee">
                            <i class="fas fa-users"></i>
                            <span>Employees</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/customer">
                            <i class="fas fa-user-friends"></i>
                            <span>Customers</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/driver">
                            <i class="fas fa-id-card"></i>
                            <span>Drivers</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/vehicle">
                            <i class="fas fa-car"></i>
                            <span>Vehicles</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/booking">
                            <i class="fas fa-calendar-check"></i>
                            <span>Bookings</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/bill">
                            <i class="fas fa-file-invoice-dollar"></i>
                            <span>Billing</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/reports/bookings">
                            <i class="fas fa-chart-bar"></i>
                            <span>Reports</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/system-log">
                            <i class="fas fa-cogs"></i>
                            <span>System</span>
                        </a>
                    </li>
                </ul>
            </div>
        </aside>
        
        <!-- Main Content Area -->
        <main class="main-content">
            <div class="page-header">
                <h1>Admin Dashboard</h1>
                
            </div>
            
            <div class="module-grid">
                <!-- Employee Management -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-users"></i>
                        <h2>Employee Management</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/admin/employee/add" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Add New Employee</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/employee" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View All Employees</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/employee" class="action-link">
                            <i class="fas fa-edit"></i>
                            <span>Update/Delete Employee</span>
                        </a>
                    </div>
                </div>
                
                <!-- System Management -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-cogs"></i>
                        <h2>System Management</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/admin/system-log" class="action-link">
                            <i class="fas fa-clipboard-list"></i>
                            <span>View System Logs</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/roles" class="action-link">
                            <i class="fas fa-user-shield"></i>
                            <span>Manage Roles and Permissions</span>
                        </a>
                    </div>
                </div>
                
                <!-- Customer Management -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-user-friends"></i>
                        <h2>Customer Management</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/customer" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View All Customers</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/customer/add" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Add New Customer</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/customer" class="action-link">
                            <i class="fas fa-edit"></i>
                            <span>Update/Delete Customer</span>
                        </a>
                    </div>
                </div>
                
                <!-- Booking Management -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-calendar-check"></i>
                        <h2>Booking Management</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/booking" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View All Bookings</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/booking/add" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Create New Booking</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/booking" class="action-link">
                            <i class="fas fa-edit"></i>
                            <span>Update Booking</span>
                        </a>
                    </div>
                </div>
                
                <!-- Vehicle Management -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-car"></i>
                        <h2>Vehicle Management</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/vehicle" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View All Vehicles</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/vehicle/add" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Add New Vehicle</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/vehicle" class="action-link">
                            <i class="fas fa-edit"></i>
                            <span>Update/Delete Vehicle</span>
                        </a>
                    </div>
                </div>
                
                <!-- Driver Management -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-id-card"></i>
                        <h2>Driver Management</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/driver" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View All Drivers</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/driver/add" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Add New Driver</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/driver" class="action-link">
                            <i class="fas fa-edit"></i>
                            <span>Update/Delete Driver</span>
                        </a>
                    </div>
                </div>
                
                <!-- Billing -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-file-invoice-dollar"></i>
                        <h2>Billing</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/bill" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View All Bills</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/bill/generate" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Generate New Bill</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/bill" class="action-link">
                            <i class="fas fa-edit"></i>
                            <span>Update Payment Status</span>
                        </a>
                    </div>
                </div>
                
                <!-- Reports -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-chart-bar"></i>
                        <h2>Reports</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/admin/reports/bookings" class="action-link">
                            <i class="fas fa-file-alt"></i>
                            <span>Booking Reports</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/reports/revenue" class="action-link">
                            <i class="fas fa-money-bill-wave"></i>
                            <span>Revenue Reports</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/reports/vehicles" class="action-link">
                            <i class="fas fa-car"></i>
                            <span>Vehicle Reports</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/reports/drivers" class="action-link">
                            <i class="fas fa-id-card"></i>
                            <span>Driver Reports</span>
                        </a>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>