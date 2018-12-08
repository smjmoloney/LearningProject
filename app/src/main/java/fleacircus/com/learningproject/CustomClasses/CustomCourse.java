package fleacircus.com.learningproject.CustomClasses;

import java.util.ArrayList;

public class CustomCourse {
    private static final CustomCourse ourInstance = new CustomCourse();

    private String title;
    private ArrayList<CustomTopic> topics;
    private CustomTopic selectedTopic;

    public CustomCourse() {
    }

    public CustomCourse(String title) {
        this.title = title;
    }

    public void clearInstance() {
        if (ourInstance == null)
            return;

        getInstance().setTitle(null);
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

    public CustomTopic getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(CustomTopic selectedTopic) {
        this.selectedTopic = selectedTopic;
    }
}
