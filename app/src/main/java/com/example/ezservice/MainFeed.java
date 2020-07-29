package com.example.ezservice;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.ezservice.adapters.ViewPagerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainFeed extends AppCompatActivity {

    TextView btn_NombreApp;
    ImageView btn_messages,btn_search;
    TabLayout tabLayout;
    int[] tabIcons={R.drawable.ic_search,R.drawable.ic_profile,R.drawable.ic_configuration};
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //Relaciones------------------------
        btn_NombreApp = findViewById(R.id.btn_nombreApp);
        btn_messages = findViewById(R.id.btn_messages);
        btn_search = findViewById(R.id.btn_search);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.ly_TabLayout);

        //Carga de ViewPager
        loadViewPager(tabLayout);

        //Carga de íconos
        tabIcons(tabLayout);

        initializeIconColors(tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                iconColor(tab,"#63a4ff");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                iconColor(tab,"#E0E0E0");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainFeed.this, "Ir a mensajes", Toast.LENGTH_SHORT).show();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainFeed.this, "Ir a búsqueda", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadViewPager(TabLayout tablayout){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Solicitar(),"");
        adapter.addFragment(new Fragment_Lista(),"");
        adapter.addFragment(new Fragment_Configuration(),"");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

    private void tabIcons(TabLayout tablayout){
        for(int i=0;i<3;i++){
            tablayout.getTabAt(i).setIcon(tabIcons[i]);
            iconColor(tablayout.getTabAt(i),"#E0E0E0");
        }
    }

    private void iconColor(TabLayout.Tab tab, String color){
        tab.getIcon().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
    }

    private void initializeIconColors(TabLayout tablayout){
        iconColor(tablayout.getTabAt(tabLayout.getSelectedTabPosition()),"#63a4ff");
    }
}