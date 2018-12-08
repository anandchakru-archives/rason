# RASON - json server

Json Server to Cache json. Helps with mock/virtualize a REST service. Accessible across CORS.

## Live @ https://rason.herokuapp.com/

## How TO

#### Clone & Start
```sh
git clone https://github.com/anandchakru/rason.git
cd rason
mvn spring-boot:run
```

#### Keys
```sh
curl -isX GET http://localhost:9090/api/keys
Set-Cookie: JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly
[]
```
Note the Set-Cookie for sessionId

#### Create
```sh
curl -isd '{"name":"rson"} --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" -H "Content-Type: application/json" -X POST http://localhost:9090/api/foo

{"slug":"foo"}
```

If the slug (foo) is already used, or slug length is more than 50 chars long, new random slug is generated
```sh
curl -isd '{"name":"rson"}' --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" -H "Content-Type: application/json" -X POST http://localhost:9090/api/foo

{"slug":"2421a"}
```

#### Read
```sh
curl -is --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" http://localhost:9090/api/foo

{"name":"rson"}
```

#### Update
```sh
curl -isd '{"name":"rason", "some":"more"}' --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" -H "Content-Type: application/json" -X PUT http://localhost:9090/api/foo

{"slug":"foo"}

curl -is http://localhost:9090/api/foo

{"name":"rason", "some":"more"}
```

#### Delete
```sh
curl -is -H "Content-Type: application/json" --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" -X DELETE http://localhost:9090/api/foo

{"slug":"foo"}
```
Json removed, after delete. GET will return `{"fault":"notfound"}`
```sh
curl -is --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" http://localhost:9090/api/foo

{"fault":"notfound"}
```
#### Bulk Uploads with slug
Request payload shall be in the form of `{{slug1:json1},{slug2:json1}..}`
```sh
curl -isd '{"slugone":{"test":"simpleJsonForTest"},"slugtwo":{"test2":"UpdatedJsonForTest"}}' --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" -H "Content-Type: application/json" -X POST http://localhost:9090/bum

{"payload":{"slugone":{"slug":"slugone"},"slugtwo":{"slug":"slugtwo"}}}
```
If the slug is already used, or slug length is more than 50, new random slug is generated. 
```sh
curl -isd '{"slugone":{"test":"simpleJsonForTest"},"slugtwo":{"test2":"UpdatedJsonForTest"}}' --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" -H "Content-Type: application/json" -X POST http://localhost:9090/bum

{"payload":{"slugone":{"slug":"481da"},"slugone":{"slug":"fd81a"}}}
```
#### Bulk Uploads without slug
Data in the form of `[json1, json2..]`
```sh
curl -isd '[{"test":"simpleJsonForTest"},{"test2":"UpdatedJsonForTest"}]' --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" -H "Content-Type: application/json" -X POST http://localhost:9090/bul

{"payload":{"ddc4b":{"slug":"ddc4b"},"9e96c":{"slug":"9e96c"}}}
```
#### Heartbeat

```sh
curl -is http://localhost:9090/hb
{"payload":"beat_471741300"}
```

#### Stats
```sh
curl -is --cookie "JSESSIONID=A1F1C30051E912337B379FB6B3EACAE1; Path=/; HttpOnly" http://localhost:9090/stats
{"used":1,"max":2000,"maxLifeMinutes":1440}
```

## Tasks
- [x] CRUD on payload
- [x] CORS enabled
- [x] Stats
- [x] Live Demo @ https://rason.herokuapp.com/
- [x] Bulk upload
- [x] Random JSON
- [x] Counter based StringKey and JsonVal, a wrapper for JsonNode
- [x] Caffeine cache
- [x] Shift Click on saveJSON for custom slug.
- [ ] Frequently used JSON samples
  * [ ] Oauth token response
  * [ ] Employee-Department
  * [ ] User-Address
  * [ ] Student-Teacher
  * [ ] Blog post
- [ ] UI to validate, format, compress JSON
- [ ] Support for simple logical expressions (eg: EQ, OR, AND)
- [ ] SSL support
- [ ] dynamic expiry
- [ ] Payload version

## Ambitious Tasks
- [ ] Access-Control-Allow-Origin as * instead of hosted website
- [ ] Dynamic expiry time for data
- [ ] Permanent data (mysql/firebase + cache)
- [ ] Private data (use oauth/authslug/jwt?)