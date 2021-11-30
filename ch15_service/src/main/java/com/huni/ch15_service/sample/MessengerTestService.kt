package com.huni.ch15_service.sample

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast

// Messenger 객체 사용
class MessengerTestService : Service() {
    lateinit var messenger : Messenger

    companion object {
        val TAG = MessengerTestService::class.java.simpleName
    }

    //internal
    //자바는 public, protect, private 이 있으나 코틀린은 변경자가 없는 경우는 public
    //패키지 전용 가시성에 대한 대안으로 코틀린에는 internal 이라는 새로운 가시성 변경자가 도입.
    //internal은 같은 모듈 내에서만 볼 수 있으며, 모듈은 한꺼번에 컴파일되는 코틀린 파일들을 의미
    internal class IncomingHandler (
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler(Looper.getMainLooper()) {
        //외부에서 서비스에 데이터 전달시 자동 호출
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                10 ->
                    Toast.makeText(applicationContext, "${msg.obj}", Toast.LENGTH_SHORT).show()
                20 ->
                    Toast.makeText(applicationContext, "${msg.obj}", Toast.LENGTH_SHORT).show()
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "onBind")
        //onbind반환값으로 messenger 생성하여 binder속성을 반환한다.
        messenger = Messenger(IncomingHandler(this))
        return messenger.binder
    }
}