# Edtech
Rest Api with Java(21) and Spring boot 3 for a course school.

With this application it is possible to register users (STUDENT/INSTRUCTOR/ADMIN), courses, enroll and evaluate courses (NPS). 

The application has been configured to use docker to run MySql on port 3306.

## Requirements
Java 21 or higher
Docker
Maven 3.2 or higher

## Swagger
The application has been documented using swagger and can be accessed at http://localhost:8080/swagger-ui/index.html.

## Using the application
Important: The application has been configured for authorization/authentication with spring security. 
Only the POST /users endpoint can be accessed without being authenticated. 

To register a user on the POST /users endpoint, you must enter: name, username, email, passowrd and role (STUDENT/INSTRUCTOR/ADMIN). After this, the /users/login endpoint must be accessed by entering the email and password and the authentication token will be returned, which must be sent in the other requests to access the system. 

Note: Once authenticated, if you use swagger, you can send the token through swagger itself (as shown in the image below), so you don't need to send it in other calls within swagger.
