package com.milnow5555.restaurantproject;

import com.milnow5555.restaurantproject.Database.ISendData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Order implements Serializable{


    protected List<Dish> _dishes;
    protected int _table;
    protected String _state;

    public void set_dishes(List<Dish> _dishes) {
        this._dishes = _dishes;
    }

    public Order(){};

    public void set_table(int _table) {
        this._table = _table;
    }

    public int get_table() {
        return _table;
    }

    public List<Dish> get_dishes() {
        return _dishes;
    }

    public Order(List<Dish> dishes)
    {
        _dishes=dishes;
    }

    /*public Order(List<Dish> meals, String state, int table) {
        _dishes = meals;
        _state = state;
        _table=table;
    }*/

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public void addMeal(String name, double price, List<String> components)
    {
        _dishes.add(new Dish(name,price,components));
    }

    public void addMeal(Dish dish)
    {
        _dishes.add(dish);
    }

    public double getOrderCost()
    {
        double cost=0;
        for(Dish meal: _dishes)
        {
            cost+= meal.getPrice();
        }
        return cost;
    }

    public void removeMeal(String name)
    {
        for(Dish dish: _dishes)
        {
            if(dish.getName()==name)
            {
                _dishes.remove(dish);
                break;
            }
        }
    }

    public void removeMeal(int position)
    {
        _dishes.remove(position);
    }

    public Dish getDish(int position){
        return _dishes.get(position);
    }
}