package com.example.soram.iasbp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.gigamole.navigationtabstrip.NavigationTabStrip;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayTime = new ArrayList<String>();
    ArrayList<String> arrayDate = new ArrayList<String>();
    ArrayList<String> arrayValue = new ArrayList<String>();
    ArrayList<String> insideArray = new ArrayList<String>();
    ArrayList<String> arrayTempDate = new ArrayList<String>();
    ArrayList<String> arrayTempTime = new ArrayList<String>();
    ArrayList<String> arrayTempValue = new ArrayList<String>();
    //Control
    ArrayList<String> controlMode = new ArrayList<>();
    ArrayList<String> controlStatus = new ArrayList<String>();
    ArrayList<String> controlLowMin = new ArrayList<String>();
    ArrayList<String> controlLowMax = new ArrayList<String>();
    ArrayList<String> controlHighMin = new ArrayList<String>();
    ArrayList<String> controlHighMax = new ArrayList<String>();
    Integer cont = 0;
    ActionBar actionBar;
    Boolean humiOrTemp = false;
    Bundle bundle;
    NavigationTabStrip tiles;
    String HOST_URL;
    String USERNAME;
    String PASSWORD;
    String HUMIDATA;
    String TEMPDATA;
    String CONTROL;
    String INSIDEDATA;
    int whichSide;
    OkHttpClient client;
    String emptyTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiles = findViewById(R.id.tiles);
        getValues();
        setActionBar();
        setTiles();
        getHumiData(HUMIDATA);
    }

    public void getHumiData(String url) {
        generateCredentials();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newControl service = retrofit.create(newControl.class);
        Call<List<GetHumiData>> call = service.sqlData(url);
        call.enqueue(new Callback<List<GetHumiData>>() {
            @Override
            public void onResponse(Call<List<GetHumiData>> call, Response<List<GetHumiData>> response) {
                List<GetHumiData> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    String date = list.get(i).getDate();
                    String time = list.get(i).getTime();
                    String value = list.get(i).getValue();
                    if (!humiOrTemp){
                        arrayValue.add(value);
                        arrayTime.add(time);
                        arrayDate.add(date);
                    }else {
                        arrayTempDate.add(date);
                        arrayTempValue.add(value);
                        arrayTempTime.add(time);
                    }
                }
                if (humiOrTemp){
                    bundle = new Bundle();
                    bundle.putStringArrayList("HumiValues", arrayValue);
                    bundle.putStringArrayList("HumiTime", arrayTime);
                    bundle.putStringArrayList("TempValues", arrayTempValue);
                    bundle.putStringArrayList("TempTime", arrayTempTime);
                    bundle.putStringArrayList("Date", arrayTempDate);

                    if (cont == 0){
                        getControlData();
                    }
                    cont++;
                }else {
                    humiOrTemp = true;
                    getHumiData(TEMPDATA);
                }
            }
            @Override
            public void onFailure(Call<List<GetHumiData>> call, Throwable t) {
                Log.e("Fail ", " " + t);
            }
        });

    }
    public void getControlData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
        Call<List<GetControlData>> call = service.controlData(CONTROL);
        call.enqueue(new Callback<List<GetControlData>>() {
            @Override
            public void onResponse(Call<List<GetControlData>> call, Response<List<GetControlData>> response) {
                List<GetControlData> list = response.body();

                for (int i = 0; i < list.size(); i++){

                    controlMode.add(list.get(i).getMode());
                    controlHighMax.add(list.get(i).getHighMax());
                    controlHighMin.add(list.get(i).getHighMin());
                    controlLowMax.add(list.get(i).getLowMax());
                    controlLowMin.add(list.get(i).getLowMin());
                    controlStatus.add(list.get(i).getStatus());
                }
                bundle.putStringArrayList("HighMax", controlHighMax);
                bundle.putStringArrayList("HighMin", controlHighMin);
                bundle.putStringArrayList("LowMax", controlLowMax);
                bundle.putStringArrayList("LowMin", controlLowMin);
                bundle.putStringArrayList("Status", controlStatus);
                bundle.putStringArrayList("Mode", controlMode);
                getInsideData();
            }
            @Override
            public void onFailure(Call<List<GetControlData>> call, Throwable t) {
                Log.e("Fail ", " " + t);
            }
        });

    }
    public void getInsideData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
        Call<List<GetInsideData>> call = service.insideData(INSIDEDATA);
        call.enqueue(new Callback<List<GetInsideData>>() {
            @Override
            public void onResponse(Call<List<GetInsideData>> call, Response<List<GetInsideData>> response) {
                List<GetInsideData> list = response.body();
                for (int i = 0; i < list.size(); i++){
                    insideArray.add(list.get(i).getValue());
                }
                bundle.putStringArrayList("Inside", insideArray);
                loadFragment(new MainFragment(), bundle, R.anim.from_right, R.anim.to_left);
            }

            @Override
            public void onFailure(Call<List<GetInsideData>> call, Throwable t) {

            }
        });


    }
    public void loadFragment(Fragment fragment, Bundle bundle, int anim1, int anim2){
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(anim1, anim2);
        fragmentTransaction.replace(R.id.relativeView, fragment);
        fragmentTransaction.commit();


    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Do you really want to exit?")
                .setNegativeButton(R.string.action_no, null)
                .setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                humiOrTemp = false;
                cont = 0;
                controlMode.clear();
                arrayDate.clear();
                arrayValue.clear();
                arrayTime.clear();
                insideArray.clear();
                arrayTempDate.clear();
                arrayTempValue.clear();
                arrayTempTime.clear();
                getHumiData(new ApiKeys().getHumiData());
                tiles.setTabIndex(0, true);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_logo4);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("");
    }

    // API_KEYS
    public void getValues(){
        emptyTag = "";
        HOST_URL = new ApiKeys().getLink();
        HUMIDATA = new ApiKeys().getHumiData();
        TEMPDATA = new ApiKeys().getTempData();
        CONTROL  = new ApiKeys().getControl();
        INSIDEDATA = new ApiKeys().getInsideData();

    }
    public void generateCredentials(){
        USERNAME = new ApiKeys().getUsername();
        PASSWORD = new ApiKeys().getPassword();
        client = new HttpClient(USERNAME,PASSWORD, emptyTag, emptyTag).getClient();
    }
    public void setTiles(){
        whichSide = 1;
        tiles.setTitles("Home", "Graphs", "Control");
        tiles.setInactiveColor(getResources().getColor(R.color.tiles_inactive));
        tiles.setActiveColor(getResources().getColor(R.color.tiles_active));
        tiles.setTabIndex(0, true);
        tiles.setStripColor(getResources().getColor(R.color.mainPink));
        tiles.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                switch (title){
                    case "GRAPHS":  if (whichSide == 0){
                        loadFragment(new GraphFragment(), bundle, R.anim.from_left, R.anim.to_right); break;
                    }else{
                    } loadFragment(new GraphFragment(), bundle, R.anim.from_right, R.anim.to_left); break;
                    case "HOME":    loadFragment(new MainFragment(), bundle, R.anim.from_left, R.anim.to_right); whichSide = 1;  break;
                    case "CONTROL":
                        loadFragment(new ControlFragment(), bundle, R.anim.from_right, R.anim.to_left); whichSide = 0;  break;

                }
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
    }
    public void switchScreen() {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }
}


