package fleacircus.com.learingprojectclean.UserCreation;

public class User {
    private String collegeSchool, courseYear;
    private Boolean teacherStudent;

    private String username, password;

    public User(String collegeSchool, String courseYear, Boolean teacherStudent) {
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

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public Boolean getTeacherStudent() {
        return teacherStudent;
    }

    public void setTeacherStudent(Boolean teacherStudent) {
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
