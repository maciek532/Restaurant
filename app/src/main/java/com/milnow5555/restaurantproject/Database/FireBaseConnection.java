package com.milnow5555.restaurantproject.Database;


import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milnow5555.restaurantproject.Dish;

import java.lang.*;

import java.util.*;

public class FireBaseConnection implements ISendData {

    final FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference childRef;
    List<Object> list;
    boolean ready=false;

    public FireBaseConnection(String path/*"nazwa segmentu w bazie np zamównienia albo posiłki"*/)
    {
        database=FirebaseDatabase.getInstance();
        ref=database.getReference(path);
        list=new ArrayList<>();
        //map.put("fasada","jajko");
    }

    public void setValueInFireBase(Object data)
    {
        childRef.push().setValue(data);
    }

    public List<Object> getValueFromFireBase(String firstNode, String className,Context context) throws ClassNotFoundException {
        Class clas=Class.forName(className);
        DatabaseReference firstRef=ref.child(firstNode);//FirebaseDatabase.getInstance().getReference(firstNode);
        firstRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Object sd=ds.getValue(clas);
                    Dish q=(Dish)sd;
                    list.add(ds.getValue(Dish.class));
                    Dish ff=ds.getValue(Dish.class);
                    /*Toast.makeText(context,"Do zrobienia" + ff.getName(),Toast.LENGTH_LONG).show();
                    Toast.makeText(context,"Do zrobienia" + q.getName(),Toast.LENGTH_LONG).show();*/
                }
                ready=true;
            }


            @java.lang.Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        while(!ready){};
        return list;
    }

    public Object getChangeValueFromFireBase(String className) throws ClassNotFoundException {
        final Object[] obj = new Object[1];
        Class clas=Class.forName(className);
        childRef.addValueEventListener(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obj[0] =dataSnapshot.getValue(clas);
            }

            @java.lang.Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return obj[0];
    }

    public Object getChangeValueFromFireBase(String firstNode,String className) throws ClassNotFoundException {
        DatabaseReference firstRef=childRef.child(firstNode);
        final Object[] obj = new Object[1];
        Class clas=Class.forName(className);
        firstRef.addValueEventListener(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obj[0] =dataSnapshot.getValue(clas);
            }

            @java.lang.Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return obj[0];
    }

    public Object getChangeValueFromFireBase(String firstNode,String secondNode,String className) throws ClassNotFoundException {
        DatabaseReference firstRef=childRef.child(firstNode);
        DatabaseReference secondRef=firstRef.child(secondNode);
        final Object[] obj = new Object[1];
        Class clas=Class.forName(className);
        secondRef.addValueEventListener(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obj[0] =dataSnapshot.getValue(clas);
            }

            @java.lang.Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return obj[0];
    }
}