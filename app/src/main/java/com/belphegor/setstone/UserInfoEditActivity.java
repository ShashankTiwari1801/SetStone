package com.belphegor.setstone;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.database_manager.DatabaseManager;

public class UserInfoEditActivity extends AppCompatActivity {

    Context context;

    final static int id_user_name_et = R.id.ET_USER_INFO_NAME;
    final static int id_user_age_et = R.id.ET_USER_INFO_AGE;
    final static int id_user_height_et = R.id.ET_USER_INFO_HEIGHT;
    final static int id_save_btn_tv = R.id.TV_USER_INFO_SUBMIT_BTN;

    EditText userNameET, userAgeET, userHeightET;
    TextView saveButtonTV;

    DatabaseManager databaseManager;

    public void init(){
        initViews();
        initUtils();
        initUIElementsAndManagers();
    }

    public void initViews(){
        userNameET = findViewById(id_user_name_et);
        userAgeET = findViewById(id_user_age_et);
        userHeightET = findViewById(id_user_height_et);
        saveButtonTV = findViewById(id_save_btn_tv);
    }
    public void initUtils(){
        databaseManager = new DatabaseManager(context);
    }
    public void initUIElementsAndManagers(){
        saveButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = userNameET.getText().toString();
                int age = Integer.parseInt(userAgeET.getText().toString());
                float height = Float.parseFloat(userHeightET.getText().toString());

                databaseManager.createUserTable();
                databaseManager.updateUserTable(name, height, age);

                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.context = this;

        init();
    }
}