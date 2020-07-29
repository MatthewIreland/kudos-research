package parsing;

import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

// Just a very basic HTTP server to try out RPC requests and responses.
public class TestServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        server.createContext("/marker/ocaml", httpExchange -> {
            String requestString;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))) {
                requestString = reader.lines().collect(Collectors.joining());
            }
            System.out.println(requestString);

            JSONObject request = new JSONObject(requestString);

            JSONObject result = new JSONObject();
            result.put("grade", 20);
            result.put("message", "Hello world");

            JSONObject response = new JSONObject();
            response.put("jsonrpc", "2.0");
            response.put("id", request.get("id"));
            response.put("result", result);

            String responseString = response.toString();
            System.out.println(responseString);
            httpExchange.sendResponseHeaders(200, responseString.length());

            try (OutputStream responseBody = httpExchange.getResponseBody()) {
                responseBody.write(responseString.getBytes(StandardCharsets.UTF_8));
            }
        });

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
}
