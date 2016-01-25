# Exchange Rate

Web Application written in Spring with Cucumber/Selenium tests.

## Working application

<http://zooplus.herokuapp.com/>


## How to run?

1. Clone the repository
2. Update `apilayer.key` in the `application.properties` file
* Key can be obtained in the service currencylayer.com
* More information: https://currencylayer.com/documentation
3. Build the project

```
./gradlew build
```

4. Run the application

```
java -jar build/libs/exchange-rate-0.1.0.jar
```

## Running tests

1. Unit tests

```
./gradlew check
```

2. Component tests (Cucumber/Selenium)

```
./gradlew componentTest
```

## Continuous Integration

<https://travis-ci.org/leszko/exchange-rate>