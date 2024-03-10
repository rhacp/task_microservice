### Run the Project

First, download the repository as a ZIP file and build the project with Maven.

Then, you can run a Docker stack with both the application and the database that will stay online.

---

### Build the Project with Maven

1. Ensure you are in the project's root directory. Then, build the project using Maven with the following command: <br>
   `mvn clean package`
2. Then, the calculations are made and the call to the REST endpoint is sent.

---

### Run Docker Container

##### Build Docker Image

Ensure you are in the project's root directory. Create a new Docker image named "task-standalone" with the following command:<br>
`docker build -t task-standalone .`

##### Run Docker Compose File

Once the image is created, you can run the microservice in a Docker stack called "task-standalone" by using the following command:<br>
`docker-compose up -d`

---

### Usage

Once the application is running, you can send a POST request on the `/api/input` endpoint with the JSON input in the request body through Postman or any other tool. The service will then make the calculations according to the input, send the answer to the REST endpoint and return a response containing details about the call.

ENDPOINT: `http://localhost:8080/api/input`

##### JSON Input Example

```
{
  "operation_number": 5,
  "input_array":[
    {"command":"append", "number": 22},
    {"command":"multiply", "number": 2},
    {"command":"power", "number": 2},
    {"command":"reduce", "number": 2937},
    {"command":"multiply", "number": 4},
    {"command":"divide", "number": 9}
  ]
}
```

---

### Tech Stack

* Java 17
* Maven
* Spring Boot
* Docker
* PostgreSQL