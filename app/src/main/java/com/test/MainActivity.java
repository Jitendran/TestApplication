package com.test;

import android.os.Bundle;
import android.view.View;

import com.test.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import io.reactivex.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    public static final BehaviorSubject<String> RX = BehaviorSubject.createDefault("Starting");
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigateUp();
        activityMainBinding.contentMain.logView.setLayoutManager(new LinearLayoutManager(this));

        Subscriber.init(activityMainBinding.contentMain.logView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Subscriber.done();
        activityMainBinding = null;
        navController = null;
        super.onDestroy();
    }
}