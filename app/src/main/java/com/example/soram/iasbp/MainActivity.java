package com.example.soram.iasbp;
import android.content.DialogInterface;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import io.github.tonnyl.light.Light;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;


public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener{

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
    String GETTOKEN;
    int whichSide;
    OkHttpClient client;
    String emptyTag;
    newControl mNewControl;
    PatternLockView patternLockView;
    List<PatternLockView.Dot> patternList;
    String PATTERNSTRING;
    Toolbar toolbar;
    ImageView iasLogo;
    ImageView menu;
    LinearLayout ln;
    List<MenuObject> menuObjects;
    MenuParams menuParams;
    ContextMenuDialogFragment mMenuDialogFragment;
    FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiles = findViewById(R.id.tiles);
        patternLockView = findViewById(R.id.pattern_lock_view);
        toolbar = findViewById(R.id.toolbar);
        iasLogo = findViewById(R.id.IAS);
        menu = findViewById(R.id.menu);
        menu.setClickable(false);
        fragmentManager = getSupportFragmentManager();
        getValues();
        setActionBar();
        setTiles();
        mNewControl = new GetPrivateToken().getNewControl("", "");
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {


            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern){
                if (pattern.toString().equals(PATTERNSTRING)){
                    Light.success(patternLockView, "Correct.", Snackbar.LENGTH_SHORT).show();
                    patternLockView.setVisibility(View.INVISIBLE);
                    menu.setClickable(true);
                    client = new HttpClient(USERNAME,PASSWORD, emptyTag, emptyTag).getClient();
                    getHumiData(HUMIDATA);
                }else {
                    Light.warning(patternLockView, "Wrong password.", Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCleared() {

            }
        });
        menu.setOnClickListener(view -> mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG));

    }

    public void getHumiData(String url) {
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






    public void setActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setElevation(0);
//        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.ic_refresh));



//        actionBar = getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.mipmap.ic_logo);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar = getSupportActionBar();
//        actionBar.setElevation(0);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setTitle("");
    }

    // generate credentials
    public void getValues(){
        emptyTag = "";
        HOST_URL = new ApiKeys().getLink();
        HUMIDATA = new ApiKeys().getHumiData();
        TEMPDATA = new ApiKeys().getTempData();
        CONTROL  = new ApiKeys().getControl();
        INSIDEDATA = new ApiKeys().getInsideData();
        GETTOKEN = new ApiKeys().getGetToken();
        USERNAME = new ApiKeys().getUsername();
        PASSWORD = new ApiKeys().getPublicKey();
        PATTERNSTRING = new ApiKeys().getPatternString();
        MenuObject first = new MenuObject();
        first.setMenuTextAppearanceStyle(R.style.TextViewStyle);
        first.setTitle("refresh");
        first.setDividerColor(R.color.mainPink);
        first.setBgResource(R.color.mainBlack);
        first.setScaleType(ImageView.ScaleType.CENTER);
        first.setResource(R.mipmap.refresh_min);
//        first.setBgColor(R.color.mainBlack);
        MenuObject seconds = new MenuObject();
        seconds.setResource(R.mipmap.shutdown);
        seconds.setScaleType(ImageView.ScaleType.CENTER);
        seconds.setBgResource(R.color.mainBlack);
        seconds.setMenuTextAppearanceStyle(R.style.TextViewStyle);
        seconds.setTitle("wake-on-LAN");
        menuObjects = new ArrayList<>();
        menuObjects.add(first);
        menuObjects.add(seconds);
        menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.menu));
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);



        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);


    }
//    public void generatePrivateToken(){
//        Observable<Response<ResponseBody>> rb = mNewControl.obstest(GETTOKEN)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread());
//        rb.subscribe(responseBodyResponse -> {
//            String key = responseBodyResponse.headers().get("Token");
////            String key = new ApiKeys().encryptToken(responseBodyResponse.headers().get("Token"));
//
//            client = new HttpClient(USERNAME,key, emptyTag, emptyTag).getClient();
//            getHumiData(HUMIDATA);
//        });
//
//    }
    public void setTiles(){
        whichSide = 1;
        tiles.setTitles("Home", "Graphs", "Security");
        tiles.setBackgroundColor(getResources().getColor(R.color.mainActionBg));
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
                    case "SECURITY":
                        loadFragment(new ControlFragment(), bundle, R.anim.from_right, R.anim.to_left); whichSide = 0;  break;

                }
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
    }
    public void reset(){
        humiOrTemp = false;
        cont = 0;
        whichSide = 1;
        controlMode.clear();
        arrayDate.clear();
        arrayValue.clear();
        arrayTime.clear();
        insideArray.clear();
        arrayTempDate.clear();
        arrayTempValue.clear();
        arrayTempTime.clear();
        controlMode.clear();
        controlHighMax.clear();
        controlHighMin.clear();
        controlLowMax.clear();
        controlLowMin.clear();
        controlStatus.clear();
//                generatePrivateToken();
        getHumiData(HUMIDATA);
        tiles.setTabIndex(0, true);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position){
            case 0: reset(); break;
        }

    }
}