package com.example.gimnasio_unne;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AlumnoActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActionBarDrawerToggle toggle;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //cerrar sesion con shared preferences
        preferences=getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //el drawer layout esta en activity_alumno
        DrawerLayout drawer = findViewById(R.id.drawer_layout_alumno);
        NavigationView navigationView = findViewById(R.id.nav_view_alumno);

        //clase para implementar el icono hamburguesa
        toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.nav_app_bar_open_drawer_description, R.string.drawer_close);
        drawer.addDrawerListener(toggle);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                //es el identificador del menu que se encuenta en menu/menu_alumno
                R.id.fragmentListarCuposLibres, R.id.fragmentMisReservas)
                .setDrawerLayout(drawer)
                .build();
        //nav_host_fragment_alumno se encuentra en content_alumno
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_alumno);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    //selector de opciones para cerrar sesion
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_alumno);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                editor.putBoolean("sesion", false);
                editor.apply();
                finish();
                startActivity(new Intent(this, Login.class));
                break;
        }
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }
}
