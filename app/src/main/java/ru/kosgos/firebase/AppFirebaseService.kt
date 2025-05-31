package ru.kosgos.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "> onMessageReceived")
        Log.d(TAG, "${message.notification?.title}")
        message.data.forEach { (t, u) ->
            Log.d(TAG, "$t $u")
        }
        Log.d(TAG, "> onMessageReceived")
    }
}