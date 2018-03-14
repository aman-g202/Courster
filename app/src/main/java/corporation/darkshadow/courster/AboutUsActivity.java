package corporation.darkshadow.courster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by DELL on 5/7/2017.
 */

public class AboutUsActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaraboutus);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("About Us");

        textView = (TextView)findViewById(R.id.textViewAboutUs);
        textView.setText("Explore Educational Interest app help students find personalized information across" +
                "various education streams like Engineering, Arts, Science, Management, Medicine," +
                "Pharmacy, Designing and Law. It is like an Education Hub where enabling students" +
                "can be kept informed about various certified courses in different stream of their" +
                "choice. \n\n" +
                "This app assists you with the career guidance and can sail you through perplexity and" +
                "uncertainty and finally evolving into bright future career planning. Explore Education" +
                "App also provides best study material for courses.\n"+"                           ");
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
