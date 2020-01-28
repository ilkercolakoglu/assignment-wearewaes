# Assignment for WAES


# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Docker Documentation](https://docs.docker.com/get-started/)

* [Local API documentation and swagger](http://localhost:8080/swagger-ui.html)

## Aim of the project

Provides endpoints for adding base64encoding JSON and compares them.

### Adding Base64EncodedJSON

API URL "/assignment/{id}/{side}"

method POST

REQUEST
{
  "base64encodedJSON": "string"
}

### Find Differences of two sides includes Base64EncodedJSON
API URL "/assignment/{id}"

method GET

## Running the project

mvn clean install

mvn spring-boot:run

## Running on docker container

You can reach dockerfile and run-docker-container.sh in the project.

You can be build and create image with docker file and run docker container with commands inside of run-docker-container.sh


## Testing the project

You can use POSTMAN for calling APIs or use swagger-ui. (/swagger-ui.html)
You can see request and response API models with descriptions and example requests.


In addition, You can run integration and unit test inside src/main/test resources 


## Suggestions for improvement

- Use persistence(not in-memory) relational database
- Use Spring profiles (dev, prod)
- Create a Jenkins pipeline
- Implement Spring Security for securing REST Services
- Expand Diff Content with more sides
- Find differences more detailed. Like that
firstSide  16-19 abcd
SecondSide 16-19 dcba
- Find difference and return new response object. Such as
{
"differences":[{
"startPointOfDiff":16,
"endPointOfDiff":19,
"firstSide":"abcd",
"secondSide":"dcba"
}]
}


