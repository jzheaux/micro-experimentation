/env --add-module jdk.incubator.httpclient

import jdk.incubator.http.*;
import java.net.*;

HttpClient client = HttpClient.newHttpClient();
String defaultContentType = "application/json";

String getAsString(String url) throws Exception {
  HttpRequest request = HttpRequest.newBuilder(new URI(url))
	.build();

  return client.send(request, HttpResponse.BodyHandler.asString()).body();
}

HttpRequest preparePost(String url, String body) throws Exception {
  return HttpRequest.newBuilder(new URI(url))
        .header("Content-Type", defaultContentType)
        .POST(HttpRequest.BodyProcessor.fromString(body))
        .build();
}

String postAsString(String url, String body) throws Exception {
  HttpRequest request = preparePost(url, body);

  return client.send(request, HttpResponse.BodyHandler.asString()).body();
}

CompletableFuture<HttpResponse<String>> postAsyncAsString(String url, String body) throws Exception {
  HttpRequest request = preparePost(url, body);

  return client.postAsync(response, HttpResponse.BodyHandler.asString());
}
