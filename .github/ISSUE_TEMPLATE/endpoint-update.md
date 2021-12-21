---
name: Endpoint Update
about: 'Design and implement a update REST endpoint'
title: Design and implement a update REST endpoint for the [ENTITY] entity
labels: ''
assignees: ressec

---

## Purpose

This task aims to design and implement the `update` endpoint used to update an existing [ENTITY].

## Signature

Endpoint route is: `/api/v1/person/[ENTITY]/update`

The request body contains the [ENTITY] to update.

## Result

The endpoint returns the following result: `ResponseEntity<Client[ENTITY]Entity>`

## Example

```
{
  "createdDate": "2021-10-26T16:57:51.263+00:00",
  "modifiedDate": "2021-10-26T16:57:51.263+00:00",
  "createdBy": "Dev user",
  "modifiedBy": "Dev user",
  "statusType": "ACTIVE",
  "since": null,
  "id": "bda07040-8092-4d58-8c22-e474dea1212a",
  "entityType": "EMAIL_ADDRESS",
  "name": null,
  "description": "There's only one life-form as intelligent as me within thirty parsecs of here and that's me.",
  "reference": "Hecuba",
  "documents": [],
  "email": "hayden.denesik@yahoo.com",
  "isDefaultEmail": true,
  "addressType": "OTHER",
  "owner": {
    "entityType": "PERSON",
    "id": "e2d571d4-209f-4efb-bde2-c574057c4acb"
  },
  "identity": {
    "entityType": "EMAIL_ADDRESS",
    "id": "bda07040-8092-4d58-8c22-e474dea1212a"
  },
  "active": true
}
```
