package com.milnow5555.restaurantproject.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.milnow5555.restaurantproject.Adapter.WaitressAdapter;
import com.milnow5555.restaurantproject.CrewOrder;
import com.milnow5555.restaurantproject.MainActivity;
import com.milnow5555.restaurantproject.R;

import java.util.HashMap;
import java.util.Map;

public class WaitressView extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Map<String,CrewOrder> orderitems;
    protected DatabaseReference ordersRef;
    Activity mainActivity;

    public WaitressView() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_my_waitress_view,container,false);
        mainActivity=(MainActivity)getActivity();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        orderitems=new HashMap<>();

        ordersRef= FirebaseDatabase.getInstance().getReference("Orders");

        /*adapter = new WaitressAdapter(orderitems, new WaitressAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(CrewOrder crewOrder) {
                Intent intent=new Intent(WaitressView.this,MyMealsView.class);
                intent.putExtra(MyOrdersView.SandwichKey,crewOrder);

                startActivity(intent);
            }
        });*/

        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    orderitems.put(ds.getKey(),ds.getValue(CrewOrder.class));
                }
                adapter = new WaitressAdapter(orderitems, new WaitressAdapter.OnItemCheckListener(){
                    @Override
                    public void onItemCheck(CrewOrder crewOrder,String key) {
                        Intent intent=new Intent(mainActivity,CrewOrdersView.class);
                        intent.putExtra(MyOrdersView.SandwichKey,crewOrder);
                        intent.putExtra(MyOrdersView.SandwichKey1,key);

                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ordersRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderitems.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    orderitems.put(ds.getKey(),ds.getValue(CrewOrder.class));
                }
                adapter = new WaitressAdapter(orderitems, new WaitressAdapter.OnItemCheckListener(){
                    @Override
                    public void onItemCheck(CrewOrder crewOrder,String key) {
                        Intent intent=new Intent(mainActivity,CrewOrdersView.class);
                        intent.putExtra(MyOrdersView.SandwichKey,crewOrder);
                        intent.putExtra(MyOrdersView.SandwichKey1,key);

                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // adapter = new WaitressAdapter(mealitems);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}
