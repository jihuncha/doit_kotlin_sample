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
        val toast = Toast.makeText(this, "Toast Test?????????.", Toast.LENGTH_LONG)
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

        val items = arrayOf("??????","???","???","???")

        AlertDialog.Builder(this).run {
            setTitle("??????????????????")
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
                            "${items[p1]} ??? ${if(p2) "?????????????????????." else "?????????????????????."}", Toast.LENGTH_SHORT).show()
                    }
                })

            setPositiveButton("??????", null)
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

        //?????? ????????? ??????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }

    //?????? ?????? ?????? -> ?????? ?????? ?????? -> notify
    fun notificationTest() {
        Log.d(TAG, "notificationTest")

        //?????? ?????? ??????
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

            //?????? ?????? ??????
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

        //?????? ?????? ??????
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("?????? ?????????")
        builder.setContentText("????????????~")

        //???????????????
        val intent: Intent = Intent(this, EmptyActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,
            10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        //?????? - (???????????? ????????? 11??? ??????)
        manager.notify(11, builder.build())

        //????????? ?????? - 5?????????
        mHandler?.postDelayed({
            Log.d(TAG, "postDelay!!")
            manager.cancel(11)}, 5000)
    }
}


