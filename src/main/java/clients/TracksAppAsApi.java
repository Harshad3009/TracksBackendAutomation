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

    public Response createUser(String username, String password) {

        fetchSingupPage();

        Response response = given().
                log().all().
                baseUri(baseUrl).
                auth().preemptive().basic(adminUsername,adminPassword).
                cookies(sessionCookies).
                contentType("application/x-www-form-urlencoded").
                formParam("utf8","%E2%9C%93").
                formParam("authenticity_token", authenticity_token).
                formParam("user[login]", username).
                formParam("user[password]", password).
                formParam("user[password_confirmation]", password).
                when().
                post("/users").
                andReturn();

        if(response.getStatusCode() != 302) {
            throw new RuntimeException("Failed to create user: " + response.getStatusCode());
        }

        return response;
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

    public String getAuthenticity_token() {
        return authenticity_token;
    }

}
