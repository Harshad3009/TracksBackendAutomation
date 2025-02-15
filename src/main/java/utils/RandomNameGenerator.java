package utils;

import java.util.List;
import java.util.Random;

public class RandomNameGenerator {

    private static final List<String> PROJECT_NAMES = List.of(
            "Marketing Campaign", "Product Launch", "Website Redesign", "Customer Support Improvements", "Security Enhancements"
    );

    public static final List<String> CONTEXT_NAMES = List.of(
            "Work", "Personal", "Urgent", "Research", "Learning"
    );

    private static final List<String> TASK_NAMES = List.of(
            "Write project plan", "Fix UI bugs", "Prepare presentation", "Conduct user testing", "Optimize database queries"
    );

    private static final Random RANDOM = new Random();

    public static String getRandomProjectName() {
        return PROJECT_NAMES.get(RANDOM.nextInt(PROJECT_NAMES.size()));
    }

    public static String getRandomContextName() {
        return CONTEXT_NAMES.get(RANDOM.nextInt(CONTEXT_NAMES.size()));
    }

    public static String getRandomTaskName() {
        return TASK_NAMES.get(RANDOM.nextInt(TASK_NAMES.size()));
    }

}
