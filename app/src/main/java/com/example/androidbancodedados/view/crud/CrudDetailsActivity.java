package com.example.androidbancodedados.view.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
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

    ContactViewModel contactViewModel;

    SqlHelper sqlHelper = new SqlHelper(this);

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCrudDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        setupToolbar();
        setupViewModel();
        saveContact();
        updateView();
    }

    private void setupViewModel(){
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.context = this;
    }

    private void updateView(){
        Intent intent = getIntent();
        if (intent.getSerializableExtra("contact") != null) {
            Contact contact = (Contact) getIntent().getSerializableExtra("contact");
            this.contact = contact;
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

                Intent intent = getIntent();
                if (intent.getSerializableExtra("contact") != null) {

                    Contact contact = (Contact) getIntent().getSerializableExtra("contact");

                    contact.setId(contact.getId());
                    contact.setName(binding.etName.getText().toString());
                    contact.setPhoneNumber(binding.etContact.getText().toString());
                    contactViewModel.editContact(contact);
                    finish();
                }else{
                    Contact contact = new Contact();
                    contact.setName(binding.etName.getText().toString());
                    contact.setPhoneNumber(binding.etContact.getText().toString());

                    contactViewModel.addContact(contact);
                    Toast.makeText(CrudDetailsActivity.this, "Contato salvo", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void deleteContact(){
        contactViewModel.deleteContact(contact);
    }

    private void deleteConfirmation(){
        new AlertDialog.Builder(this)
                .setTitle("Deletar")
                .setMessage("Deseja deletar o contato?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteContact();
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.option_delete){
            deleteConfirmation();
        }
        if(item.getItemId() == R.id.option_edit){
            binding.etName.setEnabled(true);
            binding.etContact.setEnabled(true);
            binding.btnSave.setVisibility(View.VISIBLE);
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
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

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}