package com.milnow5555.restaurantproject;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milnow5555.restaurantproject.Adapter.WaitressAdapter;
import com.milnow5555.restaurantproject.View.CrewOrdersView;
import com.milnow5555.restaurantproject.View.MyOrdersView;
import com.milnow5555.restaurantproject.View.WaitressView;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OrderService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.milnow5555.restaurantproject.action.FOO";
    private static final String ACTION_BAZ = "com.milnow5555.restaurantproject.action.BAZ";
    boolean wait = true;
    DatabaseReference ordersRef;
    ValueEventListener eventListener;

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.milnow5555.restaurantproject.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.milnow5555.restaurantproject.extra.PARAM2";
    private Handler handler;

    public OrderService() {
        super("OrderService");
        handler = new Handler();
        setIntentRedelivery(true);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, OrderService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    boolean firstRun = false;

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, OrderService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

        eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (firstRun) {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                    mBuilder.setSmallIcon(R.drawable.logo);
                    mBuilder.setContentTitle("New Order");
                    mBuilder.setContentText("Occurs new order!!!");
                    mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    mBuilder.setAutoCancel(true);
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    mBuilder.setSound(alarmSound);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                    notificationManagerCompat.notify(1, mBuilder.build());
                }
                firstRun = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ordersRef.addValueEventListener(eventListener);
        int a = 0;
        while (true) {
            try {
                Thread.sleep(3000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
                Toast.makeText(OrderService.this, "onDestroy", Toast.LENGTH_SHORT).show();
        ordersRef.removeEventListener(eventListener);
        //wait = false;
        Intent intent = new Intent();
        intent.setAction("restartService");
        intent.setClass(this, Restarter.class);
        this.sendBroadcast(intent);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Toast.makeText(OrderService.this, "onTaskRemoved", Toast.LENGTH_SHORT).show();
        ordersRef.removeEventListener(eventListener);
        Intent intent = new Intent();
        intent.setAction("restartService");
        intent.setClass(this, Restarter.class);
        this.sendBroadcast(intent);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}