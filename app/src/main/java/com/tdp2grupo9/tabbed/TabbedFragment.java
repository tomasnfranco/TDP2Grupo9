package com.tdp2grupo9.tabbed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.adopcion.BuscarMascotaFragment;
import com.tdp2grupo9.fragment.adopcion.PublicarAdopcionFragment;
import com.tdp2grupo9.fragment.adopcion.ResultadosBusquedaFragment;
import com.tdp2grupo9.fragment.adopcion.UltimasPublicacionesFragment;

import java.util.List;
import java.util.Locale;

public class TabbedFragment extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Context context;
    private TabbedFragment tabbedFragment;

    public static TabbedFragment newInstance() {
        return new TabbedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabbed, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        this.context = v.getContext();
        tabbedFragment = this;
        return v;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new TabbedContentFragment();
            Bundle args = new Bundle();
            args.putInt(TabbedContentFragment.ARG_SECTION_NUMBER, position + 10);
            fragment.setArguments(args);

            switch (position) {
                case 0: return PublicarAdopcionFragment.newInstance();
                case 1: return  UltimasPublicacionesFragment.newInstance();
                case 2: return new Fragment();
                case 3: return ResultadosBusquedaFragment.newInstance();
                case 4: return BuscarMascotaFragment.newInstance(tabbedFragment);
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0: return context.getResources().getString(R.string.publicar_adopcion);
                case 1: return context.getResources().getString(R.string.ultimas_publicaciones);
                case 2: return context.getResources().getString(R.string.publicar_busqueda);
                case 3: return context.getResources().getString(R.string.resultados_busqueda);
                case 4: return context.getResources().getString(R.string.buscar_mascota);
            }
            return "";
        }
    }

    public static class TabbedContentFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        public TabbedContentFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_content,
                    container, false);
            TextView dummyTextView = (TextView) rootView
                    .findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(
                    ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> childFragments = getChildFragmentManager().getFragments();
        if (childFragments != null) {
            for (Fragment fragment : childFragments) {
                if (fragment != null)
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void showBuscarMascotaResults(Bundle bundle) {
        List<Fragment> childFragments = getChildFragmentManager().getFragments();
        if (childFragments != null) {
            for (int i = 0; i < childFragments.size(); i++) {
                Fragment fragment = childFragments.get(i);
                if (fragment instanceof ResultadosBusquedaFragment) {
                    ((ResultadosBusquedaFragment) fragment).startSearch(bundle);
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }

}