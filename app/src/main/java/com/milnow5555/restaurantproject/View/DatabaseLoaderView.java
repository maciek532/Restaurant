//package com.milnow5555.restaurantproject.View;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ProgressBar;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.milnow5555.restaurantproject.Domain.Client;
//import com.milnow5555.restaurantproject.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class DatabaseLoaderView extends AppCompatActivity {
//
//    @BindView(R.id.progressBar3)
//    ProgressBar progressBar;
//
//    private String Name, Surname, Email;
//    //TODO orderedMeals;
//    private FirebaseAuth authorisation;
//    private FirebaseUser firebaseUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i("MMMMSSSSGGGG","ON KARATEEEEEEEEEEEEEEEEe");
//        ButterKnife.bind(this);
//        setContentView(R.layout.activity_database_loader_view);
//        authorisation = FirebaseAuth.getInstance();
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i("MMMMSSSSGGGG","On STARAAAAAAAAAAAAAAAART");
//        progressBar.setVisibility(View.VISIBLE);
//        new Downloader().execute();
//    }
//
//    private class Downloader extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            Log.i("MMMMSSSSGGGG","DO IN BACKGROUNNNNDD");
//            displayName();
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//            super.onPostExecute(aVoid);
//            Log.i("MMMMSSSSGGGG","POST ESSSSSSSXXXXXXXXXx");
//            progressBar.setVisibility(View.INVISIBLE);
//            startMainView();
//        }
//    }
//
//    private void startMainView(){
//        Log.i("MMMMSSSSGGGG","INTENTTTTTTTTTTTTTTTTTT");
//        Intent intent = new Intent(this, ClientMainView.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//
//    private void displayName(){
//
//            firebaseUser = authorisation.getCurrentUser();
//            if(firebaseUser!= null) {
//                Log.i("Msg","KURRRRRRRRRRRWAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA22222222222222222222222222a");
//                String uid = firebaseUser.getUid();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        DataSnapshot name = dataSnapshot.child("Name");
//                        Name = name.getValue(String.class);
//
//                        Log.i("DatabaseLoaderName1", Name);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            } else {
//                Log.i("Msg","ERRRRRRRRRRRRRRRRRRRRRRRRRoooooR");
//            }
//
//        Log.i("MMMMSSSSGGGG","DatabaseDownloaderDisplayNameEnd");
//    }
//
//}
