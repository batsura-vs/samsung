package homeworks.task1;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Task1 {
    private static final String API_KEY = "pdct.1.1.20240203T080955Z.e650ecc669ff9e02.fef44dd98a22acfcfd3d8593e272780f9d7c1a65";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://predictor.yandex.net/api/v1/predict.json/complete?key=" + API_KEY + "&q=" + URLEncoder.encode(data, StandardCharsets.UTF_8) + "&lang=ru&limit=5"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ResponseDTO res = gson.fromJson(response.body(), ResponseDTO.class);
            System.out.println(String.join("\n", res.getText()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
