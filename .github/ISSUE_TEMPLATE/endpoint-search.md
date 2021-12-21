---
name: Endpoint Search
about: 'Design and implement a search REST endpoint'
title: Design and implement a search REST endpoint for the [ENTITY] entity
labels: ''
assignees: ressec

---

## Purpose

This task aims to design and implement the `search` endpoint used to search for [ENTITIES].

## Signature

Endpoint route is: `/api/v1/person/[ENTITY]/search`

and the request body is to be filled using a `Search[ENTITY]`

## Result

The endpoint returns the following result: `ResponseEntity<List<Client[ENTITY]>>`

## Example

```
[
    {
        "createdDate": "2021-11-10T17:56:32.143+00:00",
        "modifiedDate": "2021-11-10T17:56:32.143+00:00",
        "createdBy": "rest-api-user",
        "modifiedBy": "rest-api-user",
        "status": "ACTIVE",
        "inactiveSince": null,
        "uuid": "ae054287-a2c9-47e6-8a47-6088e7bf2b18",
        "type": "EMAIL_ADDRESS",
        "name": null,
        "description": "And then of course I've got this terrible pain in all the diodes down my left side.",
        "reference": "Atalanta",
        "documents": [],
        "email": "norman.howe@hotmail.com",
        "isDefault": false,
        "addressType": "PROFESSIONAL",
        "person": {
            "entityType": "PERSON",
            "id": "d32de6ac-821e-42a1-ba42-d19c4ff17448"
        }
    },
    {
        "createdDate": "2021-11-10T17:56:32.314+00:00",
        "modifiedDate": "2021-11-10T17:56:32.314+00:00",
        "createdBy": "rest-api-user",
        "modifiedBy": "rest-api-user",
        "status": "ACTIVE",
        "inactiveSince": null,
        "uuid": "ed868371-3664-4762-9d1c-5bcceb934c08",
        "type": "EMAIL_ADDRESS",
        "name": null,
        "description": "I think you ought to know I'm feeling very depressed.",
        "reference": "Eunostus",
        "documents": [],
        "email": "inell.harris@gmail.com",
        "isDefault": true,
        "addressType": "PRIVATE",
        "person": {
            "entityType": "PERSON",
            "id": "d32de6ac-821e-42a1-ba42-d19c4ff17448"
        }
    },
    {
        "createdDate": "2021-11-10T17:56:32.483+00:00",
        "modifiedDate": "2021-11-10T17:56:32.483+00:00",
        "createdBy": "rest-api-user",
        "modifiedBy": "rest-api-user",
        "status": "ACTIVE",
        "inactiveSince": null,
        "uuid": "68fd12a7-5363-4566-a977-140cea46f264",
        "type": "EMAIL_ADDRESS",
        "name": null,
        "description": "I won't enjoy it.",
        "reference": "Deianeira",
        "documents": [],
        "email": "rob.oreilly@hotmail.com",
        "isDefault": false,
        "addressType": "PRIVATE",
        "person": {
            "entityType": "PERSON",
            "id": "d32de6ac-821e-42a1-ba42-d19c4ff17448"
        }
    },
    {
        "createdDate": "2021-11-10T17:56:32.929+00:00",
        "modifiedDate": "2021-11-10T17:56:32.929+00:00",
        "createdBy": "rest-api-user",
        "modifiedBy": "rest-api-user",
        "status": "ACTIVE",
        "inactiveSince": null,
        "uuid": "c51bbfff-d16d-4ed3-adbf-a133d8a2a193",
        "type": "EMAIL_ADDRESS",
        "name": null,
        "description": "I wish you'd just tell me rather trying to engage my enthusiasm, because I haven't got one.",
        "reference": "Hermione",
        "documents": [],
        "email": "zachery.feeney@hotmail.com",
        "isDefault": false,
        "addressType": "PROFESSIONAL",
        "person": {
            "entityType": "PERSON",
            "id": "d32de6ac-821e-42a1-ba42-d19c4ff17448"
        }
    }
]
```
