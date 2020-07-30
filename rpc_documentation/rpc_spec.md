# Automarking JSON-RPC API specification

We use the JSON-RPC protocol version 2.0 to send automarkable
tasks to automarking servers.

## Methods

### `submit_task_for_automarking`

Submits a task to the marker, to be marked at some future time.
Returns a simple acknowledgment. The marking results can be retrieved
using the `retrieve_automarking_results` method described further below.

Example request:
```json
{
  "jsonrpc": "2.0",
  "method": "submit_task_for_automarking",
  "params": {
    "token": "+/Base64=",
    "studentCrsid": "hjp1",
    "uuid": "c14afc4e-d7f0-4f99-aca6-781cfd319bfd",
    "automarkableContents": "let square x = x * x;;"
  },
  "id": 1
}
```

Example response:
```json
{
  "jsonrpc": "2.0",
  "result": {},
  "id": 1
}
```

## `retrieve_automarking_results`

Retrieves all automarking results associated with a student CRSID.
Returns an object mapping task UUIDs to grades and optional comments.

Example request:
```json
{
  "jsonrpc": "2.0",
  "method": "retrieve_automarking_results",
  "params": {
    "token": "+/Base64=",
    "studentCrsid": "hjp1"
  },
  "id": 1
}
```

Example response:
```json
{
  "jsonrpc": "2.0",
  "result": {
    "c14afc4e-d7f0-4f99-aca6-781cfd319bfd": {
      "grade": 20,
      "comment": "Well done!"
    }
  },
  "id": 1
}
```