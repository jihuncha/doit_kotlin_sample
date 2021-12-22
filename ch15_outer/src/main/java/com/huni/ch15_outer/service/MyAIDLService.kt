package com.huni.ch15_outer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.huni.ch15_outer.MyAIDLInterface
import com.huni.ch15_outer.R

class MyAIDLService : Service() {
    companion object {
        val TAG: String = MyAIDLService::class.java.simpleName
    }

    lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return object : MyAIDLInterface.Stub() {
            override fun getMaxDuration(): Int {
                Log.d(TAG, "getMaxDuration")
                return if (player.isPlaying)
                    player.duration
                else 0
            }

            override fun start() {
                Log.d(TAG, "start")
                if (!player.isPlaying) {
                    player = MediaPlayer.create(this@MyAIDLService, R.raw.music)
                    try {
                        player.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun stop() {
                Log.d(TAG, "stop")
                if (player.isPlaying) {
                    player.stop()
                }
            }

        }
    }
}