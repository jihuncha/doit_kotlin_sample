package com.huni.ch20_firebase

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyApplication : MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth

        var email: String? = null

        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                //return 부분
                currentUser.isEmailVerified
                //TODO 왜 이렇게했지? ?: false 면 되지 않나..?
            } ?: let {
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
    }
}