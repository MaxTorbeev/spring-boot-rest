# TODO

## Api scheme
Api method must return JSON scheme by template:
```json
{
  "status" : 200,
  "type": "about:blank",
  "title": "Response title",
  "data": "Response data"
}
```
- [ ] Create ApiMessage model class
- If response returned with error or with many problems, use this api [problem scheme](link=https://datatracker.ietf.org/doc/html/draft-ietf-appsawg-http-problem-00)
- [ ] Create ApiProblem model class