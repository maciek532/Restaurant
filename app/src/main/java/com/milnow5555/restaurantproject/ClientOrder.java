package com.milnow5555.restaurantproject;

import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClientOrder extends Order {

    private static volatile ClientOrder instance = null;
    private DatabaseReference orderReference=null;

    public static ClientOrder getInstance() {
        if (instance == null) {
            synchronized (ClientOrder.class) {
                if (instance == null) {
                    instance = new ClientOrder();
                }
            }

        }
        return instance;
    }

    private ClientOrder() {
        _dishes = new ArrayList<>();
    }

    public DatabaseReference makeOrder() {
        if (orderReference == null) {
            orderReference = FirebaseDatabase.getInstance().getReference("Orders").push();
        }
        if(_state.equals("Tworzenie zamówienia"))
            orderReference.setValue(this);

        return orderReference;
    }
}
