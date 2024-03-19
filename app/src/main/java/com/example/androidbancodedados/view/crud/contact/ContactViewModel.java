package com.example.androidbancodedados.view.crud.contact;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbancodedados.data.ContactRepository;
import com.example.androidbancodedados.data.SqlHelper;
import com.example.androidbancodedados.model.Client;
import com.example.androidbancodedados.model.Contact;

import java.util.List;

public class ContactViewModel extends ViewModel {

    MutableLiveData<List<Contact>> contacts;

    public Context context;

    public ContactViewModel() {
        contacts = new MutableLiveData();
    }

    public MutableLiveData<List<Contact>> getLiveData() {
        return contacts;
    }

//    public MutableLiveData<List<Contact>> getContacts(Activity context){
//        SqlHelper sqlHelper = new SqlHelper(context);
//        contacts.setValue(sqlHelper.getContacts());
//        return contacts;
//    }
//
//    public void addContact(Contact contact){
//        SqlHelper sqlHelper = new SqlHelper(context);
//        sqlHelper.addItem(contact);
//    }
//
//    public void editContact(Contact contact){
//        SqlHelper sqlHelper = new SqlHelper(context);
//        sqlHelper.editItem(contact);
//    }
//
//    public void deleteContact(Contact contact){
//        SqlHelper sqlHelper = new SqlHelper(context);
//        sqlHelper.deleteContact(contact);
//    }
}
