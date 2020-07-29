package parsing;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// Just a very basic server to try out RPC requests and responses.
public class TestServer {

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String requestString = in.readLine();

                    JSONObject request = new JSONObject(requestString);

                    JSONObject result = new JSONObject();
                    result.put("grade", 20);
                    result.put("message", "Hello world");

                    JSONObject response = new JSONObject();
                    response.put("jsonrpc", "2.0");
                    response.put("id", request.get("id"));
                    response.put("result", result);

                    String responseString = response.toString();

                    out.println(responseString);
                }
            }
        }
    }
}
