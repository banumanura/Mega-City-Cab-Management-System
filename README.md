# 🚕 Mega City Cab Management System

A dynamic web application designed for internal company use to manage cab bookings, vehicle and driver details, employee records, and report generation. This project is developed using **Java 1.8, Tomcat 9, MySQL**, and follows **SOLID principles** with proper design patterns for scalability and maintainability.

## 🚀 Features

- **Role-Based Authentication:** Secure login with hashed passwords for Receptionist, Manager, and System Admin roles.
- **Booking Management:** Create, update, and view booking history.
- **Vehicle and Driver Management:** Add, update, and manage vehicle and driver details.
- **Payment Processing:** Calculate bills and generate invoices.
- **Employee Management:** Manage employee information with role assignments.
- **Report Generation:** Generate daily reports and monitor driver performance.

## 🛠️ Technologies Used

- **Java 1.8**
- **Apache Tomcat 9**
- **MySQL**
- **JSP and Servlets**
- **JDBC**
- **Maven**
- **BCrypt** for password hashing

## ⚙️ Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/banumanura/Mega-City-Cab-Management-System.git
cd mega-city-cab
```

### 2. Database Setup
- Create a MySQL database:
```sql
CREATE DATABASE mega_city_cab;
USE mega_city_cab;
```
- Execute the provided SQL script to create tables and insert default roles.

### 3. Configure Database Connection
- Update the database configuration in `util/DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/MegaCityCab_DB";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Deploy on Tomcat
- Copy the generated `.war` file from the `target` directory to the `webapps` folder of Tomcat.
- Start the Tomcat server:
```bash
cd /path/to/tomcat/bin
./startup.sh  # For Linux/Mac
startup.bat   # For Windows
```
- Access the application at http://localhost:8080/MegaCityCab

## ✅ Usage
1. **Login:** Use the default credentials provided in the SQL script.
2. **Dashboard Access:** Role-based dashboard view (Receptionist, Manager, System Admin).
3. **Perform Operations:**
   - Receptionist: Manage bookings and payments.
   - Manager: View reports and manage employees.
   - System Admin: Manage vehicles, drivers, and employee details.

## 🔐 Security Considerations
- Passwords are securely hashed using **BCrypt**.
- Role-based access control is implemented to restrict unauthorized access.
- Prepared statements are used to prevent SQL injection.

## 🚩 Future Enhancements
- Add unit and integration tests.
- Implement session timeout and activity logging.
- Enhance UI using modern front-end frameworks.
- Generate downloadable reports in PDF format.

## 🤝 Contribution
1. Fork the repository.
2. Create a new branch.
3. Make changes and commit.
4. Push to the branch.
5. Create a Pull Request.
