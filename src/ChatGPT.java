import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class ChatGPT {

    private final String apiKey;
    private final Gson gson = new Gson();

    public ChatGPT(String apiKey) {
        this.apiKey = apiKey;
    }

    public String enviarParaIA(String texto) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String body = """
                {
                    "model": "gpt-4o-mini",
                    "messages": [
                        {"role": "user", "content": "%s"}
                    ]
                }
                """.formatted(texto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return extrairResposta(response.body());
    }

    private String extrairResposta(String json) {
        try {
            JsonObject obj = gson.fromJson(json, JsonObject.class);
            JsonArray choices = obj.getAsJsonArray("choices");
            if (choices.size() > 0) {
                JsonObject message = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                return message.get("content").getAsString();
            }
            return "Resposta vazia da IA.";
        } catch (Exception e) {
            return "Erro ao interpretar resposta: " + e.getMessage();
        }
    }
}
