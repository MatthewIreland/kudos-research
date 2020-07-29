# Automarking JSON-RPC API specification

We use the JSON-RPC protocol version 2.0 over HTTP(S) POST requests
to send automarkable work to automarking servers.

## Methods

### `automark`

Automatically marks the work provided in the parameters, and returns
the grade assigned to the work, and an optional message for comments.

Request `params` format:
```
{
  "studentCrsid": string,
  "uuid": string,
  "automarkableContents": string
}
```

Response `result` format:
```
{
  "grade": integer,
  "message": opt string
}
```