package fleacircus.com.learningproject.Course;

import java.util.ArrayList;

public class CustomCourse {
    private static final CustomCourse ourInstance = new CustomCourse();

    private String title;
    private ArrayList<CustomTopic> topics;

    public void clearInstance() {
        if (ourInstance == null)
            return;

        getInstance().setTitle(null);
        getInstance().setTopics(null);
    }

    public static CustomCourse getInstance() {
        return ourInstance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<CustomTopic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<CustomTopic> topics) {
        this.topics = topics;
    }
}
