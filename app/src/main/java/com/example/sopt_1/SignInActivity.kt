package com.example.sopt_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sopt_1.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(CURRENT_ACTIVITY, "Called onCreate")

        loginButtonClickEvent()
        signUpClickEvent()


    }

    private fun loginButtonClickEvent() {
        binding.btnLogin.setOnClickListener {

            val userId = binding.etLoginId.text
            val userPw = binding.etLoginPw.text
            if (userId.isNullOrBlank() || userPw.isNullOrBlank()) {
                Toast.makeText(this@SignInActivity, "아이디/비밀번호를 확인해주세요!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //HomeActivity로 이동
                val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                startActivity(intent)

                Toast.makeText(this@SignInActivity, "로그인 성공", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun signUpClickEvent() {
        binding.tvSignUp.setOnClickListener {
            //SignUpActivity로 이동, registerActivityForResult 이용
            //startActivity : 새 액티비티를 열어줌
            //startActivityForResult : 새 액티비티를 열어줌 + 결과값 전달

            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivityForResult(
                intent,
                REQUEST_CODE
            )     //int형 requestCode -> 여러 액티비티를 쓰는 경우, 어떤 Activity인지 식별하는 값

        }
    }

    //onActivityResult() 메소드 : 호출된 Activity에서 저장한 결과를 돌려줌
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(
                    this@SignInActivity, "RESULT_OK"
                            + "/n이름 : " + data?.getStringExtra("userName")
                            + "/n깃허브 아이디 : " + data?.getStringExtra("userId")
                            + "/n비밀번호 : " + data?.getStringExtra("userPw"), Toast.LENGTH_SHORT
                ).show();
            } else {   // RESULT_CANCEL
                Toast.makeText(this@SignInActivity, "RESULT_CANCEL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    companion object {
        const val CURRENT_ACTIVITY = "SignInActivity"
        const val REQUEST_CODE = 0
    }

    override fun onStart() {
        super.onStart()
        Log.d(CURRENT_ACTIVITY, "Called onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(CURRENT_ACTIVITY, "Called onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(CURRENT_ACTIVITY, "Called onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(CURRENT_ACTIVITY, "Called onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(CURRENT_ACTIVITY, "Called onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(CURRENT_ACTIVITY, "Called onDestroy")
    }


}