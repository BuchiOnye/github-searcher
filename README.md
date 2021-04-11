**GITHUB REPOSITORY SEARCHER**
-
This is a spring mvc project which 

1) consists of views a user can interact with to search their public repositories for commits analytics as well as contributors.
    
2) exposes relevant APIs 
    These APIs consist of a 
    1. user public repository search api
    2. repository contributors api which is paginated
    3. paginated repository commit details api 
 
Documentation for the API can be found using the URL below after starting the service.

`http://localhost:8080/swagger-ui.html#`

[![URL-Shortener-API.png](https://i.postimg.cc/RhdzXNgQ/URL-Shortener-API.png)](https://postimg.cc/sGM82Ddv)

## Technologies
Project is created with:
* Java programming language
* Spring boot framework
* Mockito library (for unit test)
* Docker
* Redis
* Bootstrap
* Javascript


## Setup
To run this project, ensure you have previously installed Java Develoment Kit (JDK) (preferably version 1.8 or greater), Apache maven build tool and Redis locally :
```
Navigate to root project directory 
$  cd ./console

Build application using
$ mvn clean package 

Create a desired directory external to the application folder
Navigate to target directory and copy the github-searcher.jar to desired directory 
$ cd ./target

$ cd console/src/main/resources

Copy configuration file (application.poperties) to same directory with previously copied jar file
Modify database connection credentials e.g github client credentials or redis connection configurations if desired
Run 
$ java -jar github-searcher.jar

Alternatively install docker on your device
Navigate to project directory

To build project,

Run 
docker build -t github-searcher:latest .

To start application
Run 
docker run --rm -it -p 8081:8081 --name github-searcher github-searcher:latest

```
      
**Security**
-
SSO OAuth2 Authentication using GITHUB for authentication

`POST http://localhost:8080/` requires authentication. 

NOTE: All apis are completely free services which do not require authentication, however, you must specify a valid github username to make calls successfully 

**Testing**
-
The code contains tests which can be found in the test folder.

[![shortened-url.png](https://i.postimg.cc/QtLKcxYd/shortened-url.png)](https://postimg.cc/B8gv0sGr)

[![AAA.png](https://i.postimg.cc/MHqcr3Sk/AAA.png)](https://postimg.cc/34fJkBN9)

[![BBBB.png](https://i.postimg.cc/L6yqNVQ7/BBBB.png)](https://postimg.cc/QHTxMc3k)


## Launch
After a successful startup of the project, 
* access swagger api documentation link with [http://localhost:8080/swagger-ui.html#](http://localhost:8080/swagger-ui.html#)
* you can confirm startup status by accessing Health Check link: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
* Postman-collection [https://www.getpostman.com/collections/8442bc09427460d116c9](https://www.getpostman.com/collections/8442bc09427460d116c9)
