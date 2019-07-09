package com.example.mamanguo.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.mamanguo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Notifications extends AppCompatActivity {

    private NotificationManager mNotifyManager;

    /*Constants for the notification id, the web url for a button in the notification, and the

    custom notification actions for buttons in the notification*/

    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_GUIDE_URL = "https://developer.android.com/design/patterns/notifications.html";
    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.mamanguo.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION = "com.example.mamanguo.ACTION_CANCEL_NOTIFICATION";


    private BottomNavigationView mNotifyButton;
    private Button mUpdateButton;
    private Button mCancelButton;


    private NotificationReceiver mReceiver = new NotificationReceiver();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyButton = findViewById(R.id.nav_view);
       /* mUpdateButton = (Button) findViewById(R.id.update);
        mCancelButton = (Button) findViewById(R.id.cancel);*/


        mNotifyButton.setEnabled(true);
        /*mUpdateButton.setEnabled(false);
        mCancelButton.setEnabled(true);*/


        //Initialize and register the notification receiver

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReceiver, intentFilter);


        //Set OnClick methods.
        mNotifyButton.setOnClickListener(view -> sendNotification());

        /*mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }

        });*/
        /*mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });
*/


    }


    /**
     * Unregister the receiver when the app is destroyed
     */

    @Override

    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    /**
     * OnClick method for the "Notify Me!" button. Creates and delivers a simple notification
     */

    public void sendNotification() {

        //Sets up the pending intent that is delivered when the notification is clicked
        Intent notificationIntent = new Intent(this, Notifications.class);

        PendingIntent notificationPendingIntent = PendingIntent.getActivity

                (this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Sets up the pending intent to cancel the notification,
        // delivered when the user dismisses the notification

        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);


        //Sets up the pending intent associated with the Learn More notification action,

        //uses an implicit intent to go to the web.

        Intent learnMoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NOTIFICATION_GUIDE_URL));

        PendingIntent learnMorePendingIntent = PendingIntent.getActivity

                (this, NOTIFICATION_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT);


        //Sets up the pending intent to update the notification. Corresponds to a press of the

        //Update Me! button

        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);

        PendingIntent updatePendingIntent = PendingIntent.getBroadcast

                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);


        //Builds the notification with all of the parameters
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Request accepted")
                .setContentText("Someone is willing to do your laundry")
                .setSmallIcon(R.drawable.logo2)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                //.addAction(R.drawable.ic_learn_more, getString(R.string.learn_more), learnMorePendingIntent)
                //.addAction(R.drawable.ic_update, getString(R.string.update), updatePendingIntent)
                .setDeleteIntent(cancelPendingIntent);


        //Delivers the notification
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        //Enables the update and cancel buttons but disables the "Notify Me!" button
        /*mNotifyButton.setEnabled(false);
        mUpdateButton.setEnabled(true);
        mCancelButton.setEnabled(true);*/

    }


    /**
     * OnClick method for the "Cancel Me!" button. Cancels the notification
     */

    private void cancelNotification() {

        //Cancel the notification

        mNotifyManager.cancel(NOTIFICATION_ID);


        //Resets the buttons

        mNotifyButton.setEnabled(true);

        mUpdateButton.setEnabled(false);

        mCancelButton.setEnabled(false);

    }


    /**
     * The broadcast receiver class for notifications. Responds to the update notification and
     * <p>
     * cancel notification pending intents actions.
     */

    private class NotificationReceiver extends BroadcastReceiver {
        /**
         * Gets the action from the incoming broadcast intent and responds accordingly
         *
         * @param context Context of the app when the broadcast is received.
         * @param intent  The broadcast intent containing the action.
         */

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            assert action != null;
            switch (action) {
                case ACTION_CANCEL_NOTIFICATION:
                    cancelNotification();
                    break;
                default:
                    Toast.makeText(context, "No action specified", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    }

}
