package corporation.darkshadow.courster.pojo;

/**
 * Created by darkshadow on 28/1/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("coursename")
    @Expose
    private String coursename;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("university")
    @Expose
    private String university;

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

}