# Introduction

This project is the basis of a take-home exam designed to evaluate back-end candidates for the Fuze back-office group. It contains a stripped-down application that is typical of the type of microservices that are developed by the back-office group.

This exam provides a basic evaluation of the following:
* Java Fundamentals
* Common Java EE frameworks
* Relational Data Modeling
* RESTful Web Services
* Unit Testing
* Git
* Maven

The app has been stripped of real-world complexities and has been specifically designed to be easy run. It contains a very simple CRUD-style REST API for 3 typical entities: Customers, Users & Departments. These entities are stored in-memory using HyperSQL DataBase and the schema is automatically created at start-up from a .sql file within the classpath. The entire application, including the web server, is packaged into a single Jar file that can be run by invoking the main() method of the com.fuze.takehome.Main class, without arguments.

Note that you may find the app incomplete or lacking in features, this is intentional. The goal is not to have a production-ready app. Functionality has been deliberately omitted to make the exercises quicker to complete. 

**Please read these instructions carefully and do your best at completing the exercises below.**

**Submit the completed exercises as a single zip file.** For code review feedback (see exercise question #1), you may either place comments inside the code or accompany the zip file with a seperate text file, whatever you prefer.

# Prerequisites
In order to run and modify the application, you will need to have the following:
* Java 8 SDK
* Eclipse or similar IDE or a text editor (whatever floats your boat)
* Git
* Maven 3, either as IDE plugin or external

# Getting Started

1. Clone this repository to your local development environment.

2. Import the project into your IDE. If you are using Eclipse, import it as an “Existing Maven Project”.

3. Ensure that the project is configured to use the Java 8 SDK.

4. Run the Maven install command to build the project. There should be no compilation problems. All Maven dependencies should be automatically resolved as they are all found in the public Maven Central repository.

5. Run the app by invoking the main() method of the com.fuze.takehome.Main. This can be accomplished in Eclipse by selecting “Run As -> Java Application” when right-clicking on Main.java.

6. The application will start and automatically initialize both an Undertow web server and a HyperSQL DataBase server.

7. The base URL for the REST API is http://localhost:50001/ . All endpoints are relative to that URL. The health endpoint should now be accessible using your favourite REST client at http://localhost:50001/health and it should return an HTTP 200 code.

# Exercises

1. Perform a code review on the class com.fuze.takehome.jaxrs.endpoint.UserEndpoint and com.fuze.takehome.service.UserService. Evaluate the code a though it were written by a colleague and provide any suggestions for the developer as to what should/needs to be changed. If you wish, you may go ahead and make code changes yourself. You may either place comments inside the code or send them as a text file accompanying the code, whatever you prefer.

2. Oh no! A lazy developer started writing a J-Unit test for the UserService but couldn’t be bothered to finish it! Even worse, they left the test in a broken state. Fix the existing test and complete it as you see fit. The class is com.fuze.takehome.test.UserTest. You do not need to create tests for the other entities, only the User entity.

3. The customer endpoint (implement by com.fuze.takehome.jaxrs.endpoint.CustomerEndpoint) does not provide an update method. Implement the ability to update the Customer entity using typical RESTful good practises.

4. The Department-User relationship is a one One-To-Many relationship. Change the relationship so that it becomes a Many-To-Many relationship. You will need to make modifications to all layers of the application. Hint: The schema creation script can be found at /src.main/resources/schema/create.schema.sql.