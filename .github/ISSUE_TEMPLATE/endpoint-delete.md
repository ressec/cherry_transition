---
name: Endpoint Delete
about: 'Design and implement a delete REST endpoint'
title: Design and implement a delete REST endpoint for the [entity] entity
labels: ''
assignees: ressec

---

## Purpose

This task aims to design and implement the `delete` endpoint used to delete an [entity] from the backend given its identifier.

## Signature

Endpoint route is: `/api/v1/person/[entity]/delete/{id}`

## Result

If successful, `HTTP code = 200` the following message is returned:

`Successfully deleted [entity] with id: '541f37dd-00cd-469e-a9a0-6c4c59437916'` 

If not successful, `HTTP code != 200` the following message is returned:

`Cannot delete [entity] with id: '541f37dd-00cd-469e-a9a0-6c4c59437916' due to: [exception message]!` 

