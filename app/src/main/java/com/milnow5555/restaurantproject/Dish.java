package com.milnow5555.restaurantproject;


import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable{
    private String Name;
    private double _weight;
    private double Price;

    private List<String> _components;

    public Dish(){};
    public Dish(String Name,Double Price)
    {
        this.Name=Name;
        this.Price=Price;
    }

    public double getPrice() {
        return Price;
    }

    public String getName() {
        return Name;
    }

    public List<String> getComponents() {
        return _components;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public Dish(String name, double price, List<String> components) {
        Name = name;
        Price = price;
        _components = components;
    }
}
