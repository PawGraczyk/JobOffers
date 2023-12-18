# JobOffers
Web application designed for aggregation of job offers for Junior Java Developers from various sources, including websites and other web applications. The primary functionality of the application involves extracting current job listings from websites.<br>
Only registered and verified user (bearer authentication) can use application's endpoints (besides /register and /token).

## Details
- Java 17+,
- Spring Boot web application,
- Modular monolith hexagonal architecture,
- Facade design pattern,
- NoSQL database (MongoDB) for offer and user repositories,
- Unit tests covering each of the modules,
- "Happy path" integration test (offers external API stubbed using WireMock),
- Scheduler used for fetching new, unique offers periodically,  
- As for now it is a pure backend application. 

## Installation and testing
[Docker Desktop](https://www.docker.com/products/docker-desktop/) is required for the application to run.

Docker files:<br>
a) Docker-compose file - **[docker-compose.yml](https://github.com/pgraczykdev/JobOffers/blob/master/docker-compose.yml)** <br>
b) Mongo-db admin role initialization - **[init-mongo.js](https://github.com/pgraczykdev/JobOffers/blob/master/init-mongo.js)** <br>

Application endpoints can be tested using [Postman](https://www.postman.com/) or Swagger UI (default address: http://localhost:8080/swagger-ui/index.html).


|       ENDPOINT        | METHOD |         REQUEST          | RESPONSE |             FUNCTION                           |
|:---------------------:|:------:|:------------------------:|:--------:|:----------------------------------------------:|
|    /register          |  POST | JSON (username, password) |   JSON   | Registers unique user (based on username)       |
|    /token             |  POST | JSON (username, password) |   JSON   | Retrieves token for the registered user (login) |
|    /offers            |  GET  | --                        |   JSON   | Retrieves all saved offers from the database    |
|    /offers/{id}       |  GET  | PATH VARIABLE (id)        |   JSON   | Retrieves offer with the given id               |
|    /offers            |  POST | JSON (company, title, salary, offerUrl) | JSON |Adds new, unique offer (based on offerUrl) to the database|
