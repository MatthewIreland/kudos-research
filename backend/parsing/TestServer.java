package parsing;

import com.sun.net.httpserver.HttpServer;

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
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))) {
                System.out.println(reader.lines().collect(Collectors.joining()));
            }

            String response = "{\"hello\": \"world\"}";
            httpExchange.sendResponseHeaders(200, response.length());

            try (OutputStream responseBody = httpExchange.getResponseBody()) {
                responseBody.write(response.getBytes(StandardCharsets.UTF_8));
            }
        });

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
}
