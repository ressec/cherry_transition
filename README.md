# 1 - Project description
<hr/>

The Cherry project is a java full-stack e-commerce solution providing a micro-services cloud ready platform for customers to buy and sell any kind of product or service.

Artifact | Description |
:--- | :---
`parent` | Artifact used for the whole **Maven** build. |
`commons` | Artifact providing common entities to be used by the client applications. |
`model` | Artifact containing the **Cherry** data model entities to be used by the client applications. |
`persistence` | Artifact containing the backend entities (not visible nor accessible by the client applications). |
`rest-api` | Artifact containing the REST APIs entities that can be used by the client applications. |


# 2 - REST API Controllers and Endpoints
<hr/>

The REST API controllers and endpoints are available at: http://localhost:8085/swagger-ui/

The REST API controllers available are:

| Controller | Description |
| :--- | :--- |
| `EmailAddress` | Used to manage the email addresses of the persons. |
| `PostalAddress` | Used to manage the postal addresses of the persons. |
| `PhoneNumber` | Used to manage the phone numbers of the persons. |
| `Document` | Used to manage the documents. |
| `Person` | Used to manage the persons. |
| `Organization` | Used to manage the organizations. |


# 3 - Tests
<hr/>

## 3.1 - Integration Tests

The integration tests are run using a Docker image of the H2 database. to run the integration tests from Maven, activate the **Maven** 
`integration-tests` profile and execute the `verify` goal from the **parent** module.

It will start a Docker container based on the h2 image (nemerosa one) and make available h2 on port `9093`. It will
then run all the integration tests and then stop the container.

## 3.2 - Local Testing

### Run integration tests on local  

You can also run the integration tests on local. For that you need to have a h2 instance up and running and available on port `9092`.

You then can activate one of the following profile:

| Profile | Description |
| :--- | :--- |
| `db-dev` | Used for development. |
| `db-test` | Used for testing/integration. |
| `db-uat` | Used for user acceptance tests. |
| `db-prod` | Used for production. |
| `db-public` | Used for customers. |

Ensure that for the given profile, the schema in the database is initialized. If not, then on the **persistence** module, execute the following Maven
goal: `flyway:migrate`.

and then for the given test, run/debug it.

### Info on a schema

To get info on a specific schema, select the corresponding Maven profile and go to the **persistence** module and execute the: `flyway:info` goal.

### Clean a schema

To clean a specific schema, select the corresponding Maven profile and go to the **persistence** module and execute the: `flyway:clean` goal. Don't
forget that after having cleaned a schema, you will have to re-create it (see - Initialize a schema) before being able to use it.

### Initialize a schema

To initialize a specific schema, select the corresponding Maven profile and go to the **persistence** module and execute the: `flyway:migrate` goal.
