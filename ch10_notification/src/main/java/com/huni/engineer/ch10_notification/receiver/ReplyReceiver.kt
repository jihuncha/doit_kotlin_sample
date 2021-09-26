package com.huni.engineer.ch10_notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.RemoteInput

class ReplyReceiver : BroadcastReceiver() {
    val TAG: String = ReplyReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        // 알림의 입력 글 획득
        val replyTxt = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")

        Log.d(TAG, "onReceive - $replyTxt")

        // 알림 취소
        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE)
                as NotificationManager

        manager.cancel(11)

    }
}