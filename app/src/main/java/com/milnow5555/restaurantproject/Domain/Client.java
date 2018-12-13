package com.milnow5555.restaurantproject.Domain;

import java.util.ArrayList;



public class Client implements User {

    private String Name;
    private String Surname;
    private String Type;

    public String getDetails() {
        return getName() + " " + getSurname();
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getType(){return Type;}

    public void setType(String Type)
    {
        this.Type=Type;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

}
