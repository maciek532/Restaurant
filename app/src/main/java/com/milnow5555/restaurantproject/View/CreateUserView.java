package com.milnow5555.restaurantproject.View;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.milnow5555.restaurantproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateUserView extends AppCompatActivity {

    @BindView(R.id.editTextEmail)
    EditText emailText;
    @BindView(R.id.editTextSurname)
    EditText surnameText;
    @BindView(R.id.editTextPassword)
    EditText passwordText;
    @BindView(R.id.editTextName)
    EditText nameText;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private FirebaseAuth authorisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_view);
        ButterKnife.bind(this);
        authorisation = FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (authorisation.getCurrentUser() != null) {
            finish();
        }
    }
    public void registerUser(View view){

        String surname = surnameText.getText().toString().trim();
        String name = nameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        if(!verify(name, surname, password, email)) return;

        //TODO tu musi byc dodanie do firebase usera, zwrocenie w intencji rezultatu w zaleznosci od tego czy rejestracja sie udala


        progressBar.setVisibility(View.VISIBLE);

        authorisation.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                finish();
                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoggingView.class));
            } else {

                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public boolean verify(String name, String surname, String password, String email){

        if (email.isEmpty()) {
            emailText.setError("Email is required");
            emailText.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please enter a valid email");
            emailText.requestFocus();
            return false;
        }
        if (name.isEmpty()) {
            nameText.setError("Name is required");
            nameText.requestFocus();
            return false;
        }

        if (surname.isEmpty()) {
            surnameText.setError("Surname is required");
            surnameText.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            passwordText.setError("Password is required");
            passwordText.requestFocus();
            return false;
        }


        return true;
    }
}
