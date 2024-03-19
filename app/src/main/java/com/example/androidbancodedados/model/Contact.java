package com.example.androidbancodedados.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Contact implements Serializable {

    Integer id;
    String name;
    String phoneNumber;

    LocalDateTime createdDate;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
