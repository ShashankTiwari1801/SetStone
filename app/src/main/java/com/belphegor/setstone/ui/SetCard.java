package com.belphegor.setstone.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;

public class SetCard {

    LinearLayout LLSetCard;
    final LayoutInflater layoutInflater;
    final LinearLayout parent;
    final static int SET_CARD_LAYOUT_ID = R.layout.card_set_info;

    TextView TV_SET_ID, TV_WEIGHT, TV_REPS, TV_CALS;
    ImageView IV_REMOVE;

    public int record_id = -1;

    public SetCard(LinearLayout parent){
        this.parent = parent;
        layoutInflater = LayoutInflater.from(parent.getContext());

        init();

    }

    public void init(){

        LLSetCard = (LinearLayout) layoutInflater.inflate(SET_CARD_LAYOUT_ID, parent, false);

        TV_SET_ID = LLSetCard.findViewById(R.id.TV_SET_CARD_SET_ID);
        TV_CALS = LLSetCard.findViewById(R.id.TV_SET_CARD_CAL_VIEW);
        IV_REMOVE = LLSetCard.findViewById(R.id.TV_SET_CARD_REMOVE);
        TV_WEIGHT = LLSetCard.findViewById(R.id.TV_SET_CARD_WEIGHT);
        TV_REPS = LLSetCard.findViewById(R.id.TV_SET_CARD_REPS);


    }

    public LinearLayout getRoot() {
        return LLSetCard;
    }

    public void makeCard(int setId, float weight, int reps, float calories){
        TV_SET_ID.setText("Set " + String.valueOf(setId));
        TV_WEIGHT.setText(String.format("%.2f Kg", weight));
        TV_REPS.setText("x" + String.valueOf(reps));
        TV_CALS.setText(String.format("%.2f Kcals", calories));
    }

    public void setRecordId(int recordId) {
        this.record_id = recordId;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        IV_REMOVE.setOnClickListener(onClickListener);
    }

}
