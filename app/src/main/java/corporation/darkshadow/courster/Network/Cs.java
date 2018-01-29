package corporation.darkshadow.courster.Network;

import corporation.darkshadow.courster.pojo.Course;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by darkshadow on 29/1/18.
 */

public interface Cs {
    @GET("/cs.php")
    Call<Course> getCourses();
}
