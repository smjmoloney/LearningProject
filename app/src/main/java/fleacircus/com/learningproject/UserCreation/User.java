package fleacircus.com.learningproject.UserCreation;

class User {
    private static final User ourInstance = new User();

    private String teacherStudent, collegeSchool, collegeSchoolLocation, course;
    private String username, password;

    static User getInstance() {
        return ourInstance;
    }

    public String getTeacherStudent() {
        return teacherStudent;
    }

    public void setTeacherStudent(String teacherStudent) {
        this.teacherStudent = teacherStudent;
    }

    public String getCollegeSchool() {
        return collegeSchool;
    }

    public void setCollegeSchool(String collegeSchool) {
        this.collegeSchool = collegeSchool;
    }

    public String getCollegeSchoolLocation() {
        return collegeSchoolLocation;
    }

    public void setCollegeSchoolLocation(String collegeSchoolLocation) {
        this.collegeSchoolLocation = collegeSchoolLocation;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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