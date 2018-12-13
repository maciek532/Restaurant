package com.milnow5555.restaurantproject.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milnow5555.restaurantproject.Adapter.KitchenAdapter;
import com.milnow5555.restaurantproject.CrewOrder;
import com.milnow5555.restaurantproject.Dish;
import com.milnow5555.restaurantproject.R;

import java.util.ArrayList;
import java.util.List;


public class KitchenView extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CrewOrder> orderitems;
    protected DatabaseReference ordersRef;

    public KitchenView() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_kitchen_view, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        /*mealitems = new ArrayList<>();
        for(int i=0 ;i<20;i++){
            Dish item = new Dish("Meal" + i,100,null);
            mealitems.add(item);
        }*/
        ordersRef=FirebaseDatabase.getInstance().getReference("Orders");
        OrdersListener ordersListener=new OrdersListener();

        ordersRef.addListenerForSingleValueEvent(ordersListener);
        ordersRef.addValueEventListener(ordersListener);


        //adapter = new KitchenAdapter(mealitems);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return rootView;

    }

    static class OrdersListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }



}
