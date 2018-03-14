package corporation.darkshadow.courster.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by darkshadow on 14/3/18.
 */

public class SharedPrefUtil {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MyPref";

    public SharedPrefUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLogin(String key, String mobile){
        editor.putString(key,mobile);
        editor.commit();
    }

    public String retrieveLogin(String key, String defValue){
        return sharedPreferences.getString(key,defValue);
    }

    public void logOutUser(){
        editor.clear();
        editor.commit();
    }



}
