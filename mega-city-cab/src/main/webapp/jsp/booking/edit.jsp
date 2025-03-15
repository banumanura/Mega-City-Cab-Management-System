<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Booking - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
    
    <script>
        function validateForm() {
            var pickupLocation = document.getElementById("pickupLocation").value;
            var destination = document.getElementById("destination").value;
            var estimatedDistance = document.getElementById("estimatedDistance").value;

            if (pickupLocation == "") {
                alert("Pickup location must be filled out");
                return false;
            }

            if (destination == "") {
                alert("Destination must be filled out");
                return false;
            }

            if (estimatedDistance == "" || isNaN(estimatedDistance) || parseFloat(estimatedDistance) <= 0) {
                alert("Estimated distance must be a positive number");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Edit Booking</h1>
        
        
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/booking/update" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="bookingId" value="${booking.bookingId}">

                <div class="form-group">
                    <label for="customerId">Customer:</label>
                    <select id="customerId" name="customerId" required>
                        <option value="">Select Customer</option>
                        <c:forEach items="${customers}" var="customer">
                            <option value="${customer.customerId}" ${customer.customerId == booking.customerId ? 'selected' : ''}>
                                ${customer.firstName} ${customer.lastName} (${customer.telephone})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="vehicleId">Vehicle:</label>
                    <select id="vehicleId" name="vehicleId" required>
                        <option value="">Select Vehicle</option>
                        <c:forEach items="${vehicles}" var="vehicle">
                            <option value="${vehicle.vehicleId}" ${vehicle.vehicleId == booking.vehicleId ? 'selected' : ''}>
                                ${vehicle.make} ${vehicle.model} (${vehicle.registrationNumber})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="driverId">Driver:</label>
                    <select id="driverId" name="driverId" required>
                        <option value="">Select Driver</option>
                        <c:forEach items="${drivers}" var="driver">
                            <option value="${driver.driverId}" ${driver.driverId == booking.driverId ? 'selected' : ''}>
                                ${driver.firstName} ${driver.lastName} (${driver.licenseNumber})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="pickupLocation">Pickup Location:</label>
                    <input type="text" id="pickupLocation" name="pickupLocation" value="${booking.pickupLocation}" required>
                </div>

                <div class="form-group">
                    <label for="destination">Destination:</label>
                    <input type="text" id="destination" name="destination" value="${booking.destination}" required>
                </div>

                <div class="form-group">
                    <label for="bookingDate">Booking Date:</label>
                    <input type="date" id="bookingDate" name="bookingDate" value="${booking.bookingDate}" required>
                </div>

                <div class="form-group">
                    <label for="bookingTime">Booking Time:</label>
                    <input type="time" id="bookingTime" name="bookingTime" value="${booking.bookingTime}" required>
                </div>

                <div class="form-group">
                    <label for="estimatedDistance">Estimated Distance (km):</label>
                    <input type="number" id="estimatedDistance" name="estimatedDistance" step="0.01" min="0.01" value="${booking.estimatedDistance}" required>
                </div>

                <div class="form-group">
                    <label for="specialRequests">Special Requests:</label>
                    <textarea id="specialRequests" name="specialRequests" rows="3">${booking.specialRequests}</textarea>
                </div>

                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="PENDING" ${booking.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                        <option value="CONFIRMED" ${booking.status == 'CONFIRMED' ? 'selected' : ''}>Confirmed</option>
                        <option value="IN_PROGRESS" ${booking.status == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                        <option value="COMPLETED" ${booking.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                        <option value="CANCELLED" ${booking.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                    </select>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn">Update Booking</button>
                    <a href="${pageContext.request.contextPath}/booking" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
