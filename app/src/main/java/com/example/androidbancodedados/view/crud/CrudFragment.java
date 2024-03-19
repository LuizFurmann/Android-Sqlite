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
import com.example.androidbancodedados.model.Contact;

import java.util.List;


public class CrudFragment extends Fragment {

    private ContactAdapter contactAdapter = new ContactAdapter();
    FragmentCrudBinding binding;
    public CrudFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrudBinding.inflate(inflater, container, false);

        createNewContact();
        setupViewModel();
        setupRecyclerView();

        return binding.getRoot();
    }

    private void setupViewModel(){
        ContactViewModel viewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);

        viewModel.getContacts(requireActivity());
        viewModel.getLiveData().observe(requireActivity(), data -> {
            updateList(data);
        });
    }

    private void setupRecyclerView(){
        contactAdapter.context = requireActivity();
        binding.rvContact.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvContact.setAdapter(contactAdapter);
    }

    private void updateList(List<Contact> contactList){
        contactAdapter.updateList(contactList);
        contactAdapter.notifyDataSetChanged();
    }
    private void createNewContact(){
        binding.fabNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), CrudDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}