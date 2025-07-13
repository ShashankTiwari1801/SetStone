package com.belphegor.setstone;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.WeightGraph;
import com.belphegor.setstone.ui_manager.WeightContentManager;
import com.belphegor.setstone.ui_manager.WeightGraphManager;
import com.belphegor.setstone.util.DateManager;
import com.github.mikephil.charting.charts.LineChart;

import java.time.LocalDate;

public class WeightTrackerActivity extends AppCompatActivity {

    Context context;

    //Views
    final static int id_weight_input_et = R.id.ET_WEIGHT_INPUT;
    EditText weightInputET;
    final static int id_submit_tv = R.id.TV_WEIGHT_SUBMIT;
    TextView weightSubmitTV;

    final static int id_weight_content_ll = R.id.LL_WEIGHT_CONTENT;
    LinearLayout weightContentLL;


    LineChart weightLineChart;

    // Utils

    DatabaseManager databaseManager;

    // UI Elements


    public void init(){
        initViews();
        initUtils();
        initElementsAndManagers();
    }

    private void initUtils() {
        databaseManager = new DatabaseManager(context);
    }

    public void initViews(){
        weightInputET = findViewById(id_weight_input_et);
        weightSubmitTV = findViewById(id_submit_tv);
        weightContentLL = findViewById(id_weight_content_ll);
    }

    public void initElementsAndManagers(){

        WeightContentManager weightContentManager = new WeightContentManager(weightContentLL, databaseManager);

        weightSubmitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float weight = Float.parseFloat(weightInputET.getText().toString());
                String date = LocalDate.now().toString();
                databaseManager.addWeightRecordToTable(date, weight);
                weightContentManager.reload();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_tracker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.context = this;

        init();
    }
}