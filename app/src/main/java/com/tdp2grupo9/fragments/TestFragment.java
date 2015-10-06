package com.tdp2grupo9.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdp2grupo9.view.SeleccionAtributosFragment;


public class TestFragment extends SeleccionAtributosFragment {

    public static TestFragment newInstance(int page, String title) {
        TestFragment testFragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        testFragment.setArguments(args);
        return testFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //View v = inflater.inflate(R.layout.activity_publicar_mascotas, container, false);
        return viewMain;
    }
}
