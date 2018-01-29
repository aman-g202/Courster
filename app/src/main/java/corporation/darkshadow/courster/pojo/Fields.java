package corporation.darkshadow.courster.pojo;

/**
 * Created by darkshadow on 16/12/17.
 */

public class Fields {

    private String coursename;
    private int numOfcourse;
    private String thumbnail;

    public Fields(String coursename, int numOfcourse, String thumbnail) {
        this.coursename = coursename;
        this.numOfcourse = numOfcourse;
        this.thumbnail = thumbnail;
    }

    public int getNumOfcourse() {
        return numOfcourse;
    }

    public void setNumOfcourse(int numOfcourse) {
        this.numOfcourse = numOfcourse;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}
