package clients;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ApiClient {

    private final String baseUrl;
    private String username;
    private String password;
    private final Cookies cookieStore;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.cookieStore = new Cookies();
    }

    public void basicAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Response sendPostResponse(String endpoint, String body) {
        return  given().
                baseUri(baseUrl).log().all().
                auth().preemptive().basic(username, password).
                contentType(ContentType.XML).
                body(body).
                when().
                post(endpoint).
                then().log().all().
                extract().
                response();
    }

    public Cookies getCookieStore() {
        return cookieStore;
    }
}
