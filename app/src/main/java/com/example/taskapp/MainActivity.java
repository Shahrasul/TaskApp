package com.example.taskapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.taskapp.ui.home.HomeFragment;
import com.example.taskapp.utils.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    boolean sort = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavController();
        Prefs prefs = new Prefs(this);
        if (prefs.isShown()) {
            navController.navigate(R.id.navigation_home);
        } else {
            navController.navigate(R.id.boardFragment);
        }
    }

    private void initNavController() {
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.profileFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(R.id.navigation_home);
                list.add(R.id.navigation_dashboard);
                list.add(R.id.navigation_notifications);
                list.add(R.id.profileFragment);
                if (list.contains(destination.getId())) {
                    navView.setVisibility(View.VISIBLE);
                } else {
                    navView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sort:
                if (sort) {
                    Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    assert navHost != null;
                    ((HomeFragment) navHost.getChildFragmentManager().getFragments().get(0)).sortByABC();
                    sort = false;
                } else {
                    Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    assert navHost != null;
                    ((HomeFragment) navHost.getChildFragmentManager().getFragments().get(0)).initialList();
                    sort = true;
                }
                return true;
            case R.id.item_time:
                if (sort) {
                    Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    assert navHost != null;
                    ((HomeFragment) navHost.getChildFragmentManager().getFragments().get(0)).sortByTime();
                    sort = false;
                } else {
                    Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    assert navHost != null;
                    ((HomeFragment) navHost.getChildFragmentManager().getFragments().get(0)).initialList();
                    sort = true;
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}