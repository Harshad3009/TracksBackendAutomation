package tracksTests.tracksAppAsApiTests;

import base.BaseTest;
import clients.TracksApi;
import clients.TracksAppAsApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.AuthHelper;
import utils.ResponseValidator;

import java.util.Random;

public class TracksAppAsApiTests extends BaseTest {

    private static TracksAppAsApi tracksAppAsApi;

    @BeforeClass
    public static void setupTracksAppAsApi() {
        tracksAppAsApi = new TracksAppAsApi(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    @Test
    public void fetchSignupPageTest() {
        Response signUpPageResponse = tracksAppAsApi.fetchSignupPage();
        AuthHelper authHelper = new AuthHelper(signUpPageResponse);
        SoftAssert softAssert = new SoftAssert();

        // validate authenticity_token is fetched and stored
        softAssert.assertNotNull(authHelper.getAuthenticityToken(), "authenticity_token should not be null");

        // Validate cookies are stored
        softAssert.assertNotNull(authHelper.getSessionCookies());
        softAssert.assertFalse(authHelper.getSessionCookies().asList().isEmpty());

        softAssert.assertAll();

    }

    @Test
    public void createUserWithCsrfTokenTest() {
        Response response = tracksAppAsApi.createUserWithCsrf("testUser" + new Random().nextInt(), "password" + new Random().nextInt());

        Assert.assertEquals(response.getStatusCode(), 302);
        Assert.assertEquals((response.getHeader("Location")), BASE_URL);
    }

    @Test
    public void createUserTest() {
        Response response = tracksAppAsApi.createUser("testUser" + new Random().nextInt(), "password" + new Random().nextInt());

        Assert.assertEquals(response.getStatusCode(), 302);
        Assert.assertEquals((response.getHeader("Location")), BASE_URL);
    }

    @Test
    public void createUserAndAddContextTest() {

        String userName = "testUser" + new Random().nextInt();
        String password = "password" + new Random().nextInt();
        Response response = tracksAppAsApi.createUser(userName, password);

        Assert.assertEquals(response.getStatusCode(), 302);
        Assert.assertEquals((response.getHeader("Location")), BASE_URL);

        TracksApi tracksApi = new TracksApi(userName, password);
        response = tracksApi.createContext("testContext");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getHeader("Location").contains("/contexts/"));
    }

    @Test
    public void createUserAndAddProjectTest() {

        String userName = "testUser" + new Random().nextInt();
        String password = "password" + new Random().nextInt();
        Response response = tracksAppAsApi.createUser(userName, password);

        Assert.assertEquals(response.getStatusCode(), 302);
        Assert.assertEquals((response.getHeader("Location")), BASE_URL);

        TracksApi tracksApi = new TracksApi(userName, password);
        response = tracksApi.createProject("testProject");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getHeader("Location").contains("/projects/"));
    }

    @Test
    public void createUserAndAddTaskTest() {

        String userName = "testUser" + new Random().nextInt();
        String password = "password" + new Random().nextInt();
        Response response = tracksAppAsApi.createUser(userName, password);

        Assert.assertEquals(response.getStatusCode(), 302);
        Assert.assertEquals((response.getHeader("Location")), BASE_URL);

        TracksApi tracksApi = new TracksApi(userName, password);

        response = tracksApi.createContext("testContext");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getHeader("Location").contains("/contexts/"));
        int contextId = ResponseValidator.extractIdFromLocation(response);

        response = tracksApi.createProject("testProject");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getHeader("Location").contains("/projects/"));
        int projectId = ResponseValidator.extractIdFromLocation(response);

        response = tracksApi.createTask("testTask3", String.valueOf(projectId), String.valueOf(contextId));
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getHeader("Location").contains("/todos/"));
        int taskId = ResponseValidator.extractIdFromLocation(response);
    }


}
