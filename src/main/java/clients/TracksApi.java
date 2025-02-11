package clients;

import config.ConfigManager;
import io.restassured.response.Response;
import restidioms.xmlBodies.CreateContextRequestBody;
import restidioms.xmlBodies.CreateProjectRequestBody;
import restidioms.xmlBodies.CreateTaskRequestBody;

public class TracksApi {

    private static final String BASE_URL = ConfigManager.get("base.url");
    private final ApiClient apiClient;

    public TracksApi(String username, String password) {
        this.apiClient = new ApiClient(BASE_URL);
        apiClient.basicAuth(username, password);
    }

    public Response createContext(String aContext) {
        CreateContextRequestBody createContextRequestBody = new CreateContextRequestBody(aContext);
        return apiClient.sendPostResponse("/contexts.xml", createContextRequestBody.toXml());
    }

    public Response createProject(String aProject) {
        CreateProjectRequestBody createProjectRequestBody = new CreateProjectRequestBody(aProject);
        return apiClient.sendPostResponse("/projects.xml", createProjectRequestBody.toXml());
    }

    public Response createTask(String aTask,  String projectId, String contextId) {
        CreateTaskRequestBody createTaskRequestBody = new CreateTaskRequestBody(aTask, projectId, contextId);
        return apiClient.sendPostResponse("/todos.xml", createTaskRequestBody.toXml());
    }



}
