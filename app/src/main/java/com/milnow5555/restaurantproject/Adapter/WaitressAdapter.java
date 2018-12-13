package com.milnow5555.restaurantproject.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.milnow5555.restaurantproject.CrewOrder;
import com.milnow5555.restaurantproject.Dish;
import com.milnow5555.restaurantproject.R;
import com.milnow5555.restaurantproject.View.MyMealsView;
import com.milnow5555.restaurantproject.View.WaitressView;

import java.util.List;
import java.util.Map;

public class WaitressAdapter extends RecyclerView.Adapter<WaitressAdapter.ViewHolder> {


    public interface OnItemCheckListener {
        void onItemCheck(CrewOrder crewOrder,String key);
    }

    private Map<String,CrewOrder> orderitem;
    @NonNull
    protected OnItemCheckListener onItemClick;

    public WaitressAdapter(Map<String,CrewOrder> orderitem) {
        this.orderitem= orderitem;
    }

    public WaitressAdapter(Map<String,CrewOrder> orderitem, @NonNull OnItemCheckListener onItemCheckListener) {
        this.orderitem= orderitem;
        this.onItemClick = onItemCheckListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.waitress_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final CrewOrder listitem = getItem(position);
        holder.textViewHead.setText(String.valueOf(listitem.get_table()));
        holder.textViewDesc.setText(Double.toString(listitem.getOrderCost()));
        holder.textViewState.setText(listitem.get_state());

        if(listitem.get_state().contains("Tworzenie zamówienia"))
            holder.stateButton.setText("W realizacji");
        else if(listitem.get_state().contains("W realizacji"))
            holder.stateButton.setText("Zamówienie podane");
        else if(listitem.get_state().contains("Zamówienie podane"))
            holder.stateButton.setText("Zapłacono");
        else if(listitem.get_state().contains("Zapłacono"))
            holder.stateButton.setText("Usuń zamówienie");

        holder.stateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitem.set_state(holder.stateButton.getText().toString());
                listitem.makeOrder(getKey(position));
                if(listitem.get_state()=="Usuń zamówienie");
                orderitem.remove(getKey(position));
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onItemClick.onItemCheck(getItem(position),getKey(position));
            }
        });
        }

    @Override
    public int getItemCount() {
        return orderitem.size();
    }

    private String getKey(int position)
    {
        int i=0;
        for(Map.Entry<String,CrewOrder> map:orderitem.entrySet())
        {
            if(i==position)
                return map.getKey();
            i++;
        }
        return null;
    }

    private CrewOrder getItem(int position)
    {
        int i=0;
        for(Map.Entry<String,CrewOrder> map:orderitem.entrySet())
        {
            if(i==position)
                return map.getValue();
            i++;
        }
        return null;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public Button editButton;
        public Button stateButton;
        public TextView textViewState;


        public ViewHolder(View itemView){
            super(itemView);

            textViewState=(TextView)itemView.findViewById(R.id.state);
            stateButton=(Button)itemView.findViewById(R.id.changeState);
            editButton=(Button)itemView.findViewById(R.id.editOrder);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);

        }
    }
}