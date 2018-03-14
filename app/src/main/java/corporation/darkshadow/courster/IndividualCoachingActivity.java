package corporation.darkshadow.courster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by darkshadow on 8/3/18.
 */

public class IndividualCoachingActivity extends AppCompatActivity {

    private static String name,address;
    private static double rating;
    private static Double lat,longi;
    private static Boolean open;

    private TextView getname,getaddress,getrating;
    private Button mapbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individualcoaching_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnearby);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Courster");

        getname = (TextView)findViewById(R.id.getname);
        getaddress = (TextView)findViewById(R.id.getaddress);
        getrating = (TextView)findViewById(R.id.getrating);
        mapbutton = (Button)findViewById(R.id.mapviewbutton);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        rating = intent.getDoubleExtra("rating",0);
        address = intent.getStringExtra("address");
        lat = intent.getDoubleExtra("lat",0);
        longi  = intent.getDoubleExtra("long",0);

        getname.setText(name);
        getaddress.setText(address);
        getrating.setText(String.valueOf(rating)+" Star");

        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(IndividualCoachingActivity.this,String.valueOf(lat)+","+String.valueOf(longi),Toast.LENGTH_LONG).show();
                String uri = String.format(Locale.ENGLISH, "geo:0,0?z=15&q=%f,%f(%s)", lat,longi,name);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
