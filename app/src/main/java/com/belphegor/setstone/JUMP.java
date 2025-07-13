package com.belphegor.setstone;


import android.content.Context;
import android.health.connect.datatypes.ExerciseCompletionGoal;
import android.os.Bundle;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.ui.NavBar;
import com.belphegor.setstone.ui_manager.NavBarManager;
import com.belphegor.setstone.util.Logger;

public class JUMP extends AppCompatActivity {


    private TextView ballImageView;
    Context context;
    RelativeLayout RLMain;

    NavBar navBar;
    NavBarManager navBarManager;

    public void init(){
        this.context = this;
        this.RLMain = findViewById(R.id.main);

        navBar = new NavBar(context, RLMain);
        navBarManager = new NavBarManager(context, navBar, this.getClass());
        RLMain.addView(navBar.getRoot());

        Toast.makeText(this, "JUMP aCitvity", Toast.LENGTH_SHORT).show();
        Logger.dLog("JUMP ACTIVITY STARTED");

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
        setContentView(R.layout.activity_jump);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

    }
}