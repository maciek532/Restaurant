package com.milnow5555.restaurantproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milnow5555.restaurantproject.Domain.Client;
import com.milnow5555.restaurantproject.View.ClientMainView;
import com.milnow5555.restaurantproject.View.LoggingView;
import com.milnow5555.restaurantproject.View.MyMealsView;
import com.milnow5555.restaurantproject.View.MyOrdersView;
import com.milnow5555.restaurantproject.View.WaitressView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoggingView.OnItemSelectedListener{

    FragmentManager fragmentManager;
    @BindView(R.id.navList)
    ListView drawerList;
    private Boolean ClientLogged;
    private FirebaseAuth authorisation;
    private FirebaseUser firebaseUser;
    public CrewOrder crewOrder;
    Client client;
    private ArrayAdapter<String> drawerAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent= new Intent(getApplicationContext(), OrderService.class);
        ButterKnife.bind(this);
        fragmentManager=getFragmentManager();
        authorisation = FirebaseAuth.getInstance();

        authorisation();

        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_Container,new ClientMainView());
        fragmentTransaction.commit();

/*                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/

    }

    @Override
    public void onItemSelect(boolean bool) {
        authorisation();
        if(client!=null) {
            addItemsToDrawerWhenLogged(client.getType());
            if (client.getType().equals("Crew")) {
                startService(intent);
            }
        }
        replaceFragment(new ClientMainView());
    }

    private void authorisation()
    {
        authorisation = FirebaseAuth.getInstance();
        ClientLogged = false;
        firebaseUser = authorisation.getCurrentUser();
        if(firebaseUser!= null) {
            ClientLogged = true;
            String uid = firebaseUser.getUid();
            Log.i("MSG",uid);
            DatabaseReference user= FirebaseDatabase.getInstance().getReference("Users").child(uid);
            user.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("MSG","error1");
                    client=dataSnapshot.getValue(Client.class);
                    Log.i("MSG","error2");
                    Log.i("MSG","error3");
                    //ready=true;
                    addItemsToDrawerWhenLogged(client.getType());
                    Log.i("MSG","error6");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });Log.i("MSG","error4");
            //while(!ready);Log.i("MSG","error5");
            Log.i("MSG","error7");
        } else {
            addItemsToDrawerWhenNotLogged();
        }
    }

    private void addItemsToDrawerWhenLogged(String type) {
        String[] osArray;
        drawerList.setAdapter(null);
        if(type.equals("Guest"))
            osArray =new String[] { "Restaurant Menu", "Orderings", "Restaurant Info", "Log out"};
        else
            osArray=new String[]{"Orderings","Restaurant Info","Log out"};
        drawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);

        drawerList.setAdapter(drawerAdapter); //TODO Kolor czcionki z czarnego na bialy w listview (swoj wlasny adapter najlepiej)
        if(type.equals("Guest")) {
            drawerList.setOnItemClickListener((parent, view, position, id) -> {
                ClientOrder.getInstance().set_table(15);
                ClientOrder.getInstance().set_state("Tworzenie zamówienia");
                if (position == 3) {
                    logOut();
                } else if (position == 1) {
                    myOrdersClient(null);
                } else if (position == 0) {
                    myMeals(null);
                } else {
                    Toast.makeText(getApplicationContext(), "Do zrobienia", Toast.LENGTH_LONG).show();
                }


            });
        }
        else
        {
            drawerList.setOnItemClickListener((parent, view, position, id) -> {
                ClientOrder.getInstance().set_table(15);
                if(position == 2) {
                    logOut();
                }else if(position==0) {
                    OrdersCrew(null);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Do zrobienia",Toast.LENGTH_LONG).show();
                }


            });
        }

    }

    private void addItemsToDrawerWhenNotLogged() {
        drawerList.setAdapter(null);
        String[] osArray = { "Restaurant Menu", "Orderings", "Restaurant Info", "Log in"};
        drawerAdapter = new ArrayAdapter<>(this, R.layout.textview_for_side_menu, osArray);
        drawerList.setAdapter(drawerAdapter);
        ClientOrder.getInstance().set_table(15);
        ClientOrder.getInstance().set_state("Tworzenie zamówienia");
        drawerList.setOnItemClickListener((parent, view, position, id) -> {

            if(position == 3) {
                login(null);
            }else if(position == 0){
                myMeals(null);
            }else if(position == 1){
                myOrdersClient(null);
            }else{
                Toast.makeText(getApplicationContext(),"Do zrobienia",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void logOut(){
        if(authorisation.getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            addItemsToDrawerWhenNotLogged();
            stopService(intent);
        }
    }

    void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_Container,fragment);
        fragmentTransaction.commit();
    }

    public void login(View view){
        replaceFragment(new LoggingView());
    }
    public void myMeals(View view){
        replaceFragment(new MyMealsView());
    }
    public void myOrdersClient(View view){
        replaceFragment(new MyOrdersView());
    }
    public void OrdersCrew(View view){
        replaceFragment(new WaitressView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
