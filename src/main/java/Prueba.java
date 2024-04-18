
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author diegobecerril
 */
public class Prueba {

    private static final String apiKey = "https://v6.exchangerate-api.com/v6/1907c772ff6d84acfefcfd96/latest/USD";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiKey)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String jsonString = response.body();
        
        //System.out.println("jsonString = " + jsonString);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(jsonString);
        var conversion_rates = node.get("conversion_rates");
        
        conversion_rates.fields().forEachRemaining(entry -> {
            String key = entry.getKey();
            double rate = entry.getValue().asDouble();
            System.out.println(key+ " : " + rate);
        });
    }
}
