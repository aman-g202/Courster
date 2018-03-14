package corporation.darkshadow.courster.Network;

import corporation.darkshadow.courster.pojo.nearby_response.CoachingResult;
import corporation.darkshadow.courster.pojo.nearby_response.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by darkshadow on 8/3/18.
 */

public interface CoachingCall {
    @GET("/maps/api/place/nearbysearch/json")
    Call<CoachingResult> getCoachings(@Query("location") String location, @Query("radius") Integer radius
            , @Query("keyword") String keyword, @Query("key") String key);
}
