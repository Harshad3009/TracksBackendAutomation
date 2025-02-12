package tracksTests.userSetupTests;

import base.BaseTest;
import clients.TracksApi;
import clients.TracksAppAsApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ResponseValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//@Ignore
public class SetupTestDataUtilityTest extends BaseTest {

    private static final int NUM_OF_USERS = 2;
    private static final int MAX_TASKS = 20;
    List<String> contextNames = List.of("Work", "Home", "Shopping");

    // Username and Password patterns
    private static final String USERNAME_PATTERN = "userName%d";
    private static final String PASSWORD_PATTERN = "password@%d";

    @Test
    public void createRandomUsersAndAddContextAndProjectAndTask() {
        TracksAppAsApi adminTracks = new TracksAppAsApi(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);

        Map<String, String> userCredentials = generateRandomUserCredentials(NUM_OF_USERS);

        for (Map.Entry<String, String> user : userCredentials.entrySet()) {
            adminTracks.createUser(user.getKey(), user.getValue());
            TracksApi userTracks = new TracksApi(user.getKey(), user.getValue());
            Response response = userTracks.createContext("testContext");
            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertTrue(response.getHeader("Location").contains("/contexts/"));
            int contextId = ResponseValidator.extractIdFromLocation(response);
            response = userTracks.createProject("testProject");
            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertTrue(response.getHeader("Location").contains("/projects/"));
            int projectId = ResponseValidator.extractIdFromLocation(response);
            response = userTracks.createTask("testTask", String.valueOf(projectId), String.valueOf(contextId));
            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertTrue(response.getHeader("Location").contains("/todos/"));
        }
    }

    @Test
    public void createUserAndAddRandomNumberOfTasks() {
        TracksAppAsApi adminTracks = new TracksAppAsApi(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);

        Map<String, String> userCredentials = generateRandomUserCredentials(NUM_OF_USERS);

        for (Map.Entry<String, String> user : userCredentials.entrySet()) {
            adminTracks.createUser(user.getKey(), user.getValue());
            TracksApi userTracks = new TracksApi(user.getKey(), user.getValue());
            Response response;
            for (String contextName : contextNames) {
                response = userTracks.createContext(contextName);
                Assert.assertEquals(response.getStatusCode(), 201);
                Assert.assertTrue(response.getHeader("Location").contains("/contexts/"));
            }
            for (int i = 0; i < 5; i++) {
                response = userTracks.createProject("testProject" + i);
                Assert.assertEquals(response.getStatusCode(), 201);
                Assert.assertTrue(response.getHeader("Location").contains("/projects/"));
            }

            response = userTracks.getUsersContexts();
            List<String> contextIds = ResponseValidator.extractContextIdsFromResponse(response);
            response = userTracks.getUsersProjects();
            List<String> projectIds = ResponseValidator.extractProjectIdsFromResponse(response);

            int numberOfTasks = new Random().nextInt(MAX_TASKS) + 1;
            for (int i = 0; i < numberOfTasks; i++) {
                response = userTracks.createTask("testTask" + i,
                        String.valueOf(projectIds.get(new Random().nextInt(projectIds.size()))),
                        String.valueOf(contextIds.get(new Random().nextInt(contextIds.size()))));
                Assert.assertEquals(response.getStatusCode(), 201);
                Assert.assertTrue(response.getHeader("Location").contains("/todos/"));
            }
        }
    }

    private Map<String, String> generateRandomUserCredentials(int count) {
        Map<String, String> userCredentials = new HashMap<>();
        for(int i = 0; i < count; i++) {
            userCredentials.put(String.format(USERNAME_PATTERN, new Random().nextInt()), String.format(PASSWORD_PATTERN, new Random().nextInt()));
        }
        return userCredentials;
    }
}
