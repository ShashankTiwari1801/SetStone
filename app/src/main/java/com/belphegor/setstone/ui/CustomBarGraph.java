package com.belphegor.setstone.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public class CustomBarGraph {
    final ViewGroup parent;
    ViewGroup graph;

    public CustomBarGraph(ViewGroup parent) {
        this.parent = parent;
        load();
    }

    public void load(){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

    }
}
