## API Documentation: "Pubrunda Applikationen"

## Introduction

This document serves to describe how to interact and make use of the Pubrunda API. The backend of this application functions as a REST API, which means that you interact with it through HTTP requests.

The following instructions explain what requests can be made through the API, while also documenting which classes they can be used with. 

## Authentication

In order to access the resources, all api requests need to be authenticated. This is done by first creating a user, which will return a bearer token that should be provided in all future requests.

To create a user, provide the user details (username and password) with:

`POST /api/auth/register`

To reauthenticate an already existing user, provide the user details (username and password) with:

`POST /api/auth/authenticate`

To authenticate a request, add the following header to the request:

    Authorization: Bearer <token>

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

    [{"id": 1,"username": "test","role": "USER"},{"id": 2,"username": "test2","role": "USER"}]

### Query Parameters

#### Post
Parameter      | Type          | Description
---------      | ----          | -----------
authorId       | Long          | Filter posts by id of author
authorUsername | String        | Filter posts by username of author
after          | DateTime      | Filter posts created after a timestamp
before         | DateTime      | Filter posts created before a timestamp

#### Review
Parameter      | Type          | Description
---------      | ----          | -----------
authorId       | Long          | Filter reviews by id of author
authorUsername | String        | Filter reviews by username of author
after          | DateTime      | Filter reviews created after a timestamp
before         | DateTime      | Filter reviews created before a timestamp

#### Comment
Parameter      | Type          | Description
---------      | ----          | -----------
authorId       | Long          | Filter comments by id of author
authorUsername | String        | Filter comments by username of author
after          | DateTime      | Filter comments created after a timestamp
before         | DateTime      | Filter comments created before a timestam

#### User
Parameter      | Type          | Description
---------      | ----          | -----------
username       | String        | Filter users by username

#### FriendRequest
Parameter    | Type     | Description
---------    | ----     | -----------
fromId       | Long     | Filter friend requests by id of sender
toId         | String   | Filter friend requests by id of receiver
fromUsername | String   | Filter friend requests by username of sender
toUsername   | String   | Filter friend requests by username of receiver 
after        | DateTime | Filter friend requests created after a time
before       | DateTime | Filter friend requests created before a time

#### Pub
Parameter     | Type     | Description
---------     | ----     | -----------
ownerId       | Long     | Filter pubs by id of owner
ownerUsername | String   | Filter pubs by username of owner
name          | String   | Filter pubs by name
openAfter     | String   | Filter pubs opening after timestamp
openBefore    | DateTime | Filter pubs opening before timestamp
closedAfter   | DateTime | Filter pubs closing after timestamp
closedBefore  | DateTime | Filter pubs closing before timestamp


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

### Relevant Entities
Post, Review, Comment, FriendRequest, Pub

### **NOTES**
* When creating a post, review or comment, the author of the resource is automatically set to the user that is currently authenticated.
* When creating a friend request, the sender of the request is automatically set to the user that is currently authenticated.
* When trying to create a resource containing an image, the request type should be multipart/form-data.


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