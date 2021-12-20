package com.huni.ch15_outer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import com.huni.ch15_outer.R

class MyMessengerService : Service() {
    //엑티비티의 데이터를 전달받는 메신저 (수신)
    lateinit var messenger: Messenger

    //엑티비티에 데이터를 전달하는 메신저 (발신)
    lateinit var replyMessenger: Messenger
    lateinit var player: MediaPlayer

    companion object {
        val TAG: String = MyMessengerService::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    inner class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d(TAG, "handleMessage - $msg")

            when(msg.what) {
                10 -> {
                    //서비스에 연결되자마자 전달되는 메시지
                    replyMessenger = msg.replyTo
                    if (!player.isPlaying) {
                        player = MediaPlayer.create(this@MyMessengerService, R.raw.music)
                        try {
                            //지속 시간 전송
                            val replyMsg = Message()
                            replyMsg.what = 10
                            val replyBundle = Bundle()
                            replyMsg.obj = replyBundle
                            replyMessenger.send(replyMsg)
                            //음악 재생
                            player.start()
                        } catch (e: Exception) {
                            Log.e(TAG, "e - $e")
                            e.printStackTrace()
                        }
                    }
                }

                20 -> {
                    //멈춤 메시지
                    if (player.isPlaying) player.stop()
                }

                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        messenger = Messenger(IncomingHandler(this))
        return messenger.binder
    }
}