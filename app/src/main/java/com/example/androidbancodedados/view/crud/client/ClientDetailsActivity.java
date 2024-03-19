package com.example.androidbancodedados.view.crud.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidbancodedados.databinding.ActivityClientDetailsBinding;
import com.example.androidbancodedados.model.Client;
import com.example.androidbancodedados.model.Contact;

public class ClientDetailsActivity extends AppCompatActivity {

    ActivityClientDetailsBinding binding;
    ClientViewModel clientViewModel;

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityClientDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        setupViewModel();
        saveClient();
        updateView();
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

                    clientViewModel.addClient(client);
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
}