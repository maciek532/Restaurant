package com.milnow5555.restaurantproject;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrewOrder extends Order{

    public CrewOrder(){_dishes=new ArrayList<>();};

    public void setDishes(List<Dish> dishes)
    {
        _dishes=dishes;
    }

    public void makeOrder(String key){
        FirebaseDatabase.getInstance().getReference("Orders").child(key).setValue(this);
        if(_state.equals("Usuń zamówienie"))
        {
            FirebaseDatabase.getInstance().getReference("OrdersHistory").push().setValue(this);
            FirebaseDatabase.getInstance().getReference("Orders").child(key).removeValue();
        }
    }
}
