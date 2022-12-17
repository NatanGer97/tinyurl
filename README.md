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

#Features
* **short url** - user can create short url for any url
* **url statistics** - user can see statistics about his urls (total amount of clicks, total amount of clicks per url, etc) via mongodb
* **url activity** - user can see activity about his urls (when, which url, who visited etc.) via cassandra
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

note:
    The project is still under development, so some features may be added, 
    or some modifications may be made such as exceptions handling and etc.