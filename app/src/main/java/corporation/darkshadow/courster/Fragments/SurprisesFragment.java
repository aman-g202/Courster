package corporation.darkshadow.courster.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

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
import corporation.darkshadow.courster.Adapters.Neayby_Adapter;
import corporation.darkshadow.courster.GPSTracker;
import corporation.darkshadow.courster.IndividualCoachingActivity;
import corporation.darkshadow.courster.Network.CoachingCall;
import corporation.darkshadow.courster.R;
import corporation.darkshadow.courster.RecyclerDivider.MyDividerItemDecoration;
import corporation.darkshadow.courster.RecyclerDivider.RecyclerNearByTouchListener;
import corporation.darkshadow.courster.pojo.nearby_response.CoachingResult;
import corporation.darkshadow.courster.pojo.nearby_response.Result;
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

public class SurprisesFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private List<Result> coachingsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Neayby_Adapter neayby_adapter;
    private ProgressBar progressBar;

    private String location =""   ;
    public final static String coachUrl = "https://maps.googleapis.com/";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surprises, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_nearbyview);
        progressBar = (ProgressBar)view.findViewById(R.id.loadingPanel);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL,16));

        recyclerView.addOnItemTouchListener(new RecyclerNearByTouchListener(getActivity(), recyclerView, new SurprisesFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Result r = coachingsList.get(position);

                Intent intent = new Intent(getActivity(), IndividualCoachingActivity.class);
                intent.putExtra("name",r.getName());
                intent.putExtra("rating",r.getRating());
                intent.putExtra("lat",r.getGeometry().getLocation().getLat());
                intent.putExtra("long",r.getGeometry().getLocation().getLng());
                intent.putExtra("address",r.getVicinity());

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Result r = coachingsList.get(position);
                Toast.makeText(getActivity(),"Functionalities Coming Soon",Toast.LENGTH_LONG).show();
            }
        }));

        progressBar.setVisibility(ProgressBar.VISIBLE);

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
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("TAG", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
            displayLocation();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        checkPlayServices();
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 1);



            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            return;

        } else {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();

                location = String.valueOf(latitude)+","+String.valueOf(longitude);

                Retrofit coachingHit = new Retrofit.Builder()
                        .baseUrl(coachUrl)
                        .client(getUnsafeOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CoachingCall coachingCall = coachingHit.create(CoachingCall.class);
                Call<CoachingResult> services = coachingCall.getCoachings(location,2000,"coachings","AIzaSyBNz0R9RDAln0Fy5aKOrGrKj-mYkkwXZhM");

                services.enqueue(new Callback<CoachingResult>() {
                    @Override
                    public void onResponse(Call<CoachingResult> call, Response<CoachingResult> response) {
                        if (response.isSuccessful() && response.body() != null){
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            coachingsList = response.body().getResults();
                            if (coachingsList.size() > 0){
                                neayby_adapter = new Neayby_Adapter(getActivity(),coachingsList);
                                recyclerView.setAdapter(neayby_adapter);
                            }
                            else{
                                Toast.makeText(getActivity(),"Oops! No result found",Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(getActivity(),"Oops! Some issues on the server side",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CoachingResult> call, Throwable t) {
                        if (t.getMessage() != null){
                            Log.d("failure",t.getMessage());
                        }else{
                            Toast.makeText(getActivity(),"Oops! Some error occured",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
//                Toast.makeText(getActivity(), "Server issue plz try after some time!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                    }
                    mLastLocation = LocationServices.FusedLocationApi
                            .getLastLocation(mGoogleApiClient);

                    if (mLastLocation != null) {
                        double latitude = mLastLocation.getLatitude();
                        double longitude = mLastLocation.getLongitude();

                        location = String.valueOf(latitude)+","+String.valueOf(longitude);

                        Retrofit coachingHit = new Retrofit.Builder()
                                .baseUrl(coachUrl)
                                .client(getUnsafeOkHttpClient())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CoachingCall coachingCall = coachingHit.create(CoachingCall.class);
                        Call<CoachingResult> services = coachingCall.getCoachings(location,2000,"coachings","AIzaSyBNz0R9RDAln0Fy5aKOrGrKj-mYkkwXZhM");

                        services.enqueue(new Callback<CoachingResult>() {
                            @Override
                            public void onResponse(Call<CoachingResult> call, Response<CoachingResult> response) {
                                if (response.isSuccessful() && response.body() != null){
                                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                                    coachingsList = response.body().getResults();
                                    if (coachingsList.size() > 0){
                                        neayby_adapter = new Neayby_Adapter(getActivity(),coachingsList);
                                        recyclerView.setAdapter(neayby_adapter);
                                    }
                                    else{
                                        Toast.makeText(getActivity(),"Oops! No result found",Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(getActivity(),"Oops! Some issues on the server side",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CoachingResult> call, Throwable t) {
                                if (t.getMessage() != null){
                                    Log.d("failure",t.getMessage());
                                }else{
                                    Toast.makeText(getActivity(),"Oops! Some error occured",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        Toast.makeText(getActivity(), latitude + " " + longitude, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Server issue plz try after some time!", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getActivity(), "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }

}
