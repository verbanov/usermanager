# ****User Manager**** #

## 🚀 Description
This application is a User management system.
The system is a WEB application that provides an interface
user management.

## 🚀 Project structure
**The project has an N-Tier Architecture**
- Controller - This level allows the user to work with this application.
- Service - This level of architecture is responsible for processing the data received from the DAO level.
- DAO - This level of architecture is responsible for communicating with the database.
- PostgreSQL database.

## 🚀 Features
**The system supports the following role model:**
USER
    Authorization (Login)
    View a list of users (List)
    View details of any user (View)
    Logout

ADMIN
    Authorization (Login)
    View a list of users (List)
    View details of any user (View)
    Creating a new user (New/Edit)
    Editing an existing user (New/Edit)
    User Lock/Unlock (Lock/Unlock)
    Logout

    Access configuration by HTTP methods and Role
``` 
    Login (Roles: USER/ADMIN):
    http://localhost:8080/login
    PostMapping
    
    List of all users (Roles: USER/ADMIN):
    http://localhost:8080/api/users
    GetMapping
    
    List of all users with filter by username and/or role (Roles: USER/ADMIN):
    http://localhost:8080/api/users?usernameIn=alice
    http://localhost:8080/api/users?roleIn=ADMIN&usernameIn=alice
    GetMapping
    
    Show information about user with id (Roles: ADMIN):
    http://localhost:8080/api/users/{id}
    GetMapping
    
    Create new user (Roles: ADMIN):
    http://localhost:8080/api/users
    PostMapping
    
    Edit user (Roles: ADMIN):
    http://localhost:8080/api/users/{id}
    PutMapping
    
    Change status of user (Roles: ADMIN):
    http://localhost:8080/api/change-status?id={id}
    PutMapping
    
    Logout (Roles: USER/ADMIN)
    http://localhost:8080/api/logout
    
```

## 🚀 Technologies
- Java 11
- Maven
- PostgreSQL
- Hibernate
- Spring Boot
- BasicAuthentication with PasswordEncoder Bcrypt
- Authentication with JWT 

## 🚀 Quickstart
1. Clone this repository
2. Copy link of project
3. Create new project from Version Control
4. Edit resources/application.properties - set the necessary parameters
``` java
    spring.database.driverClassName=YOUR_DRIVER
    spring.datasource.url=YOUR_URL
    spring.datasource.username=YOUR_USERNAME
    spring.datasource.password=YOUR_PASSWORD
```
5. Create the necessary name of DB
6. Run project
7. You can use following users:
```
    1. bob
    username: bob
    password: bob1234
    role: USER
    
    2. alice
    username: alice
    password: alice1234
    role: ADMIN
```
8. Examples of requests:
    Login (Roles: USER/ADMIN): (PostMapping) http://localhost:8080/login
    Json body: {"login":"alice","password":"alice1234"}
    After logging you can get JWT and use it in another requests

    Create new user (Roles: ADMIN): (PostMapping) http://localhost:8080/api/users
    Json body: {"username":"newUser","password":"newPassword1","firstName":"firstName",
"lastName":"lastName","role":"USER", "status":"INACTIVE"}


## 🚀 Example of parameters for application.properties
``` 
    spring.database.driverClassName=org.postgresql.Driver
    spring.datasource.url=jdbc:postgresql://localhost:5432/users_db
    spring.datasource.username=postgres
    spring.datasource.password=password
```

9. Use Docker to run this project in container environment
    Image on docker hub:
    docker push verbanov/usermanager
    
    Example of the link when you use docker (port: 8081 for requests):
   http://localhost:8081/login
