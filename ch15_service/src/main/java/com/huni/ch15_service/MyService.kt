package com.huni.ch15_service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}