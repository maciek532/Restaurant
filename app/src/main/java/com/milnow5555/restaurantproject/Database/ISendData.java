package com.milnow5555.restaurantproject.Database;

import android.content.Context;

import java.util.List;

public interface ISendData {

    public void setValueInFireBase(Object data);
    public List<Object> getValueFromFireBase(String objectName, String className,Context context) throws ClassNotFoundException;
}
