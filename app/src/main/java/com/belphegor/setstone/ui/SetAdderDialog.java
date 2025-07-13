package com.belphegor.setstone.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;

public class SetAdderDialog {

    final GridLayout GLDialog;

    final static int ET_WEIGHT_ID = R.id.ET_SET_ADDER_DIALOG_WEIGHT;
    final static int ET_REPS_ID = R.id.ET_SET_ADDER_DIALOG_REPS;
    final static int TV_SUBMIT_ID = R.id.TV_SET_ADDER_DIALOG_SUBMIT_BTN;


    EditText ET_WEIGHT, ET_REPS;
    TextView TV_BTN;

    float weight = 0f;
    int reps = 0;

    public SetAdderDialog(GridLayout glDialog) {
        GLDialog = glDialog;
        init();
    }

    public void init(){

        ET_WEIGHT = GLDialog.findViewById(ET_WEIGHT_ID);
        ET_REPS = GLDialog.findViewById(ET_REPS_ID);
        TV_BTN = GLDialog.findViewById(TV_SUBMIT_ID);

    }

    public boolean loadValues(){
        try{
            weight = Float.parseFloat(ET_WEIGHT.getText().toString());
            reps = Integer.parseInt(ET_REPS.getText().toString());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public float getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        TV_BTN.setOnClickListener(onClickListener);
    }

}

