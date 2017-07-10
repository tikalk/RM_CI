# RM_CI

Tikal RoadMapper - CI


API

just to test:
GET http://localhost:8080/api/test?param=a

the actual API:

POST http://localhost:8080/api/build
request:
{
	"gitUrl": "abc",
	"command": "mvn package"
}

you can use the following curl from command line:
curl -H "Content-Type: application/json" -X POST -d '{"gitUrl": "abc","command": "mvn package"}' http://localhost:8080/api/build

