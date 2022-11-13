Login:
http://localhost:8080/login

List of all users:
http://localhost:8080/user
GetMapping

List of all users with filter by username and/or role:
http://localhost:8080/user?usernameIn=alice
http://localhost:8080/user?roleIn=ADMIN&usernameIn=alice
GetMapping

Show information about user with id:
http://localhost:8080/user/{id}
GetMapping

Create new user:
http://localhost:8080/user/new
PostMapping

Edit user:
http://localhost:8080/user/{id}/edit
PutMapping

Logout
http://localhost:8080/logout

BasicAuthentication with PasswordEncoder Bcrypt

