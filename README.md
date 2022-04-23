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
	"title": "My First Contest!"
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
	"title": "some title",
	"description: "some description",
	"contestId": 5,
	"testCases": [
		"param1",
		"param2",
		"param3"
	],
	"testCaseOutcomes": [
		"outcome1",
		"outcome2",
		"outcome3"
	]
}
```

### GET

`/get-problem?id=X`: Returns a Problem object with `id=X` from the database, in the format shown above (minus testCases and testCaseOutcomes), as well as an HTTP status.

### POST

`/create-problem`: Returns the ID of the created Problem and an HTTP status. Body of the request must contain a Problem object formatted exactly as shown above.
