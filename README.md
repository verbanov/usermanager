Users:
1. bob
username: bob
password: bob1234
role: USER

2. alice
username: alice
password: alice1234
role: ADMIN

Login (Roles: USER/ADMIN):
http://localhost:8080/login

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

BasicAuthentication with PasswordEncoder Bcrypt

