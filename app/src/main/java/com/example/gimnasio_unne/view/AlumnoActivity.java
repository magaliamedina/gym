package com.example.gimnasio_unne.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.gimnasio_unne.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AlumnoActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //el drawer layout esta en activity_alumno
        DrawerLayout drawer = findViewById(R.id.drawer_layout_alumno);
        NavigationView navigationView = findViewById(R.id.nav_view_alumno);

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
                finish();
                startActivity(new Intent(this, Login.class));
                break;
        }
        return true;
    }
}
