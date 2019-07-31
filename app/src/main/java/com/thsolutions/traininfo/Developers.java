package com.thsolutions.traininfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Developers extends AppCompatActivity {
    private InterstitialAd inter;
    private String s1 = "ca-app-pub-4302621275268369/2780916718";
    private Button btn_pp;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);


        TextView textView =(TextView)findViewById(R.id.textView2);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        // Load an ad into the AdMob banner view.
        AdView adView2 = (AdView) findViewById(R.id.adView2);

        AdRequest adRequest2 = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView2.loadAd(adRequest2);

        // Load an ad into the AdMob Interstitial view.
        inter =  new InterstitialAd(this);
        inter.setAdUnitId(s1);
        AdRequest request =  new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        inter.loadAd(request);

         btn_pp = (Button)findViewById(R.id.btn_pp);
         btn_pp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 url = "https://traininfoindiathsolutions.blogspot.com/2019/02/privacy-policy-techno-habitat-solutions.html";
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse(url));
                 startActivity(i);
             }
         });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dev, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {
/*
            Intent intent = new Intent(Developers.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);

*/          finishAffinity();
            Toast.makeText(getApplicationContext(),"Please share app and help others\n\tThank You see you again", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(inter.isLoaded()){
            inter.show();
        }
        else{
            startActivity(new Intent(this,Main2Activity.class));
            finishAffinity();
        }
    }


}
