# field-agent-assessment

Field Agent API Assessment Plan
TODO Add time estimates to each of the top-level tasks

## Security Clearance CRUD
### HTTP Requests
#### Find All Security Clearances
Request
GET http://localhost:8080/api/security/clearance HTTP/1.1
Responses
Success: 200 OK
#### Find a Security Clearance By Its Identifier
Request
GET http://localhost:8080/api/security/clearance HTTP/1.1
Responses
Success: 200 OK
Failure (cannot be found): 404 Not Found
#### Add a Security Clearance
Request
Responses
Success: 201 Created
Failure (validation errors): 400 Bad Request
#### Update an Existing Security Clearance
Request
Responses
Success: 204 No Content
Failure (validation errors): 400 Bad Request
Failure (route path ID and request body ID don't match): 409 Conflict
#### Delete a Security Clearance
GET http://localhost:8080/api/security/clearance/1 HTTP/1.1
POST http://localhost:8080/api/security/clearance HTTP/1.1
Content-Type: application/json
{
"name": "Test"
}
PUT http://localhost:8080/api/security/clearance/1 HTTP/1.1
Content-Type: application/json
{
"securityClearanceId": 1,
"name": "Top Secret Updated"
}
Request
Responses
Success: 204 No Content
Failure (cannot be found): 404 Not Found
Failure (is in use validation error): 400 Bad Request
Model
Nothing to do... the SecurityClearance model exists and appears to be good to go.
[ ] Controller (#.# hours)
### SecurityClearanceController
Refer to the AgentController class and use that as an example for the methods to add and
implementation details to follow.
[ ] Domain (#.# hours)
### SecurityClearanceService
### SecurityClearanceServiceTest
Refer to the AgentService class and use that as an example for the methods to add and
implementation details to follow.
Decide how you're going to not touch the database when testing... use mocking or
test double.
Domain Rules
Security clearance name is required
Name cannot be duplicated
Retrieve existing security clearances and check to see if the security clearance to
add/update is in the list
Deleting
Can the delete succeed? We need to know if the security clearance is being referenced.
DELETE http://localhost:8080/api/security/clearance/1 HTTP/1.1

One idea... get all of the data from the agency_agent table for the security clearance ID that
you're about to delete...
Another idea... get row count from the agency_agent table for the security clearance ID that
you're about to delete...
[ ] Data (#.# hours)
### SecurityClearanceMapper (this class exists)
### SecurityClearanceJdbcTemplateRepository (this class exists but is incomplete)
### SecurityClearanceRepository (this interface exists but is incomplete)
### SecurityClearanceJdbcTemplateRepositoryTest (this test class exists but is incomplete)
### Refer to the AgentJdbcTemplateRepository class and use that as an example for the methods
to add and implementation details to follow.
Important: To support testing, update the set_known_good_state() stored
procedure as needed.
### Aliases CRUD
HTTP Requests
Fetch an Individual Agent with Aliases Attached
Request
Responses
Success: 200 OK
Review the code paths through the app for retrieving an agent... need to update to
include aliases
select * from agency_agent where security_clearance_id = 1;
select count(*) from agency_agent where security_clearance_id = 1;
GET http://localhost:8080/api/agent/2 HTTP/1.1

### Add an Alias
Request
Responses
Success: 201 Created
Failure (validation errors): 400 Bad Request
### Update an Existing Alias
Request
Responses
Success: 204 No Content
Failure (validation errors): 400 Bad Request
Failure (route path ID and request body ID don't match): 409 Conflict
### Delete an Alias
Request
POST http://localhost:8080/api/alias HTTP/1.1
Content-Type: application/json
{
"agentId": 1,
"name": "Test"
}
PUT http://localhost:8080/api/alias/1 HTTP/1.1
Content-Type: application/json
{
"aliasId": 1,
"agentId": 1,
"name": "Test",
"persona": "Something"
}
DELETE http://localhost:8080/api/alias/1 HTTP/1.1

Responses
Success: 204 No Content
Failure (cannot be found): 404 Not Found
[ ] Model (#.# hours)
### Alias
[ ] Controller (#.# hours)
### AliasController
Refer to the AgentController class and use that as an example for the methods to add and
implementation details to follow.
[ ] Domain (#.# hours)
### AliasService
### AliasServiceTest
Refer to the AgentService class and use that as an example for the methods to add and
implementation details to follow.
Decide how you're going to not touch the database when testing... use mocking or
test double.
Domain Rules
Name is required
Persona is not required unless a name is duplicated. The persona differentiates
between duplicate names.
Retrieve existing aliases and check to see if the alias to add/update is in the list
If it's a duplicate, then require the persona.
[ ] Data (#.# hours)
### AliasMapper (class)
### AliasJdbcTemplateRepository (class)
### AliasRepository (interface)
### AliasJdbcTemplateRepositoryTest (test class)

Refer to the AgentJdbcTemplateRepository class and use that as an example for the methods
to add and implementation details to follow.
To support testing, update the set_known_good_state() stored procedure as
needed.
### [ ] Global Error Handling (#.# hours)
Determine the most precise exception for data integrity failures and handle it with a
specific data integrity message.
For all other exceptions, create a general "sorry, not sorry" response that doesn't share
exception details.
Refer back to the "Spring Profiles, Error Handling, and CORS" lesson in the LMS.
Research the most specific exception to handle data integrity errors
Test Plan
### Security Clearance
 GET all security clearances
 GET a security clearance by ID
 For GET return a 404 if security clearance is not found
 POST a security clearance
 For POST return a 400 if the security clearance fails one of the domain rules
 Security clearance name is required
 Name cannot be duplicated
 PUT an existing security clearance
 For PUT return a 400 if the security clearance fails one of the domain rules
 DELETE a security clearance that is not in use by ID
 For DELETE return a 404 if the security clearance is not found
 For DELETE return a 400 if the security clearance is in use
### Alias
 GET an agent record with aliases attached
 POST an alias

 For POST return a 400 if the alias fails one of the domain rules
 Name is required
 Persona is not required unless a name is duplicated. The persona differentiates
between duplicate names.
 PUT an alias
 For PUT return a 400 if the alias fails one of the domain rules
 DELETE an alias by ID
 For DELETE Return a 404 if the alias is not found
Global Error Handling
 Return a specific data integrity error message for data integrity issues
 Return a general error message for issues other than data integrit
