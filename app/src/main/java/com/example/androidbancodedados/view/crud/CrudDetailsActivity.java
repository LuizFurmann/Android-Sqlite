package com.example.androidbancodedados.view.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidbancodedados.data.SqlHelper;
import com.example.androidbancodedados.databinding.ActivityCrudDetailsBinding;

public class CrudDetailsActivity extends AppCompatActivity {


    ActivityCrudDetailsBinding binding;

    SqlHelper sqlHelper = new SqlHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCrudDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        saveContact();
    }

    private void saveContact(){
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new Thread(() ->{
//                    long contactId = sqlHelper.addItem(binding.etName.getText().toString(), binding.etContact.getText().toString());
//
//                    runOnUiThread(() ->{
//                        if(contactId > 0)
//                            Toast.makeText(CrudDetailsActivity.this, "Contato salvo", Toast.LENGTH_SHORT).show();
//                    });
//                });

                long contactId = sqlHelper.addItem(binding.etName.getText().toString(), binding.etContact.getText().toString());
                if(contactId > 0)
                    Toast.makeText(CrudDetailsActivity.this, "Contato salvo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}