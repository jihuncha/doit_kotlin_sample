package com.huni.ch20_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.huni.ch20_firebase.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    companion object {
        val TAG : String = AuthActivity::class.java.simpleName
    }

    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (MyApplication.checkAuth()) {
            changeVisibility("login")
        } else {
            changeVisibility("logout")
        }

        binding.logoutBtn.setOnClickListener {
            //로그아웃...........
            MyApplication.auth.signOut()
            MyApplication.email = null
            changeVisibility("logout")

        }

        binding.goSignInBtn.setOnClickListener {
            changeVisibility("signin")
        }

//        https://firebase.google.com/docs/auth/android/google-signin?hl=ko#kotlin+ktx_1
        binding.googleLoginBtn.setOnClickListener {
            //구글 로그인
            //TODO ServerClient OAuthId를 사용해야한다.
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("216665149136-ubqhj1rlhj1gl5r0dom36v74ms9ot24o.apps.googleusercontent.com")
                .requestEmail()
                .build()
            //구글의 인증관리 앱 실행
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            startActivityForResult(signInIntent, 10)

        }

        binding.signBtn.setOnClickListener {
            //이메일,비밀번호 회원가입........................
            val email: String = binding.authEmailEditView.text.toString()
            val password: String = binding.authPasswordEditView.text.toString()

            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if (task.isSuccessful) {
                        // 비밀번호는 최소 6자 이상
                        //send email...
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    Toast.makeText(
                                        baseContext, "회원가입에 성공하였습니다. 전송된 메일을 확인해 주세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changeVisibility("logout")
                                } else {
                                    Toast.makeText(
                                        baseContext, "메일 전송 실패",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changeVisibility("logout")
                                }
                            }
                    } else {
                        Toast.makeText(
                            baseContext, "회원가입 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                        changeVisibility("logout")
                    }
                }

        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email: String = binding.authEmailEditView.text.toString()
            val password: String = binding.authPasswordEditView.text.toString()

            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            //로그인 성공 상황...........
                            MyApplication.email = email
                            changeVisibility("login")
                        } else {
                            //발송된 메일로 인증 확인을 안한경우...........
                            Toast.makeText(
                                baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            baseContext, "로그인 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult - " + requestCode)

        //구글 로그인 결과 처리...........................
        if (requestCode == 10) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //구글 로그인 성공..
                            MyApplication.email = account.email
                            changeVisibility("login")
                        } else {
                            //구글 로그인 실패..
                            changeVisibility("logout")
                        }
                    }

            } catch (e: ApiException) {
                Log.d(TAG, "error - $e")
                changeVisibility("logout")
            }
        }
    }


    fun changeVisibility(mode: String) {
        if (mode === "login") {
            binding.run {
                authMainTextView.text = "${MyApplication.email} 님 반갑습니다."
                logoutBtn.visibility = View.VISIBLE
                goSignInBtn.visibility = View.GONE
                googleLoginBtn.visibility = View.GONE
                authEmailEditView.visibility = View.GONE
                authPasswordEditView.visibility = View.GONE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.GONE
            }

        } else if (mode === "logout") {
            binding.run {
                authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.VISIBLE
                googleLoginBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
            }
        } else if (mode === "signin") {
            binding.run {
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.GONE
                googleLoginBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
            }
        }
    }
}