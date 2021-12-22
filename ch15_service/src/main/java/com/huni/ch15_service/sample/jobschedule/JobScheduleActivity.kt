package com.huni.ch15_service.sample.jobschedule

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.content.getSystemService
import com.huni.ch15_service.databinding.ActivityJobScheduleBinding

class JobScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobScheduleBinding

    companion object {
        val TAG : String = JobScheduleActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //잡 인포 - 잡 서비스의 실행 조건 정의
        var jobScheduler: JobScheduler? = getSystemService<JobScheduler>()

        //startJob
//        JobInfo.Builder(1, ComponentName(this, MyJobService::class.java)).run {
//            //네트워크 타입을 설정
//            setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//            jobScheduler?.schedule(build())
//        }
        
        //잡 스케줄러 - 잡 서비스 등로 시 데이터 전달
        val extras = PersistableBundle()
        extras.putString("extra_data", "hello huni")
        val builder = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        //베터리가 충전 상태인지 설정한다.
        builder.setRequiresCharging(true)
        builder.setExtras(extras)
        val jobinfo = builder.build()
        jobScheduler!!.schedule(jobinfo)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}