package com.belphegor.setstone.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.belphegor.setstone.R;

import java.time.LocalDate;

public class WeightCard {

    public static int id_layout = R.layout.card_weight_info;
    public static int id_date_view = R.id.TV_WEIGHT_CARD_DATE;
    public static int id_weight_view = R.id.TV_WEIGHT_CARD_WEIGHT;
    public static int id_trend_view = R.id.TV_WEIGHT_CARD_TREND_ICON;
    public static int id_trend_icon_up = R.drawable.icon_arrow_up;
    public static int id_trend_icon_down = R.drawable.icon_arrow_down;



    public static LinearLayout createCard(Context context, ViewGroup parent, float weight, String date, float trend){

        LinearLayout card = (LinearLayout) LayoutInflater.from(context).inflate(id_layout,  parent,false);

        TextView dateTV = card.findViewById(id_date_view);
        TextView weightTV = card.findViewById(id_weight_view);
        ImageView trendTV = card.findViewById(id_trend_view);

        dateTV.setText(date);
        weightTV.setText(String.format("%.2f Kg", weight));
        trendTV.setImageResource(
                trend < 0 ? id_trend_icon_down : id_trend_icon_up
        );

        if(trend > 0) {
            trendTV.setColorFilter(ContextCompat.getColor(context, R.color.red_grad), PorterDuff.Mode.SRC_IN);
        }
        else {
            trendTV.setColorFilter(ContextCompat.getColor(context, R.color.green_grad), PorterDuff.Mode.SRC_IN);
        }

        return card;
    }
}
