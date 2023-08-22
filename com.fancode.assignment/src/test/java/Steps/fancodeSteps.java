package Steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fancodeSteps {

    Response response;

    public void getUsersAPI() throws JsonProcessingException {
        RequestSpecification spec = RestAssured.given().log().all();
        spec.baseUri("http://jsonplaceholder.typicode.com");
        response = spec.get("/users");
        response.prettyPrint();
    }

    public List<String> getUsersBelongToFancodeCity() {
        List<String> id = new ArrayList<>();
        List<String> lat = new ArrayList<>();
        List<String> lng = new ArrayList<>();
        JsonPath js = response.jsonPath();
        for (int i = 0; i < 10; i++) {
            Double value = Double.valueOf(js.getString("[" + i + "].address.geo.lat"));
            Double value1 = Double.valueOf(js.getString("[" + i + "].address.geo.lng"));
            if (value >= -40 && value <= 5 && value1 >= 5 && value1 <= 100) {
                lat.add(String.valueOf(value));
                lng.add(String.valueOf(value1));
                id.add(js.getString("[" + i + "].id"));
            }
        }
        System.out.println("lat : " + lat);
        System.out.println("lng : " + lng);
        System.out.println("id : " + id);
        return id;
    }

    public void getTodosAPI() throws JsonProcessingException {
        RequestSpecification spec = RestAssured.given().log().all();
        spec.baseUri("http://jsonplaceholder.typicode.com");
        response = spec.get("/todos");
        response.prettyPrint();
    }

    public void checkCompletedTaskPercentage(List<String> users) {
        String res = response.getBody().asString();
        JSONArray jsonArray = new JSONArray(res);

        // Calculate completion percentage for each user
        int[] completedCountByUserId = new int[12]; // Assuming user IDs are in the range 1-11
        int[] totalCountByUserId = new int[12];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int userId = jsonObject.getInt("userId");
            boolean completed = jsonObject.getBoolean("completed");
            totalCountByUserId[userId]++;
            if (completed) {
                completedCountByUserId[userId]++;
            }
        }
        int[] userIDsToCheck = new int[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userIDsToCheck[i] = Integer.parseInt(users.get(i));
        }
        for (int userId : userIDsToCheck) {
            int completedCount = completedCountByUserId[userId];
            int totalCount = totalCountByUserId[userId];
            double completionPercentage = ((double) completedCount / totalCount) * 100;
            if (completionPercentage > 50) {
                System.out.println("User ID " + userId + " has completion percentage more than 50%.");
            } else {
                System.out.println("User ID " + userId + " does not have completion percentage more than 50%.");
            }
        }
    }
}
