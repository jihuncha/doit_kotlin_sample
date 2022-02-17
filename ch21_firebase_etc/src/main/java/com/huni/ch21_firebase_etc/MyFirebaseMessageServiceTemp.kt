package com.huni.ch21_firebase_etc

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageServiceTemp : FirebaseMessagingService() {
    companion object {
        val TAG = MyFirebaseMessageServiceTemp::class.java.simpleName
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d(TAG, "fcm token..........$p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d(TAG, "fcm message..........${p0.notification}")
        Log.d(TAG, "fcm message..........${p0.data}")
    }

//
}