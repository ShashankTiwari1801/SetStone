package com.belphegor.setstone.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.belphegor.setstone.R;

import java.util.ArrayList;
import java.util.List;

public class NavBar {

    Context context;
    View parent;
    LinearLayout LLNavBar;

    int NAVBAR_LAYOUT_ID = R.layout.nav_bar;

    LinearLayout LLButtonContainer;

    List<ImageView> IVBtnList;

    public NavBar(Context context, View parent){
        this.context = context;
        this.parent = parent;

        IVBtnList = new ArrayList<>();
        load();
    }

    public void addParams(){

        int navbar_height = (int)context.getResources().getDimension(R.dimen.navbar_height);
        int bottom_margin = (int)context.getResources().getDimension(R.dimen.space_l);
        int horizontal_margin = (int)context.getResources().getDimension(R.dimen.space_m);

        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
                (ViewGroup.LayoutParams.MATCH_PARENT),
                navbar_height
        );

        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rllp.bottomMargin = bottom_margin;
        rllp.leftMargin = horizontal_margin;
        rllp.rightMargin = horizontal_margin;

        LLNavBar.setLayoutParams(rllp);

    }

    public void loadBtn(){
        for (int i = 0; i < LLButtonContainer.getChildCount(); i++) {
            IVBtnList.add((ImageView) LLButtonContainer.getChildAt(i));
        }
    }

    public void load(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LLNavBar = (LinearLayout) layoutInflater.inflate(NAVBAR_LAYOUT_ID,null, false);

        LLButtonContainer = LLNavBar.findViewById(R.id.LL_NAV_BAR_BTN_CONTAINER);

        addParams();
        loadBtn();

    }

    public LinearLayout getRoot(){
        return LLNavBar;
    }

    public List<ImageView> getBtnList(){
        return IVBtnList;
    }
}
