package com.juvvi.careerbuddies;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.FacebookActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.juvvi.careerbuddies.Fragments.AnswerFragment;
import com.juvvi.careerbuddies.Fragments.FollowFragment;
import com.juvvi.careerbuddies.Fragments.GroupsFragment;
import com.juvvi.careerbuddies.Fragments.HomeFragment;
import com.juvvi.careerbuddies.Fragments.NotoficationFragment;
import com.juvvi.careerbuddies.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    ActivityHomeBinding binding;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        setUpToolBar();

        binding.navView.setNavigationItemSelectedListener(this);

        binding.bottomNavView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.follow:
                    replaceFragment(new FollowFragment());
                    break;
                case R.id.answers:
                    replaceFragment(new AnswerFragment());
                    break;
                case R.id.groups:
                    replaceFragment(new GroupsFragment());
                    break;
                case R.id.notification:
                    replaceFragment(new NotoficationFragment());
                    break;
            }

            return true;
        });
    }

    private void setUpToolBar() {
        setSupportActionBar(binding.topAppBar);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.topAppBar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // Inflate the menu; this add items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. This action bar will
        // automatically handle clicks on the Home/up buttons, so long
        // specify the a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        switch (id) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                return true;
            case R.id.add:
                addExperience();
                return true;
            case R.id.search:
                Toast.makeText(this, "Search Query", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addExperience() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottosheetlayout);

        LinearLayout layoutShare = dialog.findViewById(R.id.layoutShare);
        LinearLayout layoutEdit = dialog.findViewById(R.id.layoutEdit);

        layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(HomeActivity.this, "share knowledge", Toast.LENGTH_SHORT).show();
            }
        });

        layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, PostQuestionActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            startLogin();
        }
    }

    private void startLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}