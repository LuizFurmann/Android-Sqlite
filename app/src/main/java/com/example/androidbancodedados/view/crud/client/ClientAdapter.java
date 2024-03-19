package com.example.androidbancodedados.view.crud.client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbancodedados.databinding.RowClientBinding;
import com.example.androidbancodedados.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    public Context context;
    private List<Client> clientList = new ArrayList<>();

    public void updateList(List<Client> clientList){
        this.clientList.clear();
        this.clientList.addAll(clientList);
    }

    @NonNull
    @Override
    public ClientAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowClientBinding itemBinding = RowClientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ClientViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ClientViewHolder holder, int position) {
        Client currentPosition = clientList.get(position);

        holder.clientName.setText(currentPosition.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClientDetailsActivity.class);
                intent.putExtra("client", currentPosition);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder{

        TextView clientName;

        public ClientViewHolder(@NonNull RowClientBinding binding) {
            super(binding.getRoot());

            clientName = binding.tvUserName;
        }
    }
}
