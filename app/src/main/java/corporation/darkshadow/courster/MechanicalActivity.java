package corporation.darkshadow.courster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import corporation.darkshadow.courster.Adapters.MechanicalAdapter;
import corporation.darkshadow.courster.Network.Mech;
import corporation.darkshadow.courster.RecyclerDivider.MyDividerItemDecoration;
import corporation.darkshadow.courster.RecyclerDivider.RecyclerTouchListener;
import corporation.darkshadow.courster.pojo.Course;
import corporation.darkshadow.courster.pojo.CourseList;
import corporation.darkshadow.courster.pojo.Result;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by darkshadow on 28/1/18.
 */

public class MechanicalActivity extends AppCompatActivity{
    private List<Result> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MechanicalAdapter mechanicalAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mechanical_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmech);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_mechview);
        progressBar = (ProgressBar)findViewById(R.id.loadingPanel);

//        mechanicalAdapter = new MechanicalAdapter(MechanicalActivity.this,courseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this,LinearLayoutManager.VERTICAL,16));
//        recyclerView.setAdapter(mechanicalAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new MechanicalActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Result r = courseList.get(position);
//                Toast.makeText(getApplicationContext(), r.getCoursename(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MechanicalActivity.this,IndividualCourseActivity.class);
                intent.putExtra("url",r.getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Result r = courseList.get(position);
                Toast.makeText(getApplicationContext(),"Functionalities Coming Soon",Toast.LENGTH_LONG).show();
            }
        }));

        progressBar.setVisibility(ProgressBar.VISIBLE);
        Retrofit check = new Retrofit.Builder()
                        .baseUrl("https://eeia.000webhostapp.com/")
                        .client(getUnsafeOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        Mech service = check.create(Mech.class);
        Call<Course> userCall = service.getCourses();
        userCall.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(MechanicalActivity.this,"yeeeeee",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    courseList = response.body().getResult();
                    mechanicalAdapter = new MechanicalAdapter(MechanicalActivity.this,courseList);
                    recyclerView.setAdapter(mechanicalAdapter);

                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });

    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.addInterceptor(logging);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface ClickListener {
        void onClick(View child, int childPosition);

        void onLongClick(View child, int childPosition);
    }
}
