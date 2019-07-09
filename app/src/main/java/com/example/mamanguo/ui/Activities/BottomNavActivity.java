package com.example.mamanguo.ui.Activities;

import android.app.Notification;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mamanguo.R;
import com.example.mamanguo.ui.Fragments.HomeFragment;
import com.example.mamanguo.ui.Fragments.OrdersFragment;
import com.example.mamanguo.ui.Fragments.ProfileOptionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.mamanguo.helpers.MamaNguo.CHANNEL_1_ID;
import static com.example.mamanguo.helpers.MamaNguo.CHANNEL_2_ID;

public class BottomNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        notificationManager = NotificationManagerCompat.from(this);

        //Set the default fragment
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("ORDER_COMPLETE")) {
                loadFragment(new OrdersFragment());
                navView.setSelectedItemId(R.id.navigation_orders);
            } else if (extras.containsKey("RATING_SUCCESSFUL")) {
                Toast.makeText(BottomNavActivity.this, getString(R.string.rating_success_toast), Toast.LENGTH_SHORT).show();
                loadFragment(new OrdersFragment());
                navView.setSelectedItemId(R.id.navigation_orders);
            }
        } else {
            loadFragment(new HomeFragment());
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_orders:
                fragment = new OrdersFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileOptionsFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void sendNotification(String title, String message) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendNotification() {
        String title = "Accepted";
        String message = "Someone is willing to do your laundry";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

}
