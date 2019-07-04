package com.example.mamanguo.chooseServices;

import android.content.Intent;
import android.os.Bundle;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseMamaNguo.ChooseMamaNguoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mamanguo.chooseServices.ui.main.SectionsPagerAdapter;

import static com.example.mamanguo.chooseServices.Bill.getBillTotal;
import static com.example.mamanguo.chooseServices.Bill.orderItems;
import static com.example.mamanguo.chooseServices.Bill.orderQuantity;
import static com.example.mamanguo.chooseServices.Bill.orderSubtotal;

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            createIntent();
        });
    }

    private void createIntent() {
        Bundle extras = new Bundle();
        //Retrieved from Bill class
        extras.putStringArray("ORDER_ITEMS", orderItems);
        extras.putIntegerArrayList("ORDER_QUANTITY", orderQuantity);
        extras.putIntegerArrayList("ORDER_SUBTOTAL", orderSubtotal);
        extras.putInt("BILL_TOTAL", getBillTotal());
        Intent intent = new Intent(this, ChooseMamaNguoActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

}