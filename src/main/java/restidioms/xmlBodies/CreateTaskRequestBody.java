package restidioms.xmlBodies;

public class CreateTaskRequestBody {

    private final String taskName;
    private final String projectId;
    private final String contextId;


    public CreateTaskRequestBody(String taskName, String projectId, String contextId) {
        this.taskName = taskName;
        this.projectId = projectId;
        this.contextId = contextId;
        if(isNullOrEmpty(taskName)) {
            throw new IllegalArgumentException("Task name must not be null or empty");
        }
    }

    public CreateTaskRequestBody(String taskName) {
        this
                (taskName, "", "");
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public String toXml() {
        return String.format("<todo>\n<description>%s</description>\n" +
                        "  <project_id>%s</project_id>\n" +
                        "  <context_id>%s</context_id>\n" +
                        "</todo>",
                taskName, projectId, contextId);
    }
}
