## Stack 
  1. [Spring Boot]
  1. [Maven]
  1. [Spring Data Jpa]
  1. [Lombok]
  1. [H2 database]
  1. [Swagger]
  1. [Opencsv]
  
  ## How  `Build and Run` project
  
   ```jsx
    // Open root directory the project and excute mvn command
    mvn clean install
    
    // After success building   you can run project
    // Go in 'target' folder you will see bank-data-0.0.1-SNAPSHOT.jar file , try run it
    java -jar ./bank-data-0.0.1-SNAPSHOT.jar
    
    // Open browser and go on http://localhost:8080/swagger-ui.html
    // You will need a file to verify the upload, you can find it here in Resource folder  bank-data\src\main\resources\test file upload.csv
    
    // Also you can open data base admin console just open in browser  http://localhost:8080/h2-console
    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:mem:test
    User Name: sa
    Password:   // password left empty
    
    
    
    
    ```
