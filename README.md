# Expense Management Backend

A scalable, Backend for a real-time Expense Management application built with Java 21, Spring Boot, H2 DB. Designed for modularity, reliability.

## 🚀 Features

- RESTful APIs for Expense Management/User Management/Group Management/Admin Operations
- H2 In-Memory DB/ H2 Server Adoptable For Both
- Secure authentication with Google Oauth 2.0 API utilizing their JWT for Authorization Mechanism

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.5.6
- H2 DB
- Maven

## 📦 Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/vetrivetri/expenseManagementApp.git
```



### 2.1 Running the App In local In-Memory H2 DB

``` bash
cd /expenseManagementApp
mvn clean install -DskipTests
mvn spring-boot:run
```

### 2.2 Running the App In local H2 DB-Server

```bash
cd /Path/To/h2/Library
java -jar h2.jar
cd /expenseManagementApp
mvn clean install -DskipTests
mvn spring-boot:run

```
