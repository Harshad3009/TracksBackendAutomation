package utils;

import clients.TracksAppAsApi;
import config.ConfigManager;
import io.restassured.http.Cookies;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthHelper {

    private String authenticityToken;
    private Cookies sessionCookies;
    private String adminUsername;
    private String adminPassword;

    public AuthHelper(String username, String password) {
        this.adminUsername = username;
        this.adminPassword = password;

        Response response = given().
                baseUri(ConfigManager.get("base.url")).
                auth().preemptive().basic(adminUsername,adminPassword).
                when().
                get("/signup");

        // Getting authenticity_token from the login page html
        authenticityToken = extractAuthenticityTokenFromResponse(response);
        if(authenticityToken == null || authenticityToken.isEmpty()) {
            throw new RuntimeException("Failed to fetch authenticity_token from signup page");
        }

        // Storing cookies from login page
        sessionCookies = response.getDetailedCookies();
    }

    public AuthHelper(Response signUpPageResponse) {
        // Getting authenticity_token from the login page html
        authenticityToken = extractAuthenticityTokenFromResponse(signUpPageResponse);
        if(authenticityToken == null || authenticityToken.isEmpty()) {
            throw new RuntimeException("Failed to fetch authenticity_token from signup page");
        }

        // Storing cookies from login page
        sessionCookies = signUpPageResponse.getDetailedCookies();
    }

    private String extractAuthenticityTokenFromResponse(Response response) {
        XmlPath htmlParser = response.body().htmlPath();
        String auth_token_path =
                "**.find {it.@name =='authenticity_token'}.@value";
        return htmlParser.get(auth_token_path);
    }

    public Cookies getSessionCookies() {
        return sessionCookies;
    }

    public String getAuthenticityToken() {
        return authenticityToken;
    }
}
