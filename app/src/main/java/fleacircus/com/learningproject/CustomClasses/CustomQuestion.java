package fleacircus.com.learningproject.CustomClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomQuestion {
    private String title;
    private List<Map.Entry<String, Object>> answers = new ArrayList<>();

    public CustomQuestion() {
    }

    public CustomQuestion(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map.Entry<String, Object>> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Map.Entry<String, Object>> answers) {
        this.answers = answers;
    }
}
