package com.example.androidbancodedados.view.crud.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidbancodedados.R;
import com.example.androidbancodedados.databinding.ActivityClientDetailsBinding;
import com.example.androidbancodedados.model.Client;
import com.example.androidbancodedados.model.Contact;
import com.example.androidbancodedados.view.crud.CrudDetailsActivity;
import com.example.androidbancodedados.view.crud.contact.ContactAdapter;
import com.example.androidbancodedados.view.crud.contact.ContactViewModel;

import java.util.List;

public class ClientDetailsActivity extends AppCompatActivity {

    ActivityClientDetailsBinding binding;
    ClientViewModel clientViewModel;

    ContactAdapter contactAdapter = new ContactAdapter();

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityClientDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        updateView();
        setupRecyclerView();
        setupToolbar();
        setupViewModel();
        saveClient();
        createNewContact();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateView();
        setupRecyclerView();
        setupViewModel();
    }

    private void updateView(){
        Intent intent = getIntent();
        if (intent.getSerializableExtra("client") != null) {
            Client client = (Client) getIntent().getSerializableExtra("client");
            this.client = client;
            binding.btnSaveClient.setVisibility(View.GONE);

            binding.etClientName.setText(client.getName());
            binding.etCity.setText(client.getCity());
        }else{
            binding.etClientName.setEnabled(true);
            binding.etCity.setEnabled(true);
        }
    }

    private void setupViewModel(){
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.context = this;

        Intent intent = getIntent();
        if (intent.getSerializableExtra("client") != null) {
            ContactViewModel contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

            contactViewModel.getContacts(client,this);
            contactViewModel.getLiveData().observe(this, data -> {
                updateList(data);
            });
        }

    }

    private void setupRecyclerView(){
        contactAdapter.context = this;
        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        binding.rvContact.setAdapter(contactAdapter);
    }

    private void updateList(List<Contact> contactList){
        if(contactList != null){
            contactAdapter.updateList(contactList);
            contactAdapter.notifyDataSetChanged();
            binding.imgContactsNotFount.setVisibility(View.GONE);
        }else{
            binding.rvContact.setVisibility(View.GONE);
            binding.imgContactsNotFount.setVisibility(View.VISIBLE);
        }
    }

    private void saveClient(){
        binding.btnSaveClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (intent.getSerializableExtra("client") != null) {

                    Client client = (Client) getIntent().getSerializableExtra("client");

                    client.setId(client.getId());
                    client.setName(binding.etClientName.getText().toString());
                    client.setCity(binding.etCity.getText().toString());
                    client.setStatus(true);

                    clientViewModel.editClient(client);
                    finish();
                }else{
                    Client client = new Client();
                    client.setName(binding.etClientName.getText().toString());
                    client.setCity(binding.etCity.getText().toString());
                    client.setStatus(true);

                    clientViewModel.addClient(client);
                    Toast.makeText(ClientDetailsActivity.this, "Cliente salvo", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void deleteContact(){
        clientViewModel.deleteClient(client);
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

    private void createNewContact(){
        binding.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientDetailsActivity.this, CrudDetailsActivity.class);
                intent.putExtra("client", client);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.option_delete){
            deleteConfirmation();
        }
        if(item.getItemId() == R.id.option_edit){
            binding.etClientName.setEnabled(true);
            binding.etCity.setEnabled(true);
            binding.btnSaveClient.setVisibility(View.VISIBLE);
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
        if (intent.getSerializableExtra("client") != null) {
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