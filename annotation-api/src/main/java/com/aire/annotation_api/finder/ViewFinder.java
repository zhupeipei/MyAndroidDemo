package com.aire.annotation_api.finder;

import android.view.View;


public class ViewFinder implements Finder {

    @Override
    public View findView(Object source, int id) {
        return ((View) source).findViewById(id);
    }
}