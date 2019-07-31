package com.thsolutions.traininfo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

    private Button spot,pnr,seatav,schedule,trains,cancel,reschedule,diverted;
    private Intent webpage;
    private AdView adView;
    private AdRequest adRequest;
    private boolean mTryAgain = true;
    private boolean adLoaded = false;
    private static boolean activityVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        deleteCache(this);
        adView = (AdView) findViewById(R.id.adView1);

        adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        spot = (Button) findViewById(R.id.spot);
        pnr = (Button) findViewById(R.id.pnr);
        seatav = (Button) findViewById(R.id.seatav);
        schedule = (Button) findViewById(R.id.schedule);
        trains = (Button) findViewById(R.id.trains);
        //seats = (Button) findViewById(R.id.seats);
        cancel = (Button) findViewById(R.id.cancel);
        reschedule = (Button) findViewById(R.id.reschedule);
        diverted = (Button) findViewById(R.id.diverted);

/*
        //Banner adListener
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(mTryAgain){
                    if(isNetworkStatusAvialable (getApplicationContext())) {
                        adView.loadAd(new AdRequest.Builder().build());
                        mTryAgain = false;
                        Toast.makeText(getApplicationContext(),"Failed_loaded",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
                adLoaded = true;
                try{
                    adView.loadAd(adRequest);
                }
                catch (Exception e)
                {

                }

                Toast.makeText(getApplicationContext(),"loaded",Toast.LENGTH_LONG).show();
            }
        });*/

        /*
        if(adLoaded!=true){
            adView.loadAd(adRequest);
            Toast.makeText(getApplicationContext(),"loadAd Request after URL loading",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"loaded after entering app",Toast.LENGTH_LONG).show();
        }*/

        spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/e_index.jsp");
                //webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/mntes/");
                startActivity(webpage);
            }
        });


        seatav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                webpage.putExtra("webaddress","http://www.indianrail.gov.in/enquiry/SEAT/SeatAvailability.html?locale=en");
                startActivity(webpage);
            }
        });

        pnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                webpage.putExtra("webaddress","http://www.indianrail.gov.in/enquiry/PNR/PnrEnquiry.html?locale=en");
                startActivity(webpage);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/e_trainSchedule.jsp");
                //webpage.putExtra("webaddress","http://www.indianrail.gov.in/enquiry/SCHEDULE/TrainSchedule.html?locale=en");
                startActivity(webpage);
            }
        });

        trains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/e_trainsBwStns.jsp");
                //webpage.putExtra("webaddress","http://www.indianrail.gov.in/enquiry/TBIS/TrainBetweenImportantStations.html?locale=en");
                startActivity(webpage);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/_cancelledTrains.jsp");
                startActivity(webpage);
            }
        });

        reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/RescheduledTrains");
                //webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/RescheduledTrains?scrnSize=&langFile=props.hi-in");
                startActivity(webpage);
            }
        });

        diverted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = new Intent(getApplicationContext(),MainActivity.class);
                //webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/DivertedTrains?scrnSize=&langFile=props.hi-in");
                webpage.putExtra("webaddress","https://enquiry.indianrail.gov.in/xyzabc/DivertedTrains");
                startActivity(webpage);
            }
        });

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            String path = context.getCacheDir().toString();

            deleteDir(dir);
           // Toast.makeText(context, path, Toast.LENGTH_LONG).show();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    try {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Main2Activity.this);
        mbuilder.setTitle("Close App?");
        mbuilder.setMessage("Our New App\nGive a missed call\nGet your bank account balance for free");
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        Button btn_install = (Button) mView.findViewById(R.id.btn_install);
        Button btn_close = (Button) mView.findViewById(R.id.btn_close);
        Button btn_share = (Button) mView.findViewById(R.id.btn_share);
        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String context;
                Uri uri = Uri.parse("market://details?id=thsolutions.allbankinfo");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Please share app and help others\n\tThank You see you again", Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String sAux = "\nBest app for info of all Trains like\nTrain running status,\nPNR,\nTickets Avilability\nand lot more\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.thsolutions.traininfo\n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        mbuilder.setView(mView);
        AlertDialog alert = mbuilder.create();
        alert.show();

    }
    catch (Exception e)
    {
    }



        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


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
            Intent intent = new Intent(Main2Activity.this,
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

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
}
