### Run the project in local:
Clone the git repository
```
git clone https://github.com/jwalax5/score.git
```

cd to root path of the repository
```
cd score
```

build the jar
```
./gradlew clean build
```
run the application
```
java -jar build/libs/topscore-0.0.1-SNAPSHOT.jar
```
test if application start
```
curl localhost:8080/api
```
If you see "Hello, World", that's mean the application has been started.


### Run Unit Test:
```
./gradlew clean test
```

You can view the test result from
: /build/reports/tests/test/index.html 

### Run Integration Test:
```
./gradlew clean iT
```

### Run in docker
```
docker build -t docker-sb .
```

```
docker run -p 8080:8080 docker-sb
```