# bobert-api
The API for Bobert, our programming contest platform

# Endpoints

## Users

### JSON Object Structure

```
{
	"displayName": "ExampleDisplayName1",
	"firstName": "Adam",
	"lastName": "Adamson",
	"email": "adam@gmail.com",
	"password": "plaintextpassw0rdExample", //to be secured later
	"admin": false
}
```

### GET

`/get-user?id=X`: Returns a User object with `id=X` from the database, in the format shown above, as well as an HTTP status.

### POST

`/create-user`: Returns the ID of the created User and an HTTP status. Body of the request must contain a User object as shown above.

## Contests

### JSON Object Structure

```
{
	"title": "My First Contest!",
	"users": [
		1, //user IDs, may be updated to be full User objects later by frontend request
		2,
		3,
		4  //...
	],
	"problems": [
		1, //problem IDs, may be updated to be full Problem objects later by frontend request
		2,
		3,
		4  //...
	]
}
```

### GET

`/get-contest?id=X`: Returns a Contest object with `id=X` from the database, in the format shown above, as well as an HTTP status.

### POST

`/create-contest?title=X`: Returns the ID of the created Contest with `title=X` and an HTTP status.

## Problems

### JSON Object Structure

```
{
	"language": "example programming language",
	"sourceCode": "print('hello, world!')", //we still need to figure out how we're handling this, likely a reference to an instruction PDF?
	"inputCode": "print('hello, world 2!')", //same deal as above
	"timeLimit": 300, //time limit for the problem in seconds
	"memoryLimit": 1000, //memory limit for the problem in MB
	"status": "open"
}
```

### GET

`/get-problem?id=X`: Returns a Problem object with `id=X` from the database, in the format shown above, as well as an HTTP status.

### POST

`/create-problem`: Returns the ID of the created Problem and an HTTP status. Body of the request must contain a Problem object as shown above.
