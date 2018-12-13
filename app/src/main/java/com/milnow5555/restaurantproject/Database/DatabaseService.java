//package com.milnow5555.restaurantproject.Database;
//
//import android.util.Log;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//
//
//public class DatabaseService {
//
//
//    public void displayName(){
//
//        if(!ClientLogged){
//            //TODO generowanie kodu stolika
//            // nameText.setText("ExampleText");
//            Log.i("Msg","error");
//        }else{
//
//            firebaseUser = authorisation.getCurrentUser();
//            if(firebaseUser!= null) {
//                Log.i("Msg","error");
//                String uid = firebaseUser.getUid();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        DataSnapshot name = dataSnapshot.child("Name");
//                        actualName = name.getValue(String.class);
//
//                        Log.i("NAME", actualName);
//
////                        for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
//////                            client = new Client();
//////                            Log.i("Msg","Client creation !");
//////                            String eeee = userSnapshot.child(uid).getValue(Client.class).getName();
//////                            Log.i("Msg","error"  + eeee);
//////                            client.setName(userSnapshot.child(uid).getValue(Client.class).getName());
//////                            client.setSurname(userSnapshot.child(uid).getValue(Client.class).getSurname());
//////
////
////                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            } else {
//                Log.i("Msg","error");
//            }
//        }
//        Log.i("Msg","error");
//    }
//}
