package com.belphegor.setstone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.NavBar;
import com.belphegor.setstone.ui.WeightGraph;
import com.belphegor.setstone.ui_manager.NavBarManager;
import com.belphegor.setstone.ui_manager.WeightGraphManager;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

public class ProfilePageActivity extends AppCompatActivity {

    Context context;

    // Views
    RelativeLayout RLMain;
    LinearLayout waterTrackerLL;

    final static int id_weight_line_chart = R.id.LC_BODY_WEIGHT;
    LineChart weightLineChart;


    final static int id_week_start_weight_text = R.id.TV_PROFILE_PAGE_WEIGHT_START;
    final static int id_week_end_weight_text = R.id.TV_PROFILE_PAGE_WEIGHT_END;
    final static int id_week_delta_weight_text = R.id.TV_PROFILE_PAGE_WEIGHT_DELTA;
    final static int id_weight_display_card_ll = R.id.LL_WEIGHT_DISPLAY_CARD;
    final static int id_weight_thumbs = R.id.IV_THUMBS;

    TextView weightStartText, weightEndText, weightDeltaText;
    LinearLayout weightDisplayCard;
    ImageView thumbsImage;

    final static int id_user_info_card_ll = R.id.LL_USER_INFO_CARD;
    final static int id_user_name_tv = R.id.TV_USER_NAME_DISPLAY;
    final static int id_user_age_tv = R.id.TV_USER_AGE_DISPLAY;
    final static int id_user_weight_tv = R.id.TV_USER_WEIGHT_DISPLAY;
    final static int id_user_height_tv = R.id.TV_USER_HEIGHT_DISPLAY;
    TextView userNameTV, userHeightTV, userWeightTV, userAgeTV;

    LinearLayout userInfoCardLL;

    final static int id_user_bmi_tv = R.id.TV_PROFILE_PAGE_BMI;
    TextView userBmiTV;

    // Utils
    DatabaseManager databaseManager;

    // Custom Views and Managers

    NavBar navBar;
    NavBarManager navBarManager;

    WeightGraph weightGraph;
    WeightGraphManager weightGraphManager;


    public void init(){
        initViews();
        initUtils();
        initElementsAndManagers();
        loadUserInfo();
    }

    private void initUtils() {
        databaseManager = new DatabaseManager(context);
        databaseManager.createUserTable();
    }

    public void initViews(){
        RLMain = findViewById(R.id.main);
        weightLineChart = findViewById(id_weight_line_chart);
        weightStartText = findViewById(id_week_start_weight_text);
        weightEndText = findViewById(id_week_end_weight_text);
        weightDeltaText = findViewById(id_week_delta_weight_text);
        weightDisplayCard = findViewById(id_weight_display_card_ll);
        thumbsImage = findViewById(id_weight_thumbs);
        userInfoCardLL = findViewById(id_user_info_card_ll);
        userAgeTV = findViewById(id_user_age_tv);
        userWeightTV = findViewById(id_user_weight_tv);
        userNameTV = findViewById(id_user_name_tv);
        userHeightTV = findViewById(id_user_height_tv);
        userBmiTV = findViewById(id_user_bmi_tv);
    }

    public void initElementsAndManagers(){

        navBar = new NavBar(context, RLMain);
        navBarManager = new NavBarManager(context, navBar, context.getClass());

        RLMain.addView(navBar.getRoot());

        weightGraph = new WeightGraph(context, weightLineChart);
        weightGraphManager = new WeightGraphManager(weightGraph, databaseManager, context);
        WeightGraphManager.fillStartEndValue(weightStartText, weightEndText, weightDeltaText, thumbsImage,weightGraphManager.getWeekData());
        weightGraph.invalidate();
        weightDisplayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeightTrackerActivity.class);
                startActivity(intent);
            }
        });

        userInfoCardLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserInfoEditActivity.class);
                startActivity(intent);
            }
        });

    }

    public void loadUserInfo(){
        List<List<String>> rawUserData = databaseManager.getUserRecord();
        System.out.println(rawUserData);

        if(rawUserData.size() > 0){
            userNameTV.setText(rawUserData.get(0).get(0));
            userHeightTV.setText(rawUserData.get(0).get(1));
            userAgeTV.setText(rawUserData.get(0).get(2));
        }

        userWeightTV.setText(String.format("%.2f", databaseManager.getCurrentBodyWeight()));

        try{
            float weight = databaseManager.getCurrentBodyWeight();
            float height = Float.parseFloat(userHeightTV.getText().toString());
            float bmi = weight / ((height/100) * (height/100));
            userBmiTV.setText(String.format("%.2f", bmi));
        }
        catch (Exception e){

        }

    }

    public void initUIComponents(){

        context = this;

        waterTrackerLL = findViewById(R.id.LL_WATER_CONSUMPTION_CARD);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        this.context = this;


        init();
        initUIComponents();

        String date = "2025-01-01";
        int exercise_id = 32;
        float weight = 32.0f;
        int reps = 10;

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();

    }
}