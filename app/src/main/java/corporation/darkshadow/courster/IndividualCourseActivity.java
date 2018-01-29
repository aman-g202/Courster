package corporation.darkshadow.courster;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by darkshadow on 29/1/18.
 */

public class IndividualCourseActivity extends AppCompatActivity {
    private String url = "";
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individualcourse_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcourse);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        webView = (WebView)findViewById(R.id.webView);
        progressBar = (ProgressBar)findViewById(R.id.loadingPanelcourse);

        url = getIntent().getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);

        this.webView.setWebViewClient(new WebViewClient(){



            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                progressBar.setVisibility(ProgressBar.VISIBLE);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
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
