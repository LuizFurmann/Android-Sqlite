package com.example.androidbancodedados.view.crud;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidbancodedados.databinding.FragmentCrudBinding;


public class CrudFragment extends Fragment {

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

        return binding.getRoot();
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