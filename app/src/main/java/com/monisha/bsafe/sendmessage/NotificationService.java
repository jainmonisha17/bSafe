package com.monisha.bsafe.sendmessage;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.monisha.bsafe.R;
import com.monisha.bsafe.appinfo.guardian.GuardiansInfo;
import com.monisha.bsafe.home.HomeActivity;

public class NotificationService extends Service {

    boolean isRunning = false;

    FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    String myLocation;

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        myLocation = "http://maps.google.com/maps?q=loc:"+location.getLatitude()+","+location.getLongitude();
                    }else {
                        myLocation = "http://maps.google.com/maps?q=loc:12.9349042,77.5351556";
//                        myLocation = "Unable to Find Location :(";
                    }
                });
        ShakeDetector.create(this, () -> {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(5000);

            //if you want to play siren sound you can do it here
            //just create music player and play here
            //before playing sound please set volume to max
            // send message
            Toast.makeText(this, "Message being sent", Toast.LENGTH_SHORT).show();
            MessageSender sender = new MessageSender();
            sender.sendMessageAndCall(getApplication(), myLocation);
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equalsIgnoreCase("STOP")) {
            if(isRunning) {
//                this.stopForeground(true);
//                this.stopSelf();
            }
        } else {
            Intent notificationIntent = new Intent(this, HomeActivity.class);
            PendingIntent pendingIntent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("MYID", "CHANNELFOREGROUND", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                m.createNotificationChannel(channel);

                String primaryGuardian = GuardiansInfo.getPrimaryGuardian(getApplication());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + primaryGuardian));
                PendingIntent callPendingIntent = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    callPendingIntent = PendingIntent.getActivity(this, 1, callIntent, PendingIntent.FLAG_IMMUTABLE);
                } else {
                    callPendingIntent = PendingIntent.getActivity(this, 1, callIntent, 0);
                }

                Notification notification = new Notification.Builder(this, "MYID")
                        .setContentTitle("Women Safety")
                        .setContentText("Shake Device to Send SOS")
                        .setSmallIcon(R.drawable.logo)
                        .addAction(R.drawable.call_police, "Call primary guardian", callPendingIntent)
                        .setContentIntent(pendingIntent)
                        .build();
                this.startForeground(115, notification);
                isRunning = true;
                return START_STICKY;
            }
        }
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}