package corporation.darkshadow.courster.Network;

/**
 * Created by darkshadow on 1/11/17.
 */

import corporation.darkshadow.courster.pojo.Users;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DELL on 5/6/2017.
 */

public interface Login {
    @GET("/login.php")
    Call<Users> checkUser(@Query("mobile") String mobile, @Query("password") String password);
}
