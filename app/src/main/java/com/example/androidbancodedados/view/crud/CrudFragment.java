package com.example.androidbancodedados.view.crud;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidbancodedados.databinding.FragmentCrudBinding;
import com.example.androidbancodedados.model.Client;
import com.example.androidbancodedados.model.Contact;
import com.example.androidbancodedados.view.crud.client.ClientAdapter;
import com.example.androidbancodedados.view.crud.client.ClientDetailsActivity;
import com.example.androidbancodedados.view.crud.client.ClientViewModel;
import com.example.androidbancodedados.view.crud.contact.ContactAdapter;
import com.example.androidbancodedados.view.crud.contact.ContactViewModel;

import java.util.List;


public class CrudFragment extends Fragment {

    private ClientAdapter clientAdapter = new ClientAdapter();
    FragmentCrudBinding binding;
    public CrudFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrudBinding.inflate(inflater, container, false);

        createNewClient();
        setupViewModel();
        setupRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        setupViewModel();
        setupRecyclerView();
    }

    private void setupViewModel(){
        ClientViewModel viewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);

        viewModel.getClients(requireActivity());
        viewModel.getLiveData().observe(requireActivity(), data -> {
            updateList(data);
        });
    }

    private void setupRecyclerView(){
        clientAdapter.context = requireActivity();
        binding.rvClient.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvClient.setAdapter(clientAdapter);
    }

    private void updateList(List<Client> clientList){
        clientAdapter.updateList(clientList);
        clientAdapter.notifyDataSetChanged();
    }
    private void createNewClient(){
        binding.fabNewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ClientDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}