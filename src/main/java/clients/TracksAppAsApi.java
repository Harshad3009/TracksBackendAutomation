package clients;

import io.restassured.http.Cookies;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import utils.AuthHelper;

import static io.restassured.RestAssured.given;

public class TracksAppAsApi {

    private final String baseUrl;
    private final String adminUsername;
    private final String adminPassword;
    private final AuthHelper authHelper;

    public TracksAppAsApi(String baseUrl, String adminUsername, String adminPassword) {
        this.baseUrl = baseUrl;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.authHelper = new AuthHelper(adminUsername, adminPassword);
    }

    public Response fetchSignupPage() {
        Response response = given().
                baseUri(baseUrl).
                auth().preemptive().basic(adminUsername,adminPassword).
                when().
                get("/signup");

        if(response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to fetch signup page: " + response.getStatusCode());
        }

        return response;

    }

    public Response createUser(String username, String password) {

        Response response = given().
                log().all().
                baseUri(baseUrl).
                auth().preemptive().basic(adminUsername,adminPassword).
                cookies(authHelper.getSessionCookies()).
                contentType("application/x-www-form-urlencoded").
                formParam("utf8","%E2%9C%93").
                formParam("authenticity_token", authHelper.getAuthenticityToken()).
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

    public AuthHelper getAuthorisation() {
        return authHelper;
    }

}
