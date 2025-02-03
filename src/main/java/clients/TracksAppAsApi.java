package clients;

import io.restassured.http.Cookies;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TracksAppAsApi {

    private final String baseUrl;
    private final String adminUsername;
    private final String adminPassword;
    private Cookies sessionCookies;
    private String authenticity_token;

    public TracksAppAsApi(String baseUrl, String adminUsername, String adminPassword) {
        this.baseUrl = baseUrl;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public void fetchSingupPage() {
        Response response = given().
                baseUri(baseUrl).
                auth().preemptive().basic(adminUsername,adminPassword).
                when().
                get("/signup");

        if(response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to fetch signup page: " + response.getStatusCode());
        }

        // Getting authenticity_token from the login page html
        authenticity_token = getAuthenticityTokenFromResponse(response);
        if(authenticity_token == null || authenticity_token.isEmpty()) {
            throw new RuntimeException("Failed to fetch authenticity_token from signup page");
        }

        // Storing cookies from login page
        sessionCookies = response.getDetailedCookies();

    }

    private String getAuthenticityTokenFromResponse(Response response) {
        XmlPath htmlParser = response.body().htmlPath();
        String auth_token_path =
                "**.find {it.@name =='authenticity_token'}.@value";
        return htmlParser.get(auth_token_path);
    }

    public Cookies getSessionCookies() {
        return sessionCookies;
    }

}
