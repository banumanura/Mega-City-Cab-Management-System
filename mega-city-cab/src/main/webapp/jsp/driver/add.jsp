<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Driver - MegaCityCab</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-form.css">
    
    <script>
        function validateForm() {
            var firstName = document.getElementById("firstName").value;
            var lastName = document.getElementById("lastName").value;
            var licenseNumber = document.getElementById("licenseNumber").value;
            var nic = document.getElementById("nic").value;
            var address = document.getElementById("address").value;
            var telephone = document.getElementById("telephone").value;
            var emergencyContact = document.getElementById("emergencyContact").value;
            var dateOfBirth = document.getElementById("dateOfBirth").value;
            var joiningDate = document.getElementById("joiningDate").value;
            
            if (firstName == "") {
                alert("First Name must be filled out");
                return false;
            }
            
            if (lastName == "") {
                alert("Last Name must be filled out");
                return false;
            }
            
            if (licenseNumber == "") {
                alert("License Number must be filled out");
                return false;
            }
            
            if (nic == "") {
                alert("NIC must be filled out");
                return false;
            }
            
            if (address == "") {
                alert("Address must be filled out");
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
            
            if (emergencyContact == "") {
                alert("Emergency Contact must be filled out");
                return false;
            }
            
            // Validate emergency contact format (numbers only)
            if (!telephoneRegex.test(emergencyContact)) {
                alert("Emergency Contact must contain only numbers");
                return false;
            }
            
            if (dateOfBirth == "") {
                alert("Date of Birth must be filled out");
                return false;
            }
            
            if (joiningDate == "") {
                alert("Joining Date must be filled out");
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Add New Driver</h1>
        
       
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/driver/add" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" required>
                </div>
                
                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" required>
                </div>
                
                <div class="form-group">
                    <label for="licenseNumber">License Number:</label>
                    <input type="text" id="licenseNumber" name="licenseNumber" required>
                </div>
                
                <div class="form-group">
                    <label for="nic">NIC:</label>
                    <input type="text" id="nic" name="nic" required>
                </div>
                
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" required>
                </div>
                
                <div class="form-group">
                    <label for="telephone">Telephone:</label>
                    <input type="text" id="telephone" name="telephone" required>
                </div>
                
                <div class="form-group">
                    <label for="emergencyContact">Emergency Contact:</label>
                    <input type="text" id="emergencyContact" name="emergencyContact" required>
                </div>
                
                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth:</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" required>
                </div>
                
                <div class="form-group">
                    <label for="joiningDate">Joining Date:</label>
                    <input type="date" id="joiningDate" name="joiningDate" required>
                </div>
                
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="AVAILABLE">Available</option>
                        <option value="ON_TRIP">On Trip</option>
                        <option value="OFF_DUTY">Off Duty</option>
                        <option value="INACTIVE">Inactive</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Add Driver</button>
                    <a href="${pageContext.request.contextPath}/driver" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
