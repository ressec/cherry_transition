---
name: Endpoint Query
about: 'Design and implement a query REST endpoint'
title: Design and implement a query REST endpoint for the [ENTITY] entity
labels: ''
assignees: ressec

---

## Purpose

This task aims to design and implement the `query` endpoint used to query for [ENTITY] identifiers.

## Signature

Endpoint route is: `/api/v1/person/[ENTITY]/query/{params}`

where params can be any combination of:

| Name | Type | Description |
|---|---|---|
|`email` | String | Email address
|`isDefault` | Boolean | Is default email address?
|`addressType` | AddressType | Address type
|`personId` | String | Person identifier owning email addresses
|`id` | String | Email address identifier
|`statusType` | StatusType | Email address status type
|`createdBy` | String | User name being the creator of email addresses
|`modifiedBy` | String | User name having last modified email addresses

## Result

The endpoint returns the following result: `ResponseEntity<List<String>>`

## Example

```
[
    "647e6c18-318c-4452-a878-5f8f5228c9f2",
    "ba91948f-1089-41d4-8c1e-cdfa3da9088e",
    "848d474c-c96a-47ff-96f4-cd8e44f1372b",
    "ae2e46ac-da38-4e5e-a79f-2660f3e344e8",
    "3e68bdf3-6f62-4977-806a-a300d17494ac",
    "1321ec48-2fb8-4877-888f-8639e4c6c116",
    "f4d3f3d1-51ff-4da2-860b-5815d9587eff",
    "da869252-0b2a-4a2e-8e9e-8c9712a972a2",
    "d5cb38b8-4ab2-4ffe-9c60-45f7cc003b8a",
    "1148aea6-bcae-4a02-8ca7-f90ac02ba991",
    "fa1f26af-950d-4a0f-a1c3-0a438300b27a",
    "7be255e9-0297-42a5-8d1d-dab6a3738b1a",
    "07d0692d-df78-4ba7-8c4d-14a1dd6667b6",
    "cb4b8a71-c7f2-4670-90c6-ae94e67bfcb5",
    "f7607dba-f992-4304-8991-291775706be7",
    "8f6e7b65-198e-4b47-ab99-a6d405b8ec1a",
    "df565c6e-344c-4260-aa07-6c9010d614ca",
    "08199521-0f52-46b5-b1bc-7518f5a7ef1f",
    "205296ac-99d3-4eb5-a00b-12b591729fcb",
    "f1a25b65-48e5-4138-87c5-a9ef2e0ea84a",
    "2ce6d34e-5a7e-4bdb-9823-04cb0befebfa",
    "e89ecef6-4b6c-43a9-80fe-efa7072739e6"
]
```
