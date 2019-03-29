package fleacircus.com.learningproject.Classes;

import java.io.Serializable;

import fleacircus.com.learningproject.Utils.StringUtils;

public class CustomUser implements Serializable {
    private static final CustomUser ourInstance = new CustomUser();

    private String teacherStudent, collegeSchool, location, course;
    private String email, name;
    private String uid;
    private int imageID = 0;

    public static CustomUser getInstance() {
        return ourInstance;
    }

    public String getTeacherStudent() {
        return teacherStudent;
    }

    public void setTeacherStudent(String teacherStudent) {
        this.teacherStudent = StringUtils.toLowerCase(teacherStudent);
    }

    public String getCollegeSchool() {
        return collegeSchool;
    }

    public void setCollegeSchool(String collegeSchool) {
        this.collegeSchool = StringUtils.toLowerCase(collegeSchool);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = StringUtils.toLowerCase(location);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = StringUtils.toLowerCase(course);
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = StringUtils.toLowerCase(email);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.toLowerCase(name);
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public static void updateInstance(CustomUser ourInstance) {
        getInstance().setName(ourInstance.name);
        getInstance().setCourse(ourInstance.course);
        getInstance().setLocation(ourInstance.location);
        getInstance().setCollegeSchool(ourInstance.collegeSchool);
        getInstance().setTeacherStudent(ourInstance.teacherStudent);
        getInstance().setImageID(ourInstance.imageID);
    }
}