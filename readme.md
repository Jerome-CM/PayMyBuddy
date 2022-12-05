

![Logo picture](https://github.com/Jerome-CM/PayMyBuddy/blob/develop/src/main/webapp/CSS/img/screenshoots/home.png?raw=true)

This repository contains an application for sent money between friends. It's an REST API with a Web Interface

## :rocket: Getting Started

<img src="https://img.shields.io/badge/Maven-3.8.6-red"/>
<img src="https://img.shields.io/badge/Java-17-orange"/>
<img src="https://img.shields.io/badge/Spring-5.3.22-brightgreen"/>
<img src="https://img.shields.io/badge/Spring%20Boot-2.7.3-brightgreen"/>
<img src="https://img.shields.io/badge/MySQL-8.0.29-blue"/>
<img src="https://img.shields.io/badge/Jar%20jstl-1.2-lightblue"/>

## :hammer_and_wrench: Configuration

On your IDE, configure the project with your parameters
In application.properties file, located in : src/main/resources

* server
* server.port
* spring.datasource.url
* spring.datasource.username
* spring.datasource.password
* When you start the application for a first time, switch spring.jpa.hibernate.ddl-auto to create ( switch at "upgrade" for the next time )

Configure your access for manage the app on Spring Security : -----TODO ----

## :books: Documentation

The different endpoints is documented with swagger at this address after run start the application : http://localhost:8080/swagger-ui/

>  Note  
>  This project is an exercise of OpenClassRooms Java training
