package com.example.soram.iasbp;
import android.content.DialogInterface;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import io.github.tonnyl.light.Light;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    ArrayList<String> controlMode = new ArrayList<>();
    ArrayList<String> controlStatus = new ArrayList<String>();
    ArrayList<String> controlLowMin = new ArrayList<String>();
    ArrayList<String> controlLowMax = new ArrayList<String>();
    ArrayList<String> controlHighMin = new ArrayList<String>();
    ArrayList<String> controlHighMax = new ArrayList<String>();
    Integer cont = 0;
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
    String emptyTag;
    PatternLockView patternLockView;
    String PATTERNSTRING;
    Toolbar toolbar;
    ImageView iasLogo;
    ImageView menu;
    List<MenuObject> menuObjects;
    MenuParams menuParams;
    ContextMenuDialogFragment mMenuDialogFragment;
    FragmentManager fragmentManager;
    RetrofitModel retrofitModel;



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
//                    client = new HttpClient(USERNAME,PASSWORD, emptyTag, emptyTag).getClient();
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

        Call<List<GetHumiData>> call = retrofitModel.sqlData(url);
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

        Call<List<GetControlData>> call = retrofitModel.controlData(CONTROL);
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

        Call<List<GetInsideData>> call = retrofitModel.insideData(INSIDEDATA);
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
//        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.ic_refresh));



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
        retrofitModel = new RetrofitClient().getRetrofitClient(emptyTag, emptyTag);

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
        seconds.setDividerColor(R.color.mainPink);
        seconds.setMenuTextAppearanceStyle(R.style.TextViewStyle);
        seconds.setTitle("wake-on-LAN");

        MenuObject exit = new MenuObject();
        exit.setResource(R.mipmap.exit);
        exit.setTitle("exit");
        exit.setMenuTextAppearanceStyle(R.style.TextViewStyle);
        exit.setDividerColor(R.color.mainPink);
        exit.setBgResource(R.color.mainBlack);
        exit.setScaleType(ImageView.ScaleType.CENTER);
        menuObjects = new ArrayList<>();
        menuObjects.add(first);
        menuObjects.add(seconds);
        menuObjects.add(exit);
        menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.menu));
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);



        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);


    }

    public void setTiles(){
        whichSide = 1;
        tiles.setTitles("Home", "Stats", "Energy", "Security");
        tiles.setBackgroundColor(getResources().getColor(R.color.mainTilesBg));
        tiles.setInactiveColor(getResources().getColor(R.color.greenNavi));
        tiles.setActiveColor(getResources().getColor(R.color.greenNavi));
        tiles.setTabIndex(0, true);
        tiles.setStripColor(getResources().getColor(R.color.greenNavi));
        tiles.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                switch (title){
                    case "STATS":  if (whichSide == 0){
                        loadFragment(new GraphFragment(), bundle, R.anim.from_left, R.anim.to_right); break;
                    }else{
                        loadFragment(new GraphFragment(), bundle, R.anim.from_right, R.anim.to_left); break;
                    }
                    case "HOME":    loadFragment(new MainFragment(), bundle, R.anim.from_left, R.anim.to_right); whichSide = 1;  break;
                    case "SECURITY":
                        loadFragment(new ControlFragment(), bundle, R.anim.from_right, R.anim.to_left); whichSide = 0;  break;
                    case "ENERGY":  break;

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
        if (retrofitModel != null){
            getHumiData(HUMIDATA);
        }

        tiles.setTabIndex(0, true);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position){
            case 0: reset(); break;
            case 1: wakeOnLanDialog(); break;

            case 2: finishAndRemoveTask(); break;
        }

    }
    public void addAddressDialog(){
        final EditText first = new EditText(this);
        final EditText second = new EditText(this);
        final LinearLayout ln = new LinearLayout(this);
        final TextView title = new TextView(this);
        title.setText(R.string.SendMP);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0,25,0,0);

        first.setHint("IP address");
        second.setHint("MAC address");
        first.setGravity(Gravity.CENTER_HORIZONTAL);
        second.setGravity(Gravity.CENTER_HORIZONTAL);
        first.setTextColor(getResources().getColor(R.color.mainPink));
        second.setTextColor(getResources().getColor(R.color.mainPink));
        title.setTextColor(getResources().getColor(R.color.mainPink));
        first.setHintTextColor(getResources().getColor(R.color.tiles_inactive));
        second.setHintTextColor(getResources().getColor(R.color.tiles_inactive));
        first.setPadding(0,15,0,15);
        second.setPadding(0,15,0,15);
        ln.setOrientation(LinearLayout.VERTICAL);
        ln.setGravity(Gravity.CENTER);
        ln.setBackgroundColor(getResources().getColor(R.color.gray));

        ln.addView(first, 500,100);
        ln.addView(second,500,100);
        new android.app.AlertDialog.Builder(this, R.style.dialogTheme)
                .setCustomTitle(title)
                .setView(ln)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ip = first.getText().toString();
                        String mac = second.getText().toString();
                        if (ip.equals("1")){
                            new Control().wakeOnLan("192.168.1.104", "D0:50:99:88:E1:38");
                        }else if(ip.equals("2"))
                            {
                                new Control().wakeOnLan("192.168.1.103", "00:26:5e:42:1b:fe");

                            }else {
                            if (ip.isEmpty() || mac.isEmpty()){
                                Light.warning(patternLockView, "Wrong format.", Snackbar.LENGTH_SHORT).show();
                            }else {
                                new Control().wakeOnLan(ip, mac);
                            }

                        }




                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }

    public void wakeOnLanDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.DialogStyle);
//        alertDialog.setTitle("Tap to send Magic packet.");
        TextView title = new TextView(this);
        title.setText(R.string.wakeUp);
        title.setTextColor(getResources().getColor(R.color.mainActionBg));
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 10, 10, 10);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        String[] list = {"Home PC", "Acer Laptop", "Lenovo Laptop", "Custom"};


        alertDialog.setItems(list, (dialogInterface, i) -> {
            switch (i){
                case 0: new Control().wakeOnLan("192.168.1.103", "00:26:5e:42:1b:fe"); break;
                case 1: new Control().wakeOnLan("192.168.1.103", "00:26:5e:42:1b:fe"); break;
                case 2: break;
                case 3: addAddressDialog(); break;

            }
        });

        AlertDialog dialog = alertDialog.create();

        dialog.show();


    }

}