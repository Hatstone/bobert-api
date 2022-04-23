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

`/bobert-api/get-user?id=X`: Returns a User object with `id=X` from the database, in the format shown above, as well as an HTTP status.

### POST

`/bobert-api/create-user`: Returns the ID of the created User and an HTTP status. Body of the request must contain a User object as shown above.

## Contests

### JSON Object Structure

```
{
	"contest": {
		"id": 1,
		"title": "some contest title"
	},
	"isAdmin": false
}
```

### GET

`/bobert-api/get-contest?id=X`: Returns a Contest object with `id=X` from the database, in the format shown above, as well as an HTTP status.

`/bobert-api/get-usercontests?id=X`: Returns a list of Contest objects where the User with `id=X` is a participant as a list of JSON objects in the format shown above, as well as an HTTP status.

### POST

`/bobert-api/create-contest?title=X`: Returns the ID of the created Contest with `title=X` and an HTTP status.

`/bobert-api/join-contest?cid=X&uid=Y`: Adds the given User with `uid=Y` to the participant list of the given Contest with `cid=X` and returns an HTTP status.

## Problems

### JSON Object Structure

```
{
	"title": "some title",
	"description: "some description",
	"contestId": 1,
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

`/bobert-api/get-problem?id=X`: Returns a Problem object with `id=X` from the database, in the format shown above (minus testCases and testCaseOutcomes), as well as an HTTP status.

`/bobert-api/get-contestproblems?id=X`: Returns a list of Problem objects (minus testCases and testCaseOutcomes) that belong to a Contest with `id=X`, as well as an HTTP status.

### POST

`/bobert-api/create-problem`: Returns the ID of the created Problem and an HTTP status. Body of the request must contain a Problem object formatted exactly as shown above.

## Submissions

### JSON Object Structure

```
{
	"userId": 1,
	"problemId": 1,
	"data": [...], // the user's submitted code is placed here as a string
	"language": "python"
}
```

### GET

`/bobert-api/get-submission?id=X`: Returns a Submission object with `id=X` from the database, in the format shown above, as well as an HTTP status.

`/bobert-api/get-userproblemsubmissions?uid=X&pid=Y`: Returns a list of Submission objects from the User with `uid=X` that correspond to the Problem with `pid=Y`, each in the format shown above, as well as an HTTP status.

### POST

`/bobert-api/create-submission`: Returns the ID of the created Submission, the portion of the test cases passed, and an HTTP status. Body of the request must contain a Submission object formatted as shown above. Runs the submitted code in our containerized code runner with all Problem-relevant test case arguments and checks against the corresponding expected outputs.
