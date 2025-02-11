package clients;

import io.restassured.RestAssured;
import io.restassured.config.SessionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookies;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import utils.AuthHelper;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static io.restassured.config.CsrfConfig.csrfConfig;

public class TracksAppAsApi {

    private final String baseUrl;
    private final String adminUsername;
    private final String adminPassword;
    private final AuthHelper authHelper;

    private final Logger LOGGER = Logger.getLogger(TracksAppAsApi.class.getName());

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

        LOGGER.info("Response: " + response.asString());

        if(response.getStatusCode() != 302) {
            throw new RuntimeException("Failed to create user: " + response.getStatusCode());
        }

        return response;
    }

    public Response createUserWithCsrf(String username, String password) {

        RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("_tracksapp_session"));
        RestAssured.config = RestAssured.config().csrfConfig(csrfConfig().with().csrfInputFieldName("authenticity_token"));
        SessionFilter filter = new SessionFilter();

        Response response = given().
                csrf("/signup"). // -> Alternative mechanism to fetch CSRF
//        auth().form("testUser2", "testUser2", new FormAuthConfig("/users", "user[login]", "user[password]")
//                                .withAdditionalField("authenticity_token") // -> use this
////                                .withAutoDetectionOfCsrf() -> Deprecated
//                ).
        auth().preemptive().basic(adminUsername,adminPassword).
                cookies(authHelper.getSessionCookies()).
                contentType("application/x-www-form-urlencoded").
                formParam("utf8","%E2%9C%93").
                formParam("user[login]", username).
                formParam("user[password]", password).
                formParam("user[password_confirmation]", password).
                filter(filter).
                log().all().
                when().
                post("/users").
                andReturn();

        System.out.println("Session id = " + filter.getSessionId());

        LOGGER.info("Response: " + response.asString());

        if(response.getStatusCode() != 302) {
            throw new RuntimeException("Failed to create user: " + response.getStatusCode());
        }

        return response;

    }

    public AuthHelper getAuthorisation() {
        return authHelper;
    }

}
