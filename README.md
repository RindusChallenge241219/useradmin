# useradmin
Code challenge for Rindus: User administration

Small console application (CRUD) to manage users and their bank account data (IBAN). Required fields are first name, last name and IBAN. Users may have multiple accounts. Entered data must be validated.

Administrators should be able to create, list, update and delete users via console commands. Manipulation operations are restricted to the administrator who created the users. An user cannot be modified by a third party administrator.

## Software requirements
- PostgreSQL 11.x
- Hibernate 5.1.x
- Java 8.x

## Postgres server
- Host: localhost
- Port: 5432
- User/password: postgres/postgres
- Database: useradmin

## Data model
The data model is comprised of three objects: User, Account and Administrator.

| UserInfo |
| ---- |
| id: PK |
| firstName: String |
| lastName: String |

| Account |
| ------- |
| iban: PK |
| balance: double |

An user has N accounts. An account only has one user.

| Administrator |
| ------------- |
| login: String |
| password: String |

An admin has N users created. An user only has one admin.