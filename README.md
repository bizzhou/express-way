# ExpressWay


**Development Environment:** IntelliJ IDEA, MySQL
* Spring + SpringMVC + JDBC, Maven
* HTML, CSS, JavaScript, Angular

**Developer:** [Melanie Lin](https://github.com/captain-melanie), [Bin Zhou](https://github.com/bizzhou), [Yuening Li]()

## How to run frontend

install nodejs and npm

```
cd frontend
npm install -g @angular/cli
npm install

ng serve
type localhost:4200 to view page

ng serve will create a dummie server for the angular app
```
For more details, google angular-cli

![GitHub Logo](frontend/app2.gif)

## How to run backend
```
cd backend
mvn spring-boot:run

OR

Open project IntelliJ, and run the main method
```

## Log
- 11/10 - 11/13 log in/sign up, add/delete user, add/delete employee
- 11/14 get most frequent flights, get flights for a given airport
- 11/15 get employee that generate most revenue, get customers who have seats reserved on a given flight

## Fixes
- Add Person table into the database schema, edit the insert files.
