package com.milnow5555.restaurantproject.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.milnow5555.restaurantproject.MainActivity;
import com.milnow5555.restaurantproject.R;

import butterknife.BindView;

//import com.milnow5555.restaurantproject.Database.DatabaseService;

public class ClientMainView extends Fragment {


    // private DatabaseService databaseService = new DatabaseService();

    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.progressBar6)
    ProgressBar progressBar6;
    private FirebaseAuth authorisation;
    boolean clientLogged;

    String actualName;

    boolean ready=false;

    public ClientMainView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_client_main_view, container, false);
        view.performClick();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (authorisation.getCurrentUser() == null) {
            String guest = "Guest";
            nameText.setText(guest);
        }*/

    }
}