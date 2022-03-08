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

`/create-user`: Returns an HTTP status. Body of the request must contain a User object as shown above.

## Contests

### JSON Object Structure

```
{
	"title": "My First Contest!",
	"users": [
		1, //user IDs
		2,
		3,
		4  //...
	],
	"problems": [
		1, //problem IDs
		2,
		3,
		4  //...
	]
}
```
