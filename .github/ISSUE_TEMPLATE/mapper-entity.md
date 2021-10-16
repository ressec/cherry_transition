---
name: Mapper entity
about: Design and implement a new mapper entity.
title: Design and implement the <entity> mapper entity
labels: ''
assignees: ressec

---

## Purpose

This task aims to cover the design and implementation of the <entity> mapper.

## Description

_A mapper is an interface that (through the **MapStruct** library) can convert instances of entities such as a ClientPerson to a ServerPerson._

## Services

- [ ] Implement service `fromClient()` 
- [ ] Implement service `fromClientList()` 
- [ ] Implement service `fromServer()` 
- [ ] Implement service `fromServerList()`
- [ ] Implement service `copy()` for a client entity.
- [ ] Implement service `copy()` for a persistent entity. 

## Reference(s)

See: `EmailAddressMapper` class.
