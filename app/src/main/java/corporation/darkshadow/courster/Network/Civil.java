package corporation.darkshadow.courster.Network;

import corporation.darkshadow.courster.pojo.Course;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by darkshadow on 1/2/18.
 */

public interface Civil {
    @GET("/civil.php")
    Call<Course> getCourses();
}
