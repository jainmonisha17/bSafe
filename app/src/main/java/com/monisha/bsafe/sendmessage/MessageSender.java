package com.monisha.bsafe.sendmessage;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import com.monisha.bsafe.R;
import com.monisha.bsafe.appinfo.guardian.GuardiansInfo;

public class MessageSender {

    SmsManager smsManager = SmsManager.getDefault();

    public void sendMessageAndCall(Application application, String location) {
        String primaryGuardian = GuardiansInfo.getPrimaryGuardian(application);
        String secondaryGuardian = GuardiansInfo.getPrimaryGuardian(application);
        String message = application.getString(R.string.i_am_in_trouble, location);
        if (!primaryGuardian.isEmpty()) {
            smsManager.sendTextMessage("+91-"+primaryGuardian, null, message, null, null);
        }
        if (!secondaryGuardian.isEmpty()) {
            smsManager.sendTextMessage("+91-"+secondaryGuardian, null, message, null, null);
        }
    }
}
