package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


public class Campeonato extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static NovoCampeonatoViewModel ncViewModel;
    private static HomeViewModel hvm;

    public static ViewPager vpclass;
    public static TabLayout tab_vp;
    ConstraintLayout clcontainer;
    FragmentManager fragmentManager;
    private int tipoturno;
    public Campeonato() {

    }

    public static Campeonato newInstance(String param1, String param2) {
        Campeonato fragment = new Campeonato();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =inflater.inflate(R.layout.fragment_campeonato, container, false);

        ncViewModel= ViewModelProviders.of(this).get(NovoCampeonatoViewModel.class);
        fragmentManager = getChildFragmentManager();

        hvm= ViewModelProviders.of(this).get(HomeViewModel.class);

        tipoturno=NovoCampeonatoViewModel.getTipoturno();
        Log.i("campativo","tipoturno campeonato : " + tipoturno);
        vpclass = v.findViewById(R.id.vpclass);
        tab_vp = v.findViewById(R.id.tab_vp);
        setupViewPager(vpclass);
        tab_vp.setupWithViewPager (vpclass);

        if(tipoturno==2 || tipoturno==4){
            vpclass.setCurrentItem(1);
        }
        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        switch (tipoturno){
            case 1:
                adapter.addFragment(new Classificacao(),"Classificação");
                adapter.addFragment(new Primeiro_Turno(), "1º Turno");
                break;
            case 3:
                adapter.addFragment(new Classificacao(),"Classificação");
                adapter.addFragment(new Primeiro_Turno(), "1º Turno");
                break;
            case 2:

                adapter.addFragment(new Primeiro_Turno(), "1º Turno");
                adapter.addFragment(new Classificacao(),"Classificação");
                adapter.addFragment(new Segundo_Turno(), "2º Turno");
                break;
            case 4:

                adapter.addFragment(new Primeiro_Turno(), "1º Turno");
                adapter.addFragment(new Classificacao(),"Classificação");
                adapter.addFragment(new Segundo_Turno(), "2º Turno");
                break;
        }

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}