package clients;

import config.ConfigManager;
import restidioms.xmlBodies.CreateContextRequestBody;
import restidioms.xmlBodies.CreateUserRequestBody;

public class TracksApi {

    private static final String BASE_URL = ConfigManager.get("base.url");
    private final ApiClient apiClient;

    public TracksApi(String username, String password) {
        this.apiClient = new ApiClient(BASE_URL);
        apiClient.basicAuth(username, password);
    }

    public void createContext(String aContext) {
        CreateContextRequestBody createContextRequestBody = new CreateContextRequestBody(aContext);
        apiClient.sendPostResponse("/contexts.xml", createContextRequestBody.toXml());
    }

}
