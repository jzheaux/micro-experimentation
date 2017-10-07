/env --add-module jdk.incubator.httpclient

import jdk.incubator.http.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;

HttpClient client = HttpClient.newBuilder().executor(Executors.newFixedThreadPool(1)).build();

String endpoint = "http://localhost:8080";

String get(String path, HttpClient client) {
  try {
    HttpRequest request = HttpRequest.newBuilder(
      new URI(endpoint + path))
           .build();

    return
            client.send(request, HttpResponse.BodyHandler.asString())
               .body();
  } catch ( Exception e ) {
    throw new RuntimeException(e);
  }
}

CompletableFuture<?> postAsync(String path, String body, HttpClient client) {
  try {
    HttpRequest request = HttpRequest.newBuilder(
          new URI(endpoint + path))
          .POST(HttpRequest.BodyProcessor.fromString(body))
          .build();

    return client.sendAsync(request, HttpResponse.BodyHandler.asString());
  } catch ( Exception e ) {
    throw new RuntimeException(e);
  }
}

String ls() {
  return get("/cat/list", client);
}

String count() {
  return get("/cat/count", client);
}
 
HttpClient addCatsClient = HttpClient.newBuilder().executor(Executors.newFixedThreadPool(1)).build();

CompletableFuture<?> add(String name, String dad, String mom) {
  return postAsync(
              String.format("/mom/%s/dad/%s/cat", mom, dad),
              String.format("{ 'name' : '%s' }", name),
              addCatsClient);
}

CompletableFuture<?> addMany(int howMany) {
  CompletableFuture[] futures = new CompletableFuture[howMany - 1];
  for ( int i = howMany - 1; i > 0; i-- ) {
    String cat = "Cat" + i;
    String dad = "Cat" + 2*i;
    String mom = "Cat" + ( 2*i + 1 );
    futures[howMany - i - 1] = add(cat, dad, mom);
  }

  return CompletableFuture.allOf(futures)
           .thenAccept(r -> System.out.println("Added " + (howMany - 1) + " cats"));
}
