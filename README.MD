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


#### Create
```sh
curl -isd '{"name":"rson"}' -H "Content-Type: application/json" -X POST http://localhost:9090/api/foo
{"slug":"foo"}
```

#### If the slug (foo) is already used, new one is random generated
```sh
curl -isd '{"name":"rson"}' -H "Content-Type: application/json" -X POST http://localhost:9090/api/foo
{"slug":"2421a"}
```

#### Read
```sh
curl -is http://localhost:9090/api/foo
{"name":"rson"}
```

#### Update
```sh
curl -isd '{"name":"rason", "some":"more"}' -H "Content-Type: application/json" -X PUT http://localhost:9090/api/foo
{"slug":"foo"}

curl -is http://localhost:9090/api/foo
{"name":"rason", "some":"more"}
```

#### Delete
```sh
curl -is -H "Content-Type: application/json" -X DELETE http://localhost:9090/api/foo
{"slug":"foo"}

curl -is http://localhost:9090/api/foo
{"fault":"notfound"}
```

#### Heartbeat

```sh
curl -is http://localhost:9090/hb
{"payload":"beat_471741300"}
```

#### Stats
```sh
curl -is http://localhost:9090/stats
{"used":1,"max":2000,"maxLifeMinutes":1440}
```


## Tasks
- [x] CRUD on payload
- [x] CORS enabled
- [x] Stats
- [x] Live Demo @ https://rason.herokuapp.com/
- [ ] Bulk upload
- [ ] Frequently used JSON samples (eg: user, address, employee, department, student, books, teacher, blog, oauth token response)
- [ ] Permanently live JSON (eg: persist in DB + cache)
- [ ] Private JSON (requires authentication)
- [ ] UI to validate, format, compress JSON
- [ ] Support for simple logical expressions (eg: EQ, OR, AND)
- [ ] SSL support
- [ ] dynamic expiry
- [ ] payload version
- [ ] payload persistence