package com.huni.ch15_service.sample.jobschedule

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log

class MyJobService : JobService() {
    companion object {
        val TAG : String = MyJobService::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    //무조건 구현해야한다 (start/stop)
    //return true -> 작업이 아직 끝나지 않음
    //return false -> 작업이 완벽하게 종료됨 -> stop을 건너뛰고 destroy로가서 종료됨
    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob")

        //잡 서비스에서 데이터 가져오기
        Log.d(TAG, "data - ${p0?.extras?.getString("extra_data")}")

        Thread(Runnable {
            var sum = 0
            for (i in 1..10) {
                sum += i
                SystemClock.sleep(1000)
            }
            Log.d(TAG, "onStartJob/threadResult : $sum")
            jobFinished(p0, false)
        }).start()

        return true
    }

    //return true -> 잡 스케줄러를 재등록
    //return false -> 잡 스케줄러 등록 취소
    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob")
        return false
    }


}