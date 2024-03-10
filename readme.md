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

### Tech Stack

* Java 17
* Maven
* Spring Boot
* Docker
* PostgreSQL