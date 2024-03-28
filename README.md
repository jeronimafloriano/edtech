# Edtech
Rest Api with Java(21) and Spring boot 3 for a course school.

With this application it is possible to register users (STUDENT/INSTRUCTOR/ADMIN), courses, enroll and evaluate courses (NPS). 

The application has been configured to use docker to run MySql on port 3306.

## Requirements
Java 21 or higher <br>
Docker <br>
Maven 3.2 or higher

## Running the application 
In the root of the project, open a terminal and run: docker-compose up <br>
Open another terminal also in the root of the project and run: mvn spring-boot:run <br>
The application has tests. To run them, type in the command line: mvn test

## Swagger
The application has been documented using swagger and can be accessed at http://localhost:8080/swagger-ui/index.html.

## Using the application
Important: The application has been configured for authorization/authentication with spring security. <br>
Only the POST /users endpoint can be accessed without being authenticated. 

### Note 1: Access to endpoints

/users - Post method for user creation: Open <br>
/enrollment - Post method for enrolling in a course: Any logged-in user

The other endpoints can only be accessed by authenticated users with the ADMIN Role

### Note 2: Report with JasperReport
The endpoint /courses-review GET method returns a map with the name of the course and the course's NPS (but only courses with more than 4 enrollments will be counted). It will also generate, in the src\main\resources\reports\result folder, a PDF report with this result.

### Note 3: 
To register a user on the POST /users endpoint, you must enter: name, username, email, passowrd and role (STUDENT/INSTRUCTOR/ADMIN). After this, the /users/login endpoint must be accessed by entering the email and password and the authentication token will be returned, which must be sent in the other requests to access the system. 

Note: Once authenticated, if you use swagger, you can send the token through swagger itself (as shown in the image below), so you don't need to send it in other calls within swagger.

![Swagger](https://github.com/jeronimafloriano/edtech/blob/main/authorize_swagger.png)
