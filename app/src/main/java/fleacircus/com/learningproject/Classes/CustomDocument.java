package fleacircus.com.learningproject.Classes;

import java.util.ArrayList;
import java.util.List;

public class CustomDocument {

    private List<String> children = new ArrayList<>();
    private String classType;

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public void addChild(String child) {
        children.add(child);
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
