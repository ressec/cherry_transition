---
name: Endpoint Create
about: 'Design and implement a create REST endpoint'
title: Design and implement a create REST endpoint for the [entity] entity
labels: ''
assignees: ressec

---

## 1. Purpose

This task aims to design and implement a `create` endpoint used to create a new [entity] entity.

## 2. Signature

Endpoint route is: `/api/v1/person/[entity]/create`

and the request body is to be filled using a `Client[entity]` entity.

### 2.1 Example for Request Body

```
{
    "status": "INACTIVE",
    "type": "EMAIL_ADDRESS",
    "description": "A beautiful day.",
    "email": "fader.asame@hotmail.com",
    "isDefault": false,
    "reference": "A simple reference value.",
    "addressType": "PRIVATE",
    "person": {
        "entityType": "PERSON",
        "id": "d32de6ac-821e-42a1-ba42-d19c4ff17448"
    }
}
```

## 3. Result

The endpoint returns the following result: `ResponseEntity<Client[Entity]>`

## 3.1 Example for Result

```
{
    "createdDate": "2021-11-11T18:05:35.592+00:00",
    "modifiedDate": "2021-11-11T18:05:35.592+00:00",
    "createdBy": "api",
    "modifiedBy": "api",
    "status": "INACTIVE",
    "inactiveSince": "2021-11-11T18:05:35.504+00:00",
    "uuid": "1e4f9d53-f826-45af-bdc5-49a034e02c68",
    "type": "EMAIL_ADDRESS",
    "name": null,
    "description": "A beautiful day.",
    "reference": "A simple reference value.",
    "documents": null,
    "email": "fader.asame@hotmail.com",
    "isDefault": false,
    "addressType": "PRIVATE",
    "person": {
        "entityType": "PERSON",
        "id": "d32de6ac-821e-42a1-ba42-d19c4ff17448"
    }
}
```
