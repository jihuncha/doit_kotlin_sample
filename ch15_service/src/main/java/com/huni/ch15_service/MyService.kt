package com.huni.ch15_service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
//        return null
        return MyBinder()
    }
}

//bindService 함수로 서비스를 실행 -> onBind호출됨 반환값은 IBinder 인터페이스
//즉 서비스를 실행한 곳에 이 클래스의 함수를 호출하면서 매개변수 반환값으로 주고 받을 수 있다!
class MyBinder: Binder() {
    fun funA(arg: Int) {

    }
    fun funB(arg: Int): Int {
        return arg * arg
    }
}

