package com.belphegor.setstone.ui_manager;

import android.widget.LinearLayout;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.WeightCard;

import java.util.Collections;
import java.util.List;

public class WeightContentManager {

    final LinearLayout weightContentContainerLL;
    final DatabaseManager databaseManager;

    public WeightContentManager(LinearLayout weightContentContainerLL, DatabaseManager databaseManager) {
        this.weightContentContainerLL = weightContentContainerLL;
        this.databaseManager = databaseManager;

        init();
    }

    public void reload(){
        init();
    }

    private void init() {
        weightContentContainerLL.removeAllViews();
        List<List<String>> rawRecord = databaseManager.getLastNWeightRecord(50);
        Collections.reverse(rawRecord);

        float prev = 0;

        for (int i = 0; i < rawRecord.size(); i++) {
            float weight = Float.parseFloat(rawRecord.get(i).get(1));
            float delta = weight - prev;
            String date = rawRecord.get(i).get(0);

            weightContentContainerLL.addView(
                    WeightCard.createCard(weightContentContainerLL.getContext(), weightContentContainerLL, weight, date, delta)
                    ,0);

            prev = weight;
        }

        System.out.println(rawRecord);
    }
}
