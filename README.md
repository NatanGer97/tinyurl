# Tinyurl
Tinyurl is an url shortener written in Java using Spring Boot and **MongoDB**,
**Redis** and **cassandra** which supports scaling and high availability.

## Technologies
* **Backend**: Spring-boot (java)
* **Database**:
    * **MongoDB** - for storing users and urls data related to them  (total amount of clicks, total amount of clicks per url, etc)
    * **Redis** - for storing and mapping short url to long url (key-value)
    * **Cassandra** - for storing and logging user's activity about url visits (when, which url, who visited etc.)
* **Containerization**: Docker
* **Security**: JWT - spring security

# Features
* **Short url** - user can create short url for any url
* **Url statistics** - user can see statistics about his urls (total amount of clicks, total amount of clicks per url, etc) via mongodb
* **Url activity** - user can see activity about his urls (when, which url, who visited etc.) via cassandra
* **User registration** - user can register with email and password
* **User login** - user can log in with email and password



## Run Locally

Clone the project

```bash
  git clone git@github.com:NatanGer97/tinyurl.git
```

Go to the project directory

```bash
  cd my-project
```
start docker-compose
```bash
  docker-compose up -d
```
**note: you may need to run the following command to create key-space in cassandra**
```bash
CREATE KEYSPACE tiny_keyspace
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
```
**Note: For retrieving the jwt token you need to register and login first**
* The login endpoint is: http://localhost:8080/api/login
* use postman to send a post request with the following body in x-www-form-urlencoded format:
  `{
  "username": the email you registered with
  "password": the password you registered with
  }`
* You can use the following command to get the token with pre-defined (test) user:
  `curl -X POST \
  http://localhost:8080/api/login \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'username=test@gmail.com&password=test'`
note:
    The project is still under development, so some features may be added, 
    or some modifications may be made such as exceptions handling and etc.