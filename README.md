## How to run the project in local:
First clone the git 
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


## How to run Unit Test:
```
./gradlew clean test
```

You can view the test result from
: /build/reports/tests/test/index.html 

## How to run Integration Test:
```
./gradlew clean iT
```

API docs (using swagger) : 
- Please go to https://editor.swagger.io
- Paste the scoreApi.yaml content into the page

Manual API Test (Using postman) : 
- Please import the postman collection json
- There should be 8 api in total
- Usage can be reference from yaml