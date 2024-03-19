package com.example.androidbancodedados.view.crud.client;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbancodedados.data.SqlHelper;
import com.example.androidbancodedados.model.Client;
import com.example.androidbancodedados.model.Contact;

import java.util.List;

public class ClientViewModel extends ViewModel {

    Context context;

    MutableLiveData<List<Client>> clients;

    public ClientViewModel() {
        clients = new MutableLiveData();
    }

    public MutableLiveData<List<Client>> getLiveData() {
        return clients;
    }

    public void addClient(Client client) {
        SqlHelper sqlHelper = new SqlHelper(context);
        sqlHelper.addClient(client);
    }

    public MutableLiveData<List<Client>> getClients(Activity context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        clients.setValue(sqlHelper.getClients());
        return clients;
    }

    public void editClient(Client client) {
        SqlHelper sqlHelper = new SqlHelper(context);
        sqlHelper.editClient(client);
    }

    public void deleteClient(Client client) {
        SqlHelper sqlHelper = new SqlHelper(context);
        sqlHelper.deleteClient(client);
    }
}
