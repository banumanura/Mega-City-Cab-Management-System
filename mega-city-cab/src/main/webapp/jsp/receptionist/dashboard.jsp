<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MegaCityCab - Receptionist Portal</title>
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
                    <span>March 14, 2025</span>
                </div>
                <div class="user-profile">
                    <div class="avatar">
                        <i class="fas fa-user"></i>
                    </div>
                    <div class="user-details">
                        <span class="username">${sessionScope.employeeName}</span>
                        <span class="role">Receptionist</span>
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
                <h2>Receptionist Console</h2>
            </div>
            
            <div class="sidebar-menu">
                <ul>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/receptionist/dashboard">
                            <i class="fas fa-th-large"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/customer">
                            <i class="fas fa-user-friends"></i>
                            <span>Customers</span>
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
                </ul>
            </div>
        </aside>
        
        <!-- Main Content Area -->
        <main class="main-content">
            <div class="page-header">
                <h1>Receptionist Dashboard</h1>
            </div>
            
            <div class="module-grid">
                <!-- Customer Management -->
                <div class="module-card">
                    <div class="module-header">
                        <i class="fas fa-user-friends"></i>
                        <h2>Customer Management</h2>
                    </div>
                    <div class="module-content">
                        <a href="${pageContext.request.contextPath}/customer/add" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Add New Customer</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/customer" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View Customers</span>
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
                        <a href="${pageContext.request.contextPath}/booking/add" class="action-link">
                            <i class="fas fa-plus-circle"></i>
                            <span>Create New Booking</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/booking" class="action-link">
                            <i class="fas fa-list"></i>
                            <span>View Bookings</span>
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
                            <i class="fas fa-file-invoice"></i>
                            <span>Manage Bills</span>
                        </a>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>