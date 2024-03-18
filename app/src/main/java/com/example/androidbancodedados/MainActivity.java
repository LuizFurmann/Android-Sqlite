package com.example.androidbancodedados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.androidbancodedados.databinding.ActivityMainBinding;
import com.example.androidbancodedados.view.crud.CrudFragment;
import com.example.androidbancodedados.view.internet.InternetFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        replaceFragment(new CrudFragment());;
        selectOption();
    }

    private void selectOption(){
        binding.navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItemId =  item.getItemId();

                if (menuItemId ==  R.id.navigator_crud) {
                    replaceFragment(new CrudFragment());
                    setTitle("Dados do cliente");
                    return true;
                }
                else if (menuItemId ==  R.id.navigator_web) {
                    replaceFragment(new InternetFragment());
                    setTitle("Hist. de pedidos");
                    return true;
                }
                return true;
            }
        });
    }

    private  void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}