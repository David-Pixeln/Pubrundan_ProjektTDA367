## User Manual: "Pubrunda Applikationen"

## Introduction

This document serves to describe how to interact and make use of the Pubrunda API. The backend of this application functions as a REST API, which means that you interact with it through HTTP requests.

The following instructions explain what requests can be made through the API, while also documenting which classes they can be used with. 



## Get An Object

### Request

`GET /api/[resource]`

    curl -i -H 'Accept: application/json' http://localhost:7000/thing/

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

    []

### Relevant Entities
Post, Review, Comment, User, FriendRequest, Pub


### Get A List of Objects
-------------------------
#### Relevant Classes
Post, Review, Comment, User, FriendRequest, Pub

#### Request

> [Insert Code Here]

#### Parameters

The following tables describes what parameters you can filter with when getting a list of objects.

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


##### Response

> [Insert Code Here]



### Create an Object
-------------------------
#### Relevant Classes
Post, Review, Comment, User, FriendRequest, Pub

#### Request

> [Insert Code Here]


#### Response

> [Insert Code Here]



### Update an Object
---------------------
#### Relevant Classes
User, Pub

#### Request

> [Insert Code Here]


#### Response

> [Insert Code Here]



### Delete an Object
---------------------
#### Relevant Classes
Post, Review, Comment, User, FriendRequest, Pub

#### Request

> [Insert Code Here]


#### Response

> [Insert Code Here]


-----------

#### Authorization

>
