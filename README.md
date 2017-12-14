# ExpressWay
ExpressWay is an online travel reservation system, along the lines of expedia.com, kayak.com, and many others. The system allows customers to use the web to browse/search available flights and to make flight reservations. It also supports reverse auctions, in which individuals specify the price they are willing to pay for a seat and the airlines either agree to sell it at that price or not.


**Development Environment:** IntelliJ IDEA, Maven
* Backend: Spring + SpringMVC + JDBC, MySQL, REST
* Frontend: HTML, CSS, TypeScript, Angular2

**Developer:** [Melanie Lin](https://github.com/captain-melanie), [Bin Zhou](https://github.com/bizzhou)

## How to run the project

**Prerequisites:**
1. Install nodejs and npm
```
cd frontend
npm install -g @angular/cli
npm install
```
2. Add data to MySQL
```
cd database
execute files 1.sql, 2.sql, 3.sql and 4.sql
```

**Run the backend:**
```
cd backend
mvn spring-boot:run

OR

Open the project on IntelliJ and run the main method: ExpressWayMain.java
```

**Meanwhile, run the frontend**
```
cd frontend
ng serve
Type localhost:4200 on a browser to view the page
```





