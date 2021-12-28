# Leetcode-clone #

Clone [leecode problem website](https://leetcode.com/problemset/all/) support gcc, java golang anh python3.

Support feature: create problem, contest, teacher management contest and student join and solve contest.

## Requirement

| Technology | Version   |
|------------|-----------|
| Java       | >= 8      |
| Nodejs     | >= 12.8.0 |
| Docker     | >= 19.03  |
| Postgres   | >= 13.4   |
| Redis      | >= 6      |
| npm        | >= 7      |

## Deploy

### Fast way to deploy with only install docker and docker-compose

Clone source and run command:

    docker-compose up

It will take about 15 minutes to down build images and run container. 

### Setup project and run:
#### Start requisite service:
- redis
- postgres 
- docker
#### Build backend:
  - Create leetcode database in postgres and create tables in `database/schema.sql`
  - Insert demo data in `database/demo-seed.sql`
  - Build maven project: `mvn clean install`
  - Run project: `mvn spring-boot:run`

#### Build frontend:

cd to `Frontend/mini-leetcode-frontend` and execute:
 - Install dependency: `npm install`
 - Run project: `npm start`

Demo service:
- domain: http://localhost:3000
- username: admin
- password: sscm@123456

#### Environment variable:
| ENV                   | Description                               | Example                                                        |
|-----------------------|-------------------------------------------|----------------------------------------------------------------|
| DOCKER_SERVER_HOST    | point to your docker service              | `http://103.56.158.222:12375` or `unix:///var/run/docker.sock` |
| SQL_DB_USER           | Postgres user                             | `postgres`                                                     |
| POSTGRES_DB           | Postgres database                         | `leetcode`                                                     |
| SQL_DB_HOST           | Host of postgres                          | `localhost`                                                    |
| SQL_DB_PORT           | Port of postgres                          | `5432`                                                         |
| REDIS_HOST            | Host of redis                             | `localhost`                                                    |
| REDIS_PORT            | Port of redis                             | `6379`                                                         |
| REACT_APP_BACKEND_URL | Host of backend server setup in front end | `http://localhost:8080/api`                                    |
| SERVICE_HOST          | Host of backend server                    | `localhost`                                                    |
| SERVICE_PORT          | Port of backend server                    | `8080`                                                         |
