import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class po {
    public static void main(String[] args) {
        String url = "https://dummyjson.com/products";
        String consId = "1234567";
        String userKey = "faY738sH";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Cons_ID", consId);
            connection.setRequestProperty("user_key", userKey);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray productsArray = jsonObject.getJSONArray("products");

                JSONObject[] products = new JSONObject[productsArray.length()];
                for (int i = 0; i < productsArray.length(); i++) {
                    products[i] = productsArray.getJSONObject(i);
                }

                int n = products.length;
                for (int i = 0; i < n - 1; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < n; j++) {
                        double rating1 = products[j].getDouble("rating");
                        double rating2 = products[minIndex].getDouble("rating");
                        if (rating1 < rating2) {
                            minIndex = j;
                        }
                    }
                    if (minIndex != i) {
                        JSONObject temp = products[i];
                        products[i] = products[minIndex];
                        products[minIndex] = temp;
                    }
                }
                
                for (int i = 0; i < n; i++) {
                    JSONObject product = products[i];
                    System.out.println("Product ID: " + product.getInt("id"));
                    System.out.println("Product Title: " + product.getString("title"));
                    System.out.println("Rating: " + product.getDouble("rating"));
                    System.out.println("                                                    ");
                }
            }
        } catch(IOException | JSONException e){
            e.printStackTrace();
    }
}
}
