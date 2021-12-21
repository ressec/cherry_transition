---
name: Endpoint Get
about: 'Design and implement a new get endpoint'
title: Design and implement a get REST endpoint for the <entity> entity
labels: ''
assignees: ressec

---

## Purpose

This task aims to design and implement the `get` endpoint used to retrieve an [entity] from the backend given its identifier.

## Signature

Endpoint route is: `/api/v1/person/[entity]/get/{id}`

## Result

The endpoint returns the following result: `ResponseEntity<[entity]AddressEntity>`

## Example

```
{
  "createdDate": "2021-10-26T09:09:48.259+00:00",
  "modifiedDate": "2021-10-26T09:09:48.264+00:00",
  "createdBy": "Integration Tester",
  "modifiedBy": "Integration Tester",
  "statusType": "ACTIVE",
  "since": null,
  "id": "8b7684af-5a94-4d3d-b9c9-fafbb402c784",
  "entityType": "EMAIL_ADDRESS",
  "name": null,
  "description": "I wish you'd just tell me rather trying to engage my enthusiasm, because I haven't got one.",
  "reference": "Icarus",
  "documents": [],
  "email": "victor.hugo@gmail.com",
  "isDefaultEmail": true,
  "addressType": "PRIVATE",
  "owner": {
    "entityType": "PERSON",
    "id": "e2d571d4-209f-4efb-bde2-c574057c4acb"
  },
  "identity": {
    "entityType": "EMAIL_ADDRESS",
    "id": "8b7684af-5a94-4d3d-b9c9-fafbb402c784"
  },
  "active": true
}
```
