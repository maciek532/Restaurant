package com.milnow5555.restaurantproject.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.milnow5555.restaurantproject.Adapter.OrderAdapter;
import com.milnow5555.restaurantproject.CrewOrder;
import com.milnow5555.restaurantproject.MainActivity;
import com.milnow5555.restaurantproject.Order;
import com.milnow5555.restaurantproject.R;

public class CrewOrdersView extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_orders_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        textViewState = (TextView) findViewById(R.id.clientState);
        crewOrder = null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        crewOrder = (CrewOrder) getIntent().getSerializableExtra(SandwichKey);
        makeOrder = (Button) findViewById(R.id.makeOrder);		


        adapter = new OrderAdapter(crewOrder.get_dishes(), getApplicationContext(), new OrderAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(int position) {
                crewOrder.removeMeal(position);
            }
        });
        textViewPrice.setText(String.valueOf(crewOrder.getOrderCost()));
        key = getIntent().getExtras().getString(SandwichKey1);
        textViewState.setText(crewOrder.get_state());
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crewOrder.makeOrder(key);
            }
        });

        recyclerView.setAdapter(adapter);

    }

}
