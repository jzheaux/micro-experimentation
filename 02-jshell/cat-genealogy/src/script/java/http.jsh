/env --add-module jdk.incubator.http

import jdk.incubator.http.*;

HttpClient client = HttpClient.newHttpClient();

String getAsString(String url) {
  HttpRequest request = HttpRequest.newBuilder()
  	.uri(new URI(url))
	.build();

  HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
  return response.body();
}

String post
