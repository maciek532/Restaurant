package com.milnow5555.restaurantproject.View;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.milnow5555.restaurantproject.Adapter.OrderAdapter;
import com.milnow5555.restaurantproject.ClientOrder;
import com.milnow5555.restaurantproject.CrewOrder;
import com.milnow5555.restaurantproject.MainActivity;
import com.milnow5555.restaurantproject.Order;
import com.milnow5555.restaurantproject.R;

public class MyOrdersView extends Fragment {

    private MyMealsView MM;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Order orderItems;
    private TextView textViewPrice;
    private Button makeOrder;
    public static final String SandwichKey="key1";
    public static final String SandwichKey1="key2";
    private TextView textViewState;
    CrewOrder crewOrder;
    String key;
    Activity mainActivity;
    DatabaseReference orderRef;
    boolean first=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_my_orders_view,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mainActivity=(MainActivity)getActivity();
        textViewState=(TextView)view.findViewById(R.id.clientState);
        crewOrder=null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity.getApplicationContext()));
        textViewPrice= (TextView) view.findViewById(R.id.textViewPrice);
        crewOrder=(CrewOrder)mainActivity.getIntent().getSerializableExtra(SandwichKey);
        makeOrder=(Button)view.findViewById(R.id.makeOrder);

        if(crewOrder==null) {
            adapter = new OrderAdapter(ClientOrder.getInstance().get_dishes(), mainActivity.getApplicationContext(), new OrderAdapter.OnItemCheckListener() {
                @Override
                public void onItemCheck(int position) {
                    ClientOrder.getInstance().removeMeal(position);
                }
            });
            textViewPrice.setText(String.valueOf(ClientOrder.getInstance().getOrderCost()));
            textViewState.setText(ClientOrder.getInstance().get_state());
            makeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderRef=ClientOrder.getInstance().makeOrder();
                    if(first) {
                        orderRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ClientOrder order = dataSnapshot.getValue(ClientOrder.class);
                                ClientOrder.getInstance().set_dishes(order.get_dishes());
                                ClientOrder.getInstance().set_table(order.get_table());
                                ClientOrder.getInstance().set_state(order.get_state());

                                adapter = new OrderAdapter(ClientOrder.getInstance().get_dishes(), mainActivity.getApplicationContext(), new OrderAdapter.OnItemCheckListener() {
                                    @Override
                                    public void onItemCheck(int position) {
                                        ClientOrder.getInstance().removeMeal(position);
                                    }
                                });
                                textViewPrice.setText(String.valueOf(ClientOrder.getInstance().getOrderCost()));
                                textViewState.setText(ClientOrder.getInstance().get_state());

                                recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        first=false;
                    }
                }
            });
        } else {
            adapter = new OrderAdapter(crewOrder.get_dishes(), mainActivity.getApplicationContext(), new OrderAdapter.OnItemCheckListener() {
                @Override
                public void onItemCheck(int position) {
                    crewOrder.removeMeal(position);
                }
            });
            textViewPrice.setText(String.valueOf(crewOrder.getOrderCost()));
            key=mainActivity.getIntent().getExtras().getString(SandwichKey1);
            textViewState.setText(crewOrder.get_state());
            makeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    crewOrder.makeOrder(key);
                }
            });
        }
        recyclerView.setAdapter(adapter);

        return view;
    }



}
