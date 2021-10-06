package com.huni.engineer.ch10_notification

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.huni.engineer.ch10_notification.databinding.ActivityDialogTestBinding

class DialogTestActivity : AppCompatActivity() {
    val TAG: String = DialogTestActivity::class.java.simpleName

    var mActivity:Activity? = null
    var mHandler: Handler? = null

    init {
         mActivity = this
         mHandler = Handler(Looper.getMainLooper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = ActivityDialogTestBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btToastTest.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                showToast()
            }
        }

        viewBinding.btDatepickerTest.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                showDatePicker()
            }
        }

        viewBinding.btAlertButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                showAlertDialog()
            }
        })

        viewBinding.btSoundVibrateButton.setOnClickListener {
            ringtonAndVibrate()
        }

        viewBinding.btNotificationButton.setOnClickListener(object:View.OnClickListener {
            override fun onClick(p0: View?) {
                notificationTest()
            }
        })
    }

    //api 30 new style - Toast Callback
    @RequiresApi(Build.VERSION_CODES.R)
    fun showToast() {
        val toast = Toast.makeText(this, "Toast Test입니다.", Toast.LENGTH_LONG)
        toast.addCallback(
            object : Toast.Callback() {
                override fun onToastHidden() {
                    Log.d(TAG, "onToastHidden")
                    super.onToastHidden()
                }

                override fun onToastShown() {
                    Log.d(TAG, "onToastShown")
                    super.onToastShown()
                }
            })
        toast.show()
    }


    // DatePicker
    @RequiresApi(Build.VERSION_CODES.N)
    fun showDatePicker() {
        Log.d(TAG, "showDatePicker")

        val picker = DatePickerDialog(this)

        picker.setOnDateSetListener(
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    Log.d(TAG, "year - ${p1} , month - ${p2 + 1 }, day - ${p3}")
                }
            })

        picker.show()
    }

    // AlertDialog
    fun showAlertDialog() {
        Log.d(TAG, "showAlertDialog")

        val items = arrayOf("하나","둘","셋","넷")

        AlertDialog.Builder(this).run {
            setTitle("테스트입니다")
            setIcon(android.R.drawable.ic_dialog_info)

//            setItems(items, object:DialogInterface.OnClickListener{
//                override fun onClick(p0: DialogInterface?, p1: Int) {
//                    Log.d(TAG,"item - ${items[p1]}")
//                }
//            })

            setMultiChoiceItems(items,
                booleanArrayOf(true, false, true, false),
                object: DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                        Toast.makeText(mActivity,
                            "${items[p1]} 이 ${if(p2) "선택되었습니다." else "해제되었습니다."}", Toast.LENGTH_SHORT).show()
                    }
                })

            setPositiveButton("닫기", null)
            setCancelable(true)
            show()
        }.setCanceledOnTouchOutside(false)
    }


    fun ringtonAndVibrate() {
        Log.d(TAG, "ringtonAndVibrate")

        //notification sound uri
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone= RingtoneManager.getRingtone(applicationContext, notification)
        ringtone.play()

        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        //진동 크기를 제공
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }

    //알림 빌더 생성 -> 알림 객체 생성 -> notify
    fun notificationTest() {
        Log.d(TAG, "notificationTest")

        //알림 빌더 생성
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder : NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description = "My Channel One Description"
            channel.setShowBadge(true)

            //각종 속성 추가
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().
                    setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).
                    setUsage(AudioAttributes.USAGE_ALARM).
                    build()
            channel.setSound(uri, audioAttributes)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 100, 200)

            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this, channelId)
        } else{
            builder = NotificationCompat.Builder(this)
        }

        //알림 객체 생성
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("알림 테스트")
        builder.setContentText("안녕안녕~")

        //팬딩인텐트
        val intent: Intent = Intent(this, EmptyActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,
            10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        //알림 - (아이디는 임시로 11로 생성)
        manager.notify(11, builder.build())

        //취소할 경우 - 5초뒤에
        mHandler?.postDelayed({
            Log.d(TAG, "postDelay!!")
            manager.cancel(11)}, 5000)
    }
}


