package com.example.ktmeats.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.ktmeats.Common.Common;
import com.example.ktmeats.Model.Request;
import com.example.ktmeats.OrderStatus;
import com.example.ktmeats.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference requests;

    public ListenOrder(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requests.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");


    }

    @Override
    public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

    }

    @Override
    public void onChildChanged( DataSnapshot snapshot, String previousChildName) {
        Request request = snapshot.getValue(Request.class);
        showNotification(snapshot.getKey(),request);
    }

    private void showNotification(String key, Request request) {
        Intent intent = new Intent(getBaseContext(), OrderStatus.class);
        intent.putExtra("userPhone",request.getPhone());
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("KtmEats")
                .setContentInfo("Your order was updated")
                .setContentText("Order #"+key+" was update status to "+ Common.convertStatusCode(request.getStatus()) )
                .setContentIntent(contentIntent)
                .setContentInfo("Info");
                //.setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1,builder.build());
    }

    @Override
    public void onChildRemoved( DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved( DataSnapshot snapshot,  String previousChildName) {

    }

    @Override
    public void onCancelled( DatabaseError error) {

    }
}
