package com.milnow5555.restaurantproject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.milnow5555.restaurantproject.ClientOrder;
import com.milnow5555.restaurantproject.Dish;
import com.milnow5555.restaurantproject.Domain.Client;
import com.milnow5555.restaurantproject.R;
import com.milnow5555.restaurantproject.View.MyOrdersView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    private List<Dish> mealitems;
    private Context context;
    OrderAdapter adapter=this;

    public OrderAdapter(List<Dish> mealitems, Context context) {
        this.mealitems = mealitems;
        this.context = context;
    }

    @NonNull
    protected OnItemCheckListener onItemClick;

    public OrderAdapter(List<Dish> mealitems, Context context, @NonNull OnItemCheckListener onItemCheckListener) {
        this.mealitems = mealitems;
        this.context = context;
        this.onItemClick=onItemCheckListener;
    }

    public interface OnItemCheckListener{
        void onItemCheck(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Dish listitem = mealitems.get(position);
        holder.textViewHead.setText(listitem.getName());
        holder.textViewDesc.setText(Double.toString(listitem.getPrice()));
        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemCheck(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, ClientOrder.getInstance().get_dishes().size());
            }
        });

        /*holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealitems.remove(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mealitems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public FloatingActionButton floatingActionButton;


        public ViewHolder(View itemView){
            super(itemView);

            floatingActionButton=(FloatingActionButton) itemView.findViewById((R.id.btncancel));
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);

        }
    }
}