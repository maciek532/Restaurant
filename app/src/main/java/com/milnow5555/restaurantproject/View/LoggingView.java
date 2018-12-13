package com.milnow5555.restaurantproject.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.milnow5555.restaurantproject.MainActivity;
import com.milnow5555.restaurantproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoggingView extends Fragment {


    FirebaseAuth authorisation;
    MainActivity mainActivity;
    final String email_key="email";
    final String password_key="password";

//    String testName = "Pioter";
//    String testSurname = "Kowolski";
private OnItemSelectedListener listener;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.editTextEmail)
    EditText emailText;
    @BindView(R.id.editTextPassword)
    EditText passwordText;
    @BindView(R.id.textViewSignup)
    TextView textView;
    @BindView(R.id.buttonLogin)
    Button button;

    @BindView(R.id.editTextTableNumber)
    EditText tableNumberText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_logging,container,false);
        ButterKnife.bind(this,view);
        mainActivity=(MainActivity)getActivity();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mainActivity, CreateUserView.class), 2);
            }
        });

        SharedPreferences.Editor editor=mainActivity.getPreferences(Context.MODE_PRIVATE).edit();
        emailText.setText(mainActivity.getPreferences(Context.MODE_PRIVATE).getString(email_key, ""));
        passwordText.setText(mainActivity.getPreferences(Context.MODE_PRIVATE).getString(password_key,""));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String table = tableNumberText.getText().toString().trim();
                if(!verify(email, password, table)) return;



//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("loggedName", testName);
//        returnIntent.putExtra("loggedSurname", testSurname);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
                authorisation=FirebaseAuth.getInstance();
                progressBar.setVisibility(View.VISIBLE);
                authorisation.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        editor.putString(email_key, emailText.getText().toString());
                        editor.putString(password_key, passwordText.getText().toString());
                        editor.commit();
                        listener.onItemSelect(true);
                    } else {
                        Toast.makeText(mainActivity.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //authorisation = FirebaseAuth.getInstance();

        return view;
    }

    public interface OnItemSelectedListener{
        void onItemSelect(boolean bool);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener)
        {
            listener=(OnItemSelectedListener)context;
        }
        else
        {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        authorisation.getCurrentUser();
        if (authorisation.getCurrentUser() != null) {
            finish();
            Log.i("MSG","error");
            startActivity(new Intent(this, ClientMainView.class));
        }
    }*/

    public boolean verify(String email, String password, String tableText) {

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

        if (password.isEmpty()) {
            passwordText.setError("password is required");
            passwordText.requestFocus();
            return false;
        }

        if (tableText.isEmpty()) {
            passwordText.setError("Table number is required");
            passwordText.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            passwordText.setError("Minimum lenght of password should be 6");
            passwordText.requestFocus();
            return false;
        }
        return true;
    }

}
