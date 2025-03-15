<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Vehicle - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
    
    <script>
        function validateForm() {
            var registrationNumber = document.getElementById("registrationNumber").value;
            var model = document.getElementById("model").value;
            var make = document.getElementById("make").value;
            var year = document.getElementById("year").value;
            var color = document.getElementById("color").value;
            var seatCapacity = document.getElementById("seatCapacity").value;
            var vehicleType = document.getElementById("vehicleType").value;
            
            if (registrationNumber == "") {
                alert("Registration Number must be filled out");
                return false;
            }
            
            if (model == "") {
                alert("Model must be filled out");
                return false;
            }
            
            if (make == "") {
                alert("Make must be filled out");
                return false;
            }
            
            if (year == "") {
                alert("Year must be filled out");
                return false;
            }
            
            // Validate year format (4 digits, between 1900 and current year + 1)
            var currentYear = new Date().getFullYear();
            var yearRegex = /^[0-9]{4}$/;
            if (!yearRegex.test(year) || year < 1900 || year > currentYear + 1) {
                alert("Year must be a valid 4-digit year between 1900 and " + (currentYear + 1));
                return false;
            }
            
            if (color == "") {
                alert("Color must be filled out");
                return false;
            }
            
            if (seatCapacity == "") {
                alert("Seat Capacity must be filled out");
                return false;
            }
            
            // Validate seat capacity (positive number)
            var seatCapacityRegex = /^[1-9][0-9]*$/;
            if (!seatCapacityRegex.test(seatCapacity)) {
                alert("Seat Capacity must be a positive number");
                return false;
            }
            
            if (vehicleType == "") {
                alert("Vehicle Type must be selected");
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Add New Vehicle</h1>
        
        
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/vehicle/add" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="registrationNumber">Registration Number:</label>
                    <input type="text" id="registrationNumber" name="registrationNumber" required>
                </div>
                
                <div class="form-group">
                    <label for="model">Model:</label>
                    <input type="text" id="model" name="model" required>
                </div>
                
                <div class="form-group">
                    <label for="make">Make:</label>
                    <input type="text" id="make" name="make" required>
                </div>
                
                <div class="form-group">
                    <label for="year">Year:</label>
                    <input type="number" id="year" name="year" min="1900" max="2030" required>
                </div>
                
                <div class="form-group">
                    <label for="color">Color:</label>
                    <input type="text" id="color" name="color" required>
                </div>
                
                <div class="form-group">
                    <label for="seatCapacity">Seat Capacity:</label>
                    <input type="number" id="seatCapacity" name="seatCapacity" min="1" required>
                </div>
                
                <div class="form-group">
                    <label for="acAvailable">AC Available:</label>
                    <select id="acAvailable" name="acAvailable" required>
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="vehicleType">Vehicle Type:</label>
                    <select id="vehicleType" name="vehicleType" required>
                        <option value="">Select Vehicle Type</option>
                        <option value="STANDARD">Standard</option>
                        <option value="LUXURY">Luxury</option>
                        <option value="VAN">Van</option>
                        <option value="SUV">SUV</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="AVAILABLE">Available</option>
                        <option value="ASSIGNED">Assigned</option>
                        <option value="MAINTENANCE">Maintenance</option>
                        <option value="INACTIVE">Inactive</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Add Vehicle</button>
                    <a href="${pageContext.request.contextPath}/vehicle" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
