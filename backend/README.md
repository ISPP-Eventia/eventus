# Project installation

Clone the project locally

## Requirements

1. Installed version of Java jdk 11 or greater
2. Installed version of Maven
3. Installed version of Docker or running locally MySQL database

## Running

Update the latest dependencies with:

```
mvn clean install
```

Run the database with:

```
docker-compose up -d
```

Run the project with:

```
mvn spring-boot:run
```

Project should be running on [`http://localhost:8080`](http://localhost:8080)
