package com.thsolutions.traininfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    //private InterstitialAd inter;
    private AdView adView;
    private AdRequest adRequest, request;
    private GoogleApiClient client;
    private ProgressBar progressBar;
    private static boolean activityVisible;
    //private String url = "https://enquiry.indianrail.gov.in/mntes/";
    int i,j=0;
    private boolean mTryAgain = true;
    private boolean mTryAgainInter = true;
    private boolean adLoaded = false;
    //private boolean interLoaded = false;
    //private String s1 = "ca-app-pub-4302621275268369/7315153331";
    Intent webintent;
    private String webaddress;

    String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteCache(this);
        webintent = getIntent();
        final String webaddress = webintent.getStringExtra("webaddress");
        // Load an ad into the AdMob banner view.
        adView = (AdView) findViewById(R.id.adView);

        adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        /*
        // Load an ad into the AdMob Interstitial view.
        inter =  new InterstitialAd(MainActivity.this);
        inter.setAdUnitId(s1);

        //Interstitial AdListener
        inter.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (mTryAgain) {
                    if (isNetworkStatusAvialable(getApplicationContext())) {
                        inter.loadAd(new AdRequest.Builder().build());
                        mTryAgain = false;
                        //Toast.makeText(getApplicationContext(),"Failed_loaded",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interLoaded = true;
                //Toast.makeText(getApplicationContext(),"loaded_inter",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClosed ()
            {
                // Load the next interstitial.
                inter.loadAd(new AdRequest.Builder().build());
            }
        });*/

        //Banner adListener
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(mTryAgain){
                    if(isNetworkStatusAvialable (getApplicationContext())) {
                        adView.loadAd(new AdRequest.Builder().build());
                        mTryAgain = false;
                        //Toast.makeText(getApplicationContext(),"Failed_loaded",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
                adLoaded = true;
                //Toast.makeText(getApplicationContext(),"loaded",Toast.LENGTH_LONG).show();
            }
        });
        // ProgressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        assert progressBar != null;
        progressBar.setMax(100);

        //WebView Settings
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new WebViewClientDemo());
        myWebView.setWebChromeClient(new WebChromeClientDemo());
        myWebView.getSettings().setJavaScriptEnabled(true);

        if(isNetworkStatusAvialable (getApplicationContext())) {
            myWebView.loadUrl(webaddress);
            if(adLoaded!=true){
                adView.loadAd(adRequest);
                //Toast.makeText(getApplicationContext(),"loadAd Request after URL loading",Toast.LENGTH_LONG).show();
            }
            else{
                //Toast.makeText(getApplicationContext(),"loaded after entering app",Toast.LENGTH_LONG).show();
            }
            /*
            if(interLoaded!=true) {
                inter.loadAd(new AdRequest.Builder().build());
            }
            else{

            }*/
//         ATTENTION: This was auto-generated to implement the App Indexing API.
//         See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }
        else {
            noInternet();
            if (getIntent().getBooleanExtra("EXIT", false))
            {
                finish();
                Toast.makeText(getApplicationContext(),"Please share app and help others\n\tThank You see you again", Toast.LENGTH_LONG).show();
            }
        }

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            String path = context.getCacheDir().toString();

            deleteDir(dir);
            //Toast.makeText(context, path, Toast.LENGTH_LONG).show();

        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    //Code for Prompt Dialog box whether to close or retry
    public void noInternet(){

        myWebView.loadUrl("file:///android_asset/offline.html");
        //Create an alertdialog
        AlertDialog.Builder Checkbuilder=new  AlertDialog.Builder(MainActivity.this);
        Checkbuilder.setTitle("Error!");
        Checkbuilder.setMessage("Check Your Internet Connection.");
        Checkbuilder.setCancelable(false);
        //Builder Retry Button
        Checkbuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Restart The Activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        //Builder Exit Button
        Checkbuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                //Toast.makeText(getApplicationContext(),"Please share app and help others\n\tThank You see you again", Toast.LENGTH_LONG).show();
            }
        }) ;

        AlertDialog alert=Checkbuilder.create();
        alert.show();
    }

    //Check for Internet connection
    public static boolean isNetworkStatusAvialable (Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }

    private class WebViewClientDemo extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
            try {
                myWebView.loadUrl("file:///android_asset/offline.html");
                AlertDialog.Builder Checkbuilder = new AlertDialog.Builder(MainActivity.this);
                Checkbuilder.setTitle("Error!");
                Checkbuilder.setMessage("Check Your Internet Connection.");
                Checkbuilder.setCancelable(false);
                //Builder Retry Button
                Checkbuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Restart The Activity
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                Checkbuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alert = Checkbuilder.create();
                alert.show();
            }
            catch(Exception e){
                //Restart The Activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }

        @Override
        public void onReceivedHttpError (WebView view,
                                         WebResourceRequest request,
                                         WebResourceResponse errorResponse){
            try {
                myWebView.loadUrl("file:///android_asset/offline.html");
                AlertDialog.Builder Checkbuilder = new AlertDialog.Builder(MainActivity.this);
                Checkbuilder.setTitle("Error!");
                Checkbuilder.setMessage("Server busy");
                Checkbuilder.setCancelable(false);
                //Builder Retry Button
                Checkbuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Restart The Activity
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                Checkbuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alert = Checkbuilder.create();
                alert.show();
            }
            catch(Exception e){
                //Restart The Activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(30);
        }
    }
    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Check if the key event was the Back button and if there's history
       /* if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        else*/
        {
            //Toast.makeText(getApplicationContext(),"else",Toast.LENGTH_LONG).show();
            webintent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(webintent);

        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*if(id==R.id.action_favorite) {
            if(isNetworkStatusAvialable (getApplicationContext())){
*//*
                try {
                    if(inter.isLoaded()&&(i==0)){
                        i++;
                        inter.show();
                        //inter.loadAd(new AdRequest.Builder().build());
                    }
                    else{
                        //Toast.makeText(getApplicationContext(),"HomeButton_NotLoaded",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),"I="+i,Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ad) {

                }*//*
               // myWebView.loadUrl(url);

                if(adLoaded!=true){
                    adView.loadAd(adRequest);
                    //Toast.makeText(getApplicationContext(),"failed to load \n loaded Home button",Toast.LENGTH_LONG).show();
                }
                *//*
                if(interLoaded!=true){
                    inter.loadAd(new AdRequest.Builder().build());
                    //Toast.makeText(getApplicationContext(),"InterLoadRequest",Toast.LENGTH_LONG).show();
                }*//*
            }
            else {

                noInternet();
                if (getIntent().getBooleanExtra("EXIT", false))
                {
                    finish();
                    //Toast.makeText(getApplicationContext(),"Please share app and help others\n\tThank You see you again", Toast.LENGTH_LONG).show();
                }
            }

        }*/
        if(id==R.id.action_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "\nBest app for info of all Trains like\nTrain running status,\nPNR,\nTickets Avilability\nand lot more\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.thsolutions.traininfo\n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }


        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {
            finishAffinity();
            Toast.makeText(getApplicationContext(),"Please share app and help others\n\tThank You see you again", Toast.LENGTH_LONG).show();

        }
        if (id == R.id.deve) {
            Intent intent = new Intent(MainActivity.this,
                    Developers.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }
    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.activityPaused();
    }
}
