/open src/script/java/http.jsh

/open src/script/java/gson.jsh

public class Cat {
  public String name;
  public Cat mom;
  public Cat dad;
}

String from = "http://localhost:" + $1;
String to = "http://localhost:" + $2;

"Pulling Data From Source"

endpoint = from;
String serialized = get("/cat/list", client);
List<Cat> cats = gson.fromJson(serialized, new TypeToken<List<Cat>>(){}.getType());

"Pushing Data To Target"

endpoint = to;
List<CompletableFuture<?>> futures = new ArrayList<>(cats.size());
for ( Cat cat : cats ) {
  String name = cat.name;
  String mom = cat.mom == null ? null : cat.mom.name; 
  String dad = cat.dad == null ? null : cat.dad.name; 

  futures.add(cat(name, dad, mom)); 
}

CompletableFuture.allOf(
  futures.toArray(
    new CompletableFuture[futures.size()])).thenAccept(
      r -> System.out.println("Transfer Completed"));

/exit
