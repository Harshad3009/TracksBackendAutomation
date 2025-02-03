package clients;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ApiClient {

    private final String baseUrl;
    private final String username;
    private final String password;
    private final CookieStore cookieStore;

    public ApiClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.cookieStore = new BasicCookieStore();
    }

    public Response sendPostResponse(String endpoint, String body) {
        return  given().
                baseUri(baseUrl).
                auth().preemptive().basic(username, password).
                contentType(ContentType.XML).
                body(body).
                when().
                post(endpoint).
                then().
                extract().
                response();
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
