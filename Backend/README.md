## User Manual: "Pubrunda Applikationen"

## Introduction

This document serves to describe how to interact and make use of the Pubrunda API. The backend of this application functions as a REST API, which means that you interact with it through HTTP requests.

The following instructions explain what requests can be made through the API, while also documenting which classes they can be used with. 

## Authentication

In order to access the resources, all api requests need to be authenticated. This is done by first creating a user, which will return an authentication token that should be provided in all future requests.

To create a user, provide the user details (username and password) with:

`POST /api/auth/register`

To reauthenticate an already existing user, provide the user details (username and password) with:

`POST /api/auth/authenticate`


## Get A Single Resource

### Request

`GET /api/[resource]/{id}`

    curl -i -H 'Accept: application/json' http://localhost:8080/api/users/1

### Response

    HTTP/1.1 200 OK
    Date: Fri, 15 Dec 2023 15:46:11 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json

    {"id": 1,"username": "test","role": "USER"}

### Relevant Entities
Post, Review, Comment, User, FriendRequest, Pub



## Get All Of A Resource

### Request

`GET /api/[resource]`

    curl -i -H 'Accept: application/json' http://localhost:8080/api/users

### Response

    HTTP/1.1 200 OK
    Date: Fri, 15 Dec 2023 15:46:11 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json

    [{"id": 1,"username": "test","role": "USER"}, {"id": 2,"username": "test2","role": "USER"}]

### Query Parameters

##### Post
> | Parameter      | Type           | description    |
> |-----------     |-----------     |----------------|
> | authorId       | Long           |                |
> | authorUsername | String         |                |
> | after          | LocalDateTime  |                |
> | before         | LocalDateTime  |                |

##### Review
> | Parameter      | Type           | description    |
> |-----------     |-----------     |----------------|
> | authorId       | Long           |                |
> | authorUsername | String         |                |
> | after          | LocalDateTime  |                |
> | before         | LocalDateTime  |                |

##### Comment
> | Parameter      | Type           | description    |
> |-----------     |-----------     |----------------|
> | authorId       | Long           |                |
> | authorUsername | String         |                |
> | after          | LocalDateTime  |                |
> | before         | LocalDateTime  |                |

##### User
> | Parameter | Type      | description    |
> |-----------|-----------|----------------|
> | username  | String    |                |

##### FriendRequest
> | Parameter      | Type           | description    |
> |-----------     |-----------     |----------------|
> | id             | Long           |                |
> | fromId         | Long           |                |
> | toId           | Long           |                |
> | fromUsername   | String         |                |
> | toUsername     | String         |                |
> | after          | LocalDateTime  |                |
> | before         | LocalDateTime  |                |

##### Pub
> | Parameter     | Type           | description    |
> |-----------    |-----------     |----------------|
> | ownerId       | Long           |                |
> | ownerUsername | String         |                |
> | name          | String         |                |
> | openAfter     | LocalDateTime  |                |
> | openBefore    | LocalDateTime  |                |
> | closedAfter   | LocalDateTime  |                |
> | closedBefore  | LocalDateTime  |                |


#### Relevant Classes
Post, Review, Comment, User, FriendRequest, Pub



## Create A Resource

### Request

`POST /api/[resource]`

### Response

    HTTP/1.1 201 CREATED
    Date: Fri, 15 Dec 2023 15:46:11 GMT
    Status: 201 CREATED
    Connection: keep-alive
    Content-Type: application/json

    [{"id":1,"from":{"id":1,"username":"test","role":"USER"},"to":{"id":2,"username":"test2","role":"USER"}}]



## Update A Resource

### Request

`PUT /api/[resource]/{id}`

    curl -i -H 'Accept: application/json' -X PUT -d 'username=newUsername' http://localhost:8080/api/users/1


### Response

    HTTP/1.1 200 OK
    Date: Fri, 15 Dec 2023 15:46:11 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json

    {"id": 1,"username": "newUsername","role": "USER"}



## Delete an Object

### Request

`DELETE /api/[resource]/{id}`

    curl -i -H 'Accept: application/json' -X DELETE http://localhost:8080/api/users/1


### Response

    HTTP/1.1 200 OK
    Date: Fri, 15 Dec 2023 15:46:11 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json

    {message: "User was deleted successfully"}


### Relevant Classes
Post, Review, Comment, User, FriendRequest, Pub