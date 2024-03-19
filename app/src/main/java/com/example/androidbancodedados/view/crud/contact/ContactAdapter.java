package com.example.androidbancodedados.view.crud.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidbancodedados.databinding.RowContactBinding;
import com.example.androidbancodedados.model.Contact;
import com.example.androidbancodedados.view.crud.CrudDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    Context context;
    private List<Contact> listItems = new ArrayList<>();

    public void updateList(List<Contact> listItems){
        this.listItems.clear();
        this.listItems.addAll(listItems);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowContactBinding itemBinding = RowContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        Contact currentPosition = listItems.get(position);

        holder.contactName.setText(currentPosition.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CrudDetailsActivity.class);
                intent.putExtra("contact", currentPosition);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listItems == null)
            return 0;
        else
            return listItems.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{

        TextView contactName;
        public ContactViewHolder(@NonNull RowContactBinding binding) {
            super(binding.getRoot());

            contactName = binding.tvContactName;
        }
    }
}
