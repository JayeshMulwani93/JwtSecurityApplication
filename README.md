This Project is intended to give people a basic understanding of JWT and how JWT can be used to secure an application.

I wanted to cover the below APIs:

1.	SignUp
2.  Login to get the accessToken and provide the expiration Time Stamp.
3.  Logout
4.  Update Password & logout all existing sessions.
5.	Refresh existing JWT(UI will invoke this a few seconds before the token is about to expire)
6.	Endpoint authorised only for USERS.
7.  Endpoint authorised only for ADMINS.

Tech Stack Used:
1.	Java 8
2.	Spring Boot
3.	Spring Security
4.	JWT
5.	Rest WebServices.
6.	H2 Database
7.	RedisCache
8.	Swagger
9.	Spring Data JPA
10. Jackson to validate input requests
11. Lombok

![Swagger](https://github.com/JayeshMulwani93/jwt-security-application/blob/master/src/main/resources/SwaggerScreenshotForReadMe.png)
