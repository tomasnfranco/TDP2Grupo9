package com.tdp2grupo9.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
    protected void initializeWidgets() {
        createEspecieSpinner();
        createRazaSpinner();
        createSexoSpinner();
        createTamanioSpinner();;
        createEdadSpinner();
        createColorSpinner();
        createCompatibleConSpinner();
        createPapelesDiaSpinner();
        createVacunasDiaSpinner();
        createCastradorSpinner();
        createProteccionSpinner();
        createEnergiaSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //View v = inflater.inflate(R.layout.activity_publicar_mascotas, container, false);
        return viewMain;
    }
}
