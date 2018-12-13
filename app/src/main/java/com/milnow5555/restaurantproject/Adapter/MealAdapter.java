package com.milnow5555.restaurantproject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.milnow5555.restaurantproject.Dish;
import com.milnow5555.restaurantproject.R;
import com.milnow5555.restaurantproject.View.MyMealsView;
import com.milnow5555.restaurantproject.View.MyOrdersView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    public MealAdapter(List<Dish> mealitems, Context context) {
        this.mealitems = mealitems;
        this.context = context;
    }

    public interface OnItemCheckListener {
        void onItemCheck(Dish item, String number);

        void onItemUncheck(Dish item);
    }

    @NonNull
    protected OnItemCheckListener onItemClick;

    private List<Dish> mealitems;
    private Context context;

    public MealAdapter(List<Dish> mealitems, Context context, @NonNull OnItemCheckListener onItemCheckListener) {
        this.mealitems = mealitems;
        this.context = context;
        this.onItemClick = onItemCheckListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Dish listitem = mealitems.get(position);
        holder.textViewHead.setText(listitem.getName());
        holder.textViewDesc.setText(Double.toString(listitem.getPrice()));

        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onItemClick.onItemCheck(listitem,holder.elegantNumberButton.getNumber());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mealitems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public FloatingActionButton floatingActionButton;
        public ElegantNumberButton elegantNumberButton;
        //public CheckBox checkbox_meal;

        public ViewHolder(View itemView){
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            floatingActionButton = (FloatingActionButton) itemView.findViewById(R.id.btnorder);
            elegantNumberButton = (ElegantNumberButton) itemView.findViewById(R.id.myButton);
            //checkbox_meal = (CheckBox) itemView.findViewById(R.id.checkbox_meat);

        }
    }
}