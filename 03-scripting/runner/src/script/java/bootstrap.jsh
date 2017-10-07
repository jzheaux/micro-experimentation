"Opening http library"

/open src/script/java/http.jsh

"Inserting Orphans"

orphan("Billy")
orphan("Bobby")
orphan("Bob")
orphan("Bobkins")
orphan("Boberell")
orphan("Foo")
orphan("Phyllis")
orphan("Harris")

"Inserting Children"

cat("Tom", "Harris", "Phyllis")
cat("Tommy", "Harris", "Phyllis")
cat("Thomas", "Harris", "Phyllis")
cat("Tomtom", "Harris", "Phyllis")

"Inserting Half-Brothers"

cat("Super", "Boberell", "Foo")
cat("Supper", "Boberell", "Foo")
cat("Dooper", "Bobby", "Foo")
cat("Duper", "Bobkins", "Foo")


/exit
