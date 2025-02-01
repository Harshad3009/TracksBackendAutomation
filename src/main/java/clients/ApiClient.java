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
    private final CookieStore cookieStore;
    private final CloseableHttpClient httpClient;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.cookieStore = new BasicCookieStore();
        this.httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }

    public Response createUser(String xmlRequest) {
        return  given().
                baseUri(baseUrl).
                contentType(ContentType.XML).
                body(xmlRequest).
                when().
                post("/users.xml").
                then().
                extract().
                response();
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
