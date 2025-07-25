# Fleet Management System🚗📍  

## Overview  
The **Fleet Management System** is a robust backend application built with Java Spring Boot and MySQL/H2, designed to streamline vehicle, driver, trip, and maintenance management for logistics and transportation companies. It provides scalable CRUD operations, RESTful APIs, and centralized data handling.


---

## ⚙️ Features

- 🚘 Vehicle Management (Add, Update, Delete, List)
- 👨‍✈️ Driver Management
- 📍 Trip Assignments
- 🧰 Maintenance Scheduling
- 📊 REST APIs for all major entities

---

## 🧪 How to Run Locally

1. **Clone the repository**
   ```bash
   git clone https://github.com/kalyanram003/FleetManagement.git
   cd FleetManagement
   ```


## Tech Stack  
**Backend**              : Java 17, Spring Boot, Spring Data JPA

**Database**             : MySQL

**PDF Generation**       : iText

**Reverse Geocoding**    : Google Maps Geocoding API

**Build Tool**           : Maven

**Version Control**      : Git, GitHub


## Project Structure

**src/
 
 ├── controller/    --> Exposes REST APIs
 
 ├── service/       --> Business logic
 
 ├── repository/    --> Database access (JPA)
 
 ├── model/         --> Entity classes
 
 └── Config/        --> Security Classes

 ##  Set up your database config in src/main/resources/application.properties:

```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/fleetdb
  spring.datasource.username=root
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
```


 ## Setup Instructions

**Clone the repository**  :  git clone https://github.com/kalyanram003/Fleet-Management-System.git

**Build the project**      :  ./mvnw clean install

**Run the application**    :  ./mvnw spring-boot:run

**Access the APIs at**     :  http://localhost:8080/fms/...


## Important APIs

Endpoint                             ------>   Description

POST /fms/vehicles                   ------>   Add a new vehicle

POST /fms/locations                  ------>   Create a new location entry (with optional vehicle association)

GET /fms/locations/{id}              ------>   Fetch a location by ID

GET /fms/vehicles/{id}/locations     ------>   Fetch all locations for a vehicle

GET /fms/vehicles/{id}/locations/pdf ------>   Generate and download a PDF report
