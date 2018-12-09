package fleacircus.com.learningproject.CustomClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomAnswer {
    private String title;
    private List<Map<String, Object>> answers = new ArrayList<>();

    public CustomAnswer() {
    }

    public CustomAnswer(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String, Object>> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Map<String, Object>> answers) {
        this.answers = answers;
    }
}
