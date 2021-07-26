# checklists-rest-service
A simple REST service for managing re-usable checklists

A checklist is comprised of a set of known tasks.  When the time comes to carry out the tasks on the checklist, an instance of the checklist is created.  The instance tracks the completion of the individual tasks associated with that instance of the checklist.

A checklist can be instanced multiple times.

**This project is an early work in progress.**

### **Prerequisites**
Java 8

Gradle

### **Running**
`./gradlew bootRun
`
### **Run Tests**
`./gradlew test`

### **Endpoint Examples**

Create a checklist

`curl --header "Content-Type: application/json" --request POST --data '{"name":"List 1"}' http://localhost:8080/checklists `

Add a task to a checklist

`curl --header "Content-Type: application/json" --request POST --data '{"description":"Do the first thing"}' http://localhost:8080/checklists/1/tasks`

Create an instance of a checklist

`curl --header "Content-Type: application/json" --request POST http://localhost:8080/checklists/1/instances`

Get checklists

`curl http://localhost:8080/checklists`

Get tasks associated to checklist definition

`curl http://localhost:8080/checklists/1/tasks`

Get a specific instance of a checklist

`curl http://localhost:8080/checklists/1/instances/1`

Get task instance state (will be folded into the above as develop continues)

`curl http://localhost:8080/checklists/1/instances/1/taskInstances`


### **Next Steps**
Mapping layer between domain entities and JSON responses

Dockerization of service

Users and Spring Security/Authorization

