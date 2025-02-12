package utils;

import io.restassured.response.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponseValidator {

    private static final String LOCATION_PATTERN = "^http:\\/\\/\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}\\/(contexts|projects|todos)\\/(\\d+)$";

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        if (response.getStatusCode() != expectedStatusCode) {
            throw new AssertionError("Expected status code: " + expectedStatusCode + ", but got: " + response.getStatusCode());
        }
    }

    public static int extractIdFromLocation(Response response) {
        String responseUrl = response.getHeader("Location");
        Pattern pattern = Pattern.compile(LOCATION_PATTERN);
        Matcher matcher = pattern.matcher(responseUrl);

        if (matcher.matches()) {
            // Extract the last integer (resource ID)
            return Integer.parseInt(matcher.group(2));
        } else {
            throw new IllegalArgumentException("Invalid response URL format: " + responseUrl);
        }
    }

}
