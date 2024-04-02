package com.monisha.bsafe.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.location.LocationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.monisha.bsafe.BaseLanguageLoaderActivity;
import com.monisha.bsafe.R;
import com.monisha.bsafe.appinfo.guardian.GuardiansInfo;
import com.monisha.bsafe.guardians.AddGuardiansActivity;
import com.monisha.bsafe.policestation.ShowPoliceStationsActivity;
import com.monisha.bsafe.home.data.GridItemClickListener;
import com.monisha.bsafe.home.data.GridItem;
import com.monisha.bsafe.home.data.GridItemList;
import com.monisha.bsafe.databinding.ActivityMainBinding;
import com.monisha.bsafe.appinfo.language.LanguageInfo;
import com.monisha.bsafe.appinfo.language.Languages;
import com.monisha.bsafe.selfdefense.SelfDefenseActivity;
import com.monisha.bsafe.sendmessage.MessageSender;
import com.monisha.bsafe.sendmessage.NotificationService;
import com.monisha.bsafe.tips.TipsActivity;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends BaseLanguageLoaderActivity
        implements EasyPermissions.PermissionCallbacks, GridItemClickListener {

    private final String[] locationPermissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    private final String[] sendSmsPermissions = {Manifest.permission.SEND_SMS};
    private final String[] vibratePermissions = {Manifest.permission.VIBRATE};
    @RequiresApi(api = Build.VERSION_CODES.P)
    private final String[] foregroundServicePermissions = {Manifest.permission.FOREGROUND_SERVICE};
    private final int REQUEST_LOCATION_PERMISSION = 1000;
    private final int REQUEST_SMS_PERMISSION = 1001;
    private final int REQUEST_FOREGROUND_SERVICE_PERMISSION = 1002;
    private final int REQUEST_VIBRATE_PERMISSION = 1003;
    private Location latestLocation = new Location("gps");
    // A location service combines GPS location and network location to achieve balance between battery consumption
    // and accuracy.
    // 1. create LocationManager instance as a reference to location service.
    // 2. Request location from LocationManager
    // 3. Receive Location update from LocationListener as change of location.


    private FusedLocationProviderClient fusedLocationClient;

    //about savedInstanceState -> while adding guardian no,
    //i need to make sure the num entered by me is extracted from EditText component.
    // onSaveInstanceState() is called everytime the activity is destroyed or recreated.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // inflate -> refers to parsing XML & turning it into UI-oriented data str.
        // XML UI element binds with Java code -> ActivityMainBinding

        // location handling
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //permissions
        requestLocationPermission();
        requestSendSMSPermission();
        requestForegroundServicePermission();
        requestVibratePermission();

        // setting items in the Recycler view grid and item-click-listener
        GridItemList list = new GridItemList();
        GridItemsAdapter recyclerViewAdapter = new GridItemsAdapter(list.getItems(), this);
        binding.recyclerView.setAdapter(recyclerViewAdapter);

        // recycler view -> Setting items in the recycler view grid & item click listener

        // setOnClickListener -> onclick() method is called when the view is clicked.


        // on clicking start
        binding.start.setOnClickListener(view -> {
            if (!isLocationEnabled()) {
                Toast.makeText(this, "Please turn on the GPS", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isLocationAndSMSPermissionEnabled()) {
                Toast.makeText(this, "Please give location and SMS permissions", Toast.LENGTH_SHORT).show();
                return;
            }
            String number = GuardiansInfo.getPrimaryGuardian(getApplication());
            if (!number.isEmpty()) {
                Intent notificationIntent = new Intent(this, NotificationService.class);
                notificationIntent.setAction("Start");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getApplicationContext().startForegroundService(notificationIntent);
                    Snackbar.make(findViewById(android.R.id.content), "You are ready to go, check your notification tray", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please add a guardian number", Toast.LENGTH_SHORT).show();
            }
        });

        binding.sos.setOnClickListener(view -> {
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.scream);
            mp.start();
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        if (!EasyPermissions.hasPermissions(this, locationPermissions)) {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, locationPermissions);
        }
        updateLatestLocation();
    }

    public void requestSendSMSPermission() {
        if (!EasyPermissions.hasPermissions(this, sendSmsPermissions)) {
            EasyPermissions.requestPermissions(this, "Please grant the SMS permission", REQUEST_SMS_PERMISSION, sendSmsPermissions);
        }
        updateLatestLocation();
    }

    public void requestForegroundServicePermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (!EasyPermissions.hasPermissions(this, foregroundServicePermissions)) {
                EasyPermissions.requestPermissions(this, "Please grant the Notification service permission", REQUEST_FOREGROUND_SERVICE_PERMISSION, foregroundServicePermissions);
            }
        }
    }

    public void requestVibratePermission() {
        if (!EasyPermissions.hasPermissions(this, vibratePermissions)) {
            EasyPermissions.requestPermissions(this, "Please grant the vibrate permission", REQUEST_VIBRATE_PERMISSION, vibratePermissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        updateLatestLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Closing the app", Toast.LENGTH_LONG).show();
            onDestroy();
            return;
        }
    }

    @SuppressLint("MissingPermission")
    public void updateLatestLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latestLocation = location;
                        }
                    }
                });
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    private boolean isLocationAndSMSPermissionEnabled() {
        return EasyPermissions.hasPermissions(this, locationPermissions) &&
                EasyPermissions.hasPermissions(this, sendSmsPermissions);
    }

    @Override
    public void onItemClick(GridItem item) {
        switch (item.getType()) {
            case CALL_POLICE:
                Intent callPoliceIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 100));
                startActivity(callPoliceIntent);
                break;
            case NEARBY_POLICE_STATIONS:
                // If GPS is not turned ON, show message and don't do anything
                if (!isLocationEnabled()) {
                    Toast.makeText(this, "Please turn on the GPS", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this, ShowPoliceStationsActivity.class);
                    intent.putExtra("lat", latestLocation.getLatitude() + "");
                    intent.putExtra("lon", latestLocation.getLongitude() + "");
                    startActivity(intent);
                }
                break;
            case CHANGE_LANGUAGE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String[] languages = {Languages.ENGLISH, Languages.HINDI, Languages.KANNADA, Languages.TELUGU};
                builder.setItems(languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        String selectedLanguage = languages[position];
                        LanguageInfo.putLanguage(HomeActivity.this, selectedLanguage);
                        recreate();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case SAFETY_TIPS:
                Intent safetyTipsIntent = new Intent(this, TipsActivity.class);
                startActivity(safetyTipsIntent);
                break;
            case ADD_GUARDIANS:
                Intent addGuardiansIntent = new Intent(this, AddGuardiansActivity.class);
                startActivity(addGuardiansIntent);
                break;
            case ESCAPE:
                Intent escapeIntent = new Intent(this, TipsActivity.class);
                escapeIntent.putExtra("escape", true);
                startActivity(escapeIntent);
                break;
            case SELF_DEFENSE:
                Intent selfDefenseIntent = new Intent(this, SelfDefenseActivity.class);
                selfDefenseIntent.putExtra("self_defense", true);
                startActivity(selfDefenseIntent);
                break;
            case EMERGENCY_NUMBERS:
                Intent emergencyIntent = new Intent(this, SelfDefenseActivity.class);
                startActivity(emergencyIntent);
                break;
        }
    }
}