package corporation.darkshadow.courster.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import corporation.darkshadow.courster.Adapters.CommerceAdapter;
import corporation.darkshadow.courster.Adapters.CsAdapter;
import corporation.darkshadow.courster.CsActivity;
import corporation.darkshadow.courster.IndividualCourseActivity;
import corporation.darkshadow.courster.Network.Arts;
import corporation.darkshadow.courster.Network.Commerce;
import corporation.darkshadow.courster.Network.Cs;
import corporation.darkshadow.courster.Network.Medical;
import corporation.darkshadow.courster.R;
import corporation.darkshadow.courster.RecyclerDivider.MyDividerItemDecoration;
import corporation.darkshadow.courster.RecyclerDivider.RecyclerArtsTouchListener;
import corporation.darkshadow.courster.RecyclerDivider.RecyclerCommerceTouchListener;
import corporation.darkshadow.courster.RecyclerDivider.RecyclerCsTouchListener;
import corporation.darkshadow.courster.RecyclerDivider.RecyclerMedicalTouchListener;
import corporation.darkshadow.courster.pojo.Course;
import corporation.darkshadow.courster.pojo.Result;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 5/7/2017.
 */

public class GetInTouchFragment extends Fragment {

    private List<Result> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommerceAdapter CommerceAdapter;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getintouch,container,false);

        setHasOptionsMenu(true);

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarmech);
//        setSupportActionBar(toolbar);
//
//        // toolbar fancy stuff
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("ComputerScience Courses");

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_mechview);
        progressBar = (ProgressBar)view.findViewById(R.id.loadingPanel);

//        CsAdapter = new CsAdapter(CsActivity.this,courseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL,16));
//        recyclerView.setAdapter(CsAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerCommerceTouchListener(getActivity(), recyclerView, new GetInTouchFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Result r = courseList.get(position);
//                Toast.makeText(getApplicationContext(), r.getCoursename(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),IndividualCourseActivity.class);
                intent.putExtra("url",r.getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Result r = courseList.get(position);
                Toast.makeText(getActivity(),"Functionalities Coming Soon",Toast.LENGTH_LONG).show();
            }
        }));

        // white background notification bar
//        whiteNotificationBar(recyclerView);

        progressBar.setVisibility(ProgressBar.VISIBLE);
        Retrofit check = new Retrofit.Builder()
                .baseUrl("https://eeia.000webhostapp.com/")
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Commerce service = check.create(Commerce.class);
        Call<Course> userCall = service.getCourses();
        userCall.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(CsActivity.this,"yeeeeee",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    courseList = response.body().getResult();
                    CommerceAdapter = new CommerceAdapter(getActivity(),courseList);
                    recyclerView.setAdapter(CommerceAdapter);

                }
                else{
                    Toast.makeText(getActivity(),"Oops! Some issues on the server side",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });


        return view;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                CommerceAdapter CommerceAdapter = new CommerceAdapter();
                CommerceAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                CommerceAdapter.getFilter().filter(query);
                return false;
            }
        });
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if(id == android.R.id.home){
//            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getActivity().getWindow().setStatusBarColor(Color.WHITE);
        }
    }


}
