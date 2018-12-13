# RASON - json server

[![Build Status](https://travis-ci.org/anandchakru/rason.svg?branch=master)](https://travis-ci.org/anandchakru/rason)

Json Server to Cache json. Helps with mock/virtualize a REST service. Accessible across CORS.

## Live @ [here](https://rason.jrvite.com/index)

## How TO

#### Clone & Start
```sh
git clone https://github.com/anandchakru/rason.git
cd rason
mvn spring-boot:run
```


#### Create
```sh
curl -isd '{"name":"rson"}' -H "Content-Type: application/json" -H "Accepts: application/json" -X POST http://localhost:9090/api/firstbckt/foo

{"bucket":"firstbckt","slug":"foo"}

```

`firstbckt` is your bucket. `foo` is the slug which represents the current json `{"name":"rson"}` you saved.


If the slug `foo` is already used in `firstbckt` bucket, or slug length is more than 50 chars long, new slug is generated.
If the bucket is invalid or less than 3 characters long, or more than 10 characters long, default bucket `rnd` will be used.

```sh
curl -isd '{"name":"rson"}' -H "Content-Type: application/json" -H "Accepts: application/json" -X POST http://localhost:9090/api/firstbckt/foo

{"bucket":"firstbckt","slug":"3cee111"}
```

#### Read
```sh
curl -is http://localhost:9090/api/firstbckt/foo

{"name":"rson"}
```

#### Update
```sh
curl -isd '{"name":"rason", "some":"more"}' -H "Content-Type: application/json" -H "Accepts: application/json" -X PUT http://localhost:9090/api/firstbckt/foo

{"slug":"foo","bucket":"firstbckt"}

curl -is http://localhost:9090/api/firstbckt/foo

{"name":"rason", "some":"more"}
```

#### Delete
```sh
curl -is -H "Content-Type: application/json" -H "Accepts: application/json" -X DELETE http://localhost:9090/api/firstbckt/foo

{"slug":"foo","bucket":"firstbckt"}
```
Json removed, after delete. GET will return `{"fault":"notfound"}`
```sh
curl -is http://localhost:9090/api/firstbckt/foo

{"fault":"notfound"}

```

#### Bulk Uploads with slug
Request payload shall be in the form of `{{slug1:json1},{slug2:json1}..}`
```sh
curl -isd '{"slugone":{"test":"simpleJsonForTest"},"slugtwo":{"test2":"UpdatedJsonForTest"}}' -H "Content-Type: application/json" -H "Accepts: application/json" -X POST http://localhost:9090/api/bum/firstbckt

{"bucket":"firstbckt","slugs":{"slugone":"slugone","slugtwo":"slugtwo"}}
```
If the slug is already used, or slug length is more than 50, new random slug is generated. If 2001st element is inserted, oldest element will be removed.
```sh
curl -isd '{"slugone":{"test":"simpleJsonForTest"},"slugtwo":{"test2":"UpdatedJsonForTest"}}' -H "Content-Type: application/json" -H "Accepts: application/json" -X POST http://localhost:9090/api/bum/firstbckt

{"bucket":"firstbckt","slugs":{"slugone":"0f681d6","slugtwo":"5ec8518"}}
```

#### Bulk Uploads without slug
Data in the form of `[json1, json2..]`
```sh
curl -isd '[{"test":"simpleJsonForTest"},{"test2":"UpdatedJsonForTest"}]' -H "Content-Type: application/json" -H "Accepts: application/json" -X POST http://localhost:9090/api/bul/firstbckt

{"bucket":"firstbckt","slugs":{"5b0d7b3":"5b0d7b3","4aaa8d7":"4aaa8d7"}}
```
#### Heartbeat

```sh
curl -is http://localhost:9090/api/hb
{"payload":"beat_471741300"}
```

#### Stats
```sh
curl -is http://localhost:9090/api/stats/firstbckt
{"used":7,"max":2000,"maxLifeMinutes":14400}
```


## Tasks
- [x] CRUD on payload
- [x] CORS enabled
- [x] Stats
- [x] Live Demo @ [here](https://rason.jrvite.com/index)
- [x] Bulk upload
- [x] Random JSON
- [x] JsonVal, a wrapper for JsonNode
- [x] Caffeine cache
- [x] Shift Click on saveJSON for custom slug.
- [ ] Frequently used JSON samples
  * [ ] Oauth token response
  * [ ] Employee-Department
  * [ ] User-Address
  * [ ] Student-Teacher
  * [ ] Blog post
- [ ] Permanently live JSON (persist in DB + cache)
- [ ] Private JSON (use oauth/authslug/jwt?)
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
