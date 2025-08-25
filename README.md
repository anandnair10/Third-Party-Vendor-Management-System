# Third-Party Vendor Management System


This is a Spring Boot REST API project designed to streamline the management of third-party vendors within an organization. The system supports role-based access for Admins, Employees, and Vendors, enabling efficient onboarding, contract management, vendor rating, and document verification.

## Features:
 ### Admin
    - Register and manage Employees
    - Register and manage Vendors
    - Manage contracts with Vendors
    - Verify documents uploaded by Vendors

 ### Employee
    - View and list all registered Vendors
    - Rate Vendors and provide feedback
    
  ### Vendor
    - Upload required documents for verification

## Role-Based Access
  Implemented using Spring Security with custom UserDetails, UserDetailsService, and security configuration:

  - ADMIN: Full access to manage users, vendors, and contracts
  - EMPLOYEE: Access to view vendors and submit ratings
  - VENDOR: Access to upload documents and view profile

## Technologies Used: -
  - Java 17
  - Spring Boot
  - Spring Security
  - JPA/Hibernate
  - MySQL/PostgreSQL
  - Lombok
  - RESTful APIs
