package restidioms.xmlBodies;

public class CreateProjectRequestBody {

    private final String projectName;

    public CreateProjectRequestBody(String projectName) {
        if(isNullOrEmpty(projectName)) {
            throw new IllegalArgumentException("Context name must not be null or empty");
        }
        this.projectName = projectName;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public String toXml() {
        return "<project>" +
                "<name>" + projectName + "</name>" +
                "</project>";
    }
}
