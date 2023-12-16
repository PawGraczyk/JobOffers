# JobOffers
Web application designed for aggregation of job offers for Junior Java Developers from various sources, including websites and other web applications. The primary functionality of the application involves extracting current job listings from websites.

## Specification
- Java 16+,
- Spring Boot web application,
- Modular monolith hexagonal architecture,
- Facade design pattern,
- NoSQL database (MongoDB) for offer and user repositories,
- Unit tests covering each of the modules,
- "Happy path" integration test (offers external API stubbed using WireMock),
- Scheduler used for fetching new, unique offers periodically,  
- As for now it is a pure backend application. 

## Installation
[Docker Desktop](https://www.docker.com/products/docker-desktop/) is required for the application to run.

Docker files:<br>
a) Docker-compose file - **[docker-compose.yml](https://github.com/pgraczykdev/JobOffers/blob/master/docker-compose.yml)** <br>
b) Mongo-db admin role initialization - **[init-mongo.js](https://github.com/pgraczykdev/JobOffers/blob/master/init-mongo.js)** <br>

Application endpoints can be tested using [Postman](https://www.postman.com/) or Swagger UI (default address: http://localhost:8080/swagger-ui/index.html).


