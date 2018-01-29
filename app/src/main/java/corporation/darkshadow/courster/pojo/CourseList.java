package corporation.darkshadow.courster.pojo;

/**
 * Created by darkshadow on 29/1/18.
 */

public class CourseList {
    private String coursename,university,url;

    public CourseList(){

    }

    public CourseList(String coursename, String university, String url) {

        this.coursename = coursename;
        this.university = university;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
