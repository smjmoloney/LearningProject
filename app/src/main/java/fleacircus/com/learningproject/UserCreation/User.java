package fleacircus.com.learningproject.UserCreation;

public class User {
    private String collegeSchool, location, courseYear, teacherStudent;
    private String username, password;

    User() {

    }

    public User(String collegeSchool, String location, String courseYear, String teacherStudent) {
        this.collegeSchool = collegeSchool;
        this.courseYear = courseYear;
        this.teacherStudent = teacherStudent;
    }

    public String getCollegeSchool() {
        return collegeSchool;
    }

    public void setCollegeSchool(String collegeSchool) {
        this.collegeSchool = collegeSchool;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getTeacherStudent() {
        return teacherStudent;
    }

    public void setTeacherStudent(String teacherStudent) {
        this.teacherStudent = teacherStudent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
