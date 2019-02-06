 
#### Introduction
A demo of Kotlin & Java mixed project to build Spring Boot 2 REST API. Delombok is applied in order to make sure Java side's lombok annotated codes can be accessed from kotlin side.
 
#### Technology Stacks
* Java 11
* Kotlin
* Spring framework & Spring Boot
  * Spring web MVC
  * Spring-boot 2.1.*
  * spring-data-jpa, H2 DB
* lombok, delombok
* Maven
* Junit 5, Mokito
#### Instruction
* To run the project
```
maven spring-boot:run
```
* curl command for quick toggle & play. 
```
* create new category:

curl -i -XPOST 'http://localhost:9080/categories' \
    -H 'Content-Type:application/json' \
    -d '{"name": "my category"}'

* retrieve category:

curl -i 'http://localhost:9080/categories/{categoryId}

* create product:
curl -i -XPOST 'http://localhost:9080/products' \
    -H 'Content-Type:application/json' \
    -d '{"name": "My Product", "categoryId": {categoryId}}'

* retrieve product
curl -i 'http://localhost:9080/products/{productId}'

* retrive category with products
curl -i 'http://localhost:9080/categories/{categoryId}?includeProducts=true'

```
* H2 web concole can be accessed from http://localhost:9080/h2-console, make sure the JDBC URL on the login page is: jdbc:h2:mem:testdb 
