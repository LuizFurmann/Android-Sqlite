package com.example.androidbancodedados.view.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.androidbancodedados.R;
import com.example.androidbancodedados.data.SqlHelper;
import com.example.androidbancodedados.databinding.ActivityCrudDetailsBinding;
import com.example.androidbancodedados.model.Contact;

public class CrudDetailsActivity extends AppCompatActivity {


    ActivityCrudDetailsBinding binding;

    SqlHelper sqlHelper = new SqlHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCrudDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        saveContact();
        updateView();
    }

    private void updateView(){
        Intent intent = getIntent();
        if (intent.getSerializableExtra("contact") != null) {
            Contact contact = (Contact) getIntent().getSerializableExtra("contact");
            binding.btnSave.setVisibility(View.GONE);

            binding.etName.setText(contact.getName());
            binding.etContact.setText(contact.getPhoneNumber());
        }else{
            binding.etName.setEnabled(true);
            binding.etContact.setEnabled(true);
        }
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
                    finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.option_delete){

        }
        if(item.getItemId() == R.id.option_edit){
            binding.etName.setEnabled(true);
            binding.etContact.setEnabled(true);
            binding.btnSave.setVisibility(View.VISIBLE);
        }
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        Intent intent = getIntent();
        if (intent.getSerializableExtra("contact") != null) {
            inflater.inflate(R.menu.option_menu, menu);
            return true;
        }
        return true;
    }
}