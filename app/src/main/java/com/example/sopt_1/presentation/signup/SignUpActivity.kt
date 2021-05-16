package com.example.sopt_1.presentation.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sopt_1.api.ServiceCreator
import com.example.sopt_1.data.request.RequestSignUpData
import com.example.sopt_1.data.response.ResponseSignUpData
import com.example.sopt_1.databinding.ActivitySignUpBinding
import com.example.sopt_1.presentation.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(CURRENT_ACTIVITY, "Called onCreate")

        signUpButtonClickEvent()

    }

    private fun signUpButtonClickEvent() {
        binding.btnSignUp.setOnClickListener {

            val userName = binding.etSignupName.text
            val userId = binding.etSignupId.text
            val userPw = binding.etSignupPw.text
            if (userName.isNullOrBlank() || userId.isNullOrBlank() || userPw.isNullOrBlank()) {
                Toast.makeText(this@SignUpActivity, "빈 칸이 있는지 확인해주세요", Toast.LENGTH_SHORT)
                    .show()
            } else {
                signUpCommunicateServer()

                val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                intent.putExtra("userName", userName.toString())
                .putExtra("userId", userId.toString())
                .putExtra("userPw", userPw.toString())

                setResult(
                    RESULT_OK,
                    intent
                )   //setResult() 메소드로 결과를 저장 -> 성공 : RESULT_OK, 실패 : RESULT_CANCEL
                finish()
            }
        }
    }

    private fun signUpCommunicateServer(){
        //서버로 보낼 회원가입 데이터 생성
        val requestSignUpData = RequestSignUpData(
            id = binding.etSignupId.text.toString(),
            password = binding.etSignupPw.text.toString(),
            sex = "0",
            nickname = binding.etSignupName.text.toString(),
            phone = "010-0000-0000",
            birth = "1999-00-00"
        )

        val call: Call<ResponseSignUpData> = ServiceCreator.loginService.postSignUp((requestSignUpData))

        call.enqueue(object: Callback<ResponseSignUpData> {
            override fun onResponse(
                call: Call<ResponseSignUpData>,
                response: Response<ResponseSignUpData>
            ) {
                if(response.isSuccessful){
                    val data = response.body()?.data
                    Toast.makeText(this@SignUpActivity, "회원가입 성공 \n 이름 : "+data?.nickname, Toast.LENGTH_SHORT).show()

                }else{
                    //에러났을 때 코드
                    Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpData>, t: Throwable) {
                Log.d("NetworkTestSignUp","error:$t")
            }
        })
    }

    companion object {
        const val CURRENT_ACTIVITY = "SignUpActivity"
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