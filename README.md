# checklists-rest-service
A simple REST service for managing re-usable checklists

A checklist is comprised of a set of known tasks.  When the time comes to carry out the tasks on the checklist, an instance of the checklist is created.  The instance tracks the completion of the individual tasks associated with that instance of the checklist.

A checklist can be instanced multiple times.

**This project is an early work in progress.**

### **Prerequisites**
Java 8

### **Running**
`./gradlew bootRun
`
### **Run Tests**
`./gradlew test`

### **Endpoint Examples (using Curl or Postman)**

`POST http://localhost:8080/checklists/`

```Body { "name": "Checklist 1" }```

`POST http://localhost:8080/checklists/1/tasks`

```Body { "description": "First Task" }```

`POST http://localhost:8080/checklists/1/instances`



