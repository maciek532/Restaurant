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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milnow5555.restaurantproject.Adapter.MealAdapter;
import com.milnow5555.restaurantproject.ClientOrder;
import com.milnow5555.restaurantproject.Database.FireBaseConnection;
import com.milnow5555.restaurantproject.Dish;
import com.milnow5555.restaurantproject.MainActivity;
import com.milnow5555.restaurantproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyMealsView extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Dish> mealitems;
    public List<Dish> selectedMealItems = new ArrayList<>();
    FireBaseConnection connection;
    Activity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_my_meals_view,container,false);
        connection=new FireBaseConnection("Dishes");
        mainActivity=(MainActivity)getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity.getApplicationContext()));
        List<Dish> dishes=new ArrayList<>();
        List<Object> objlist=new ArrayList<>();



        FirebaseDatabase.getInstance().getReference("Dishes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    for(DataSnapshot ds:child.getChildren())
                    {
                        dishes.add(ds.getValue(Dish.class));
                    }
                }
                adapter = new MealAdapter(dishes,mainActivity.getApplicationContext(),new MealAdapter.OnItemCheckListener() {
                    @Override
                    public void onItemCheck(Dish item,String count) {
                        for(int i = 0; i<(Integer.parseInt(count)); ++i)
                        ClientOrder.getInstance().addMeal(item);
                    }

                    @Override
                    public void onItemUncheck(Dish item) {
                        ClientOrder.getInstance().removeMeal(item.getName());
                    }
                });

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}