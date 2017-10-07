curl localhost:8080/cat -d '{ "name" : "Billy" }'
curl localhost:8080/cat -d '{ "name" : "Bobby" }'
curl localhost:8080/cat -d '{ "name" : "Bob" }'
curl localhost:8080/cat -d '{ "name" : "Bobkins" }'
curl localhost:8080/cat -d '{ "name" : "Boberell" }'
curl localhost:8080/cat -d '{ "name" : "Foo" }'
curl localhost:8080/cat -d '{ "name" : "Phyllis" }'
curl localhost:8080/cat -d '{ "name" : "Harris" }'

curl localhost:8080/mom/Phyllis/dad/Harris/cat -d '{ "name" : "Tom" }'
curl localhost:8080/mom/Phyllis/dad/Harris/cat -d '{ "name" : "Tommy" }'
curl localhost:8080/mom/Phyllis/dad/Harris/cat -d '{ "name" : "Thomas" }'
curl localhost:8080/mom/Phyllis/dad/Harris/cat -d '{ "name" : "Tomtom" }'

curl localhost:8080/mom/Foo/dad/Boberell/cat -d '{ "name" : "Super" }'
curl localhost:8080/mom/Foo/dad/Boberell/cat -d '{ "name" : "Supper" }'
curl localhost:8080/mom/Foo/dad/Bobkins/cat -d '{ "name" : "Duper" }'
curl localhost:8080/mom/Foo/dad/Bob/cat -d '{ "name" : "Dapper" }'

