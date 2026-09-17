# Post Controller

## Endpoints

### Create a post

`POST /api/v1/posts`

Request body:

```json
{
  "content": "Hello world"
}
```

Returns `201 Created`, a `Location: /api/v1/posts/{id}` header, and the created post:

```json
{
  "id": 1,
  "content": "Hello world",
  "likes": 0
}
```

### Like a post

`POST /api/v1/posts/{id}/like`

Returns `200 OK` and the updated post. An unknown ID returns `404 Not Found`.

```json
{
  "id": 1,
  "content": "Hello world",
  "likes": 1
}
```

## Notes

Posts are stored in memory, so they are reset whenever the application restarts.
