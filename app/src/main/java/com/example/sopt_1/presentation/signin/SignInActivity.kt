package com.example.sopt_1.presentation.signin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sopt_1.api.ServiceCreator
import com.example.sopt_1.data.request.RequestLoginData
import com.example.sopt_1.data.response.ResponseLoginData
import com.example.sopt_1.databinding.ActivitySignInBinding
import com.example.sopt_1.presentation.home.HomeActivity
import com.example.sopt_1.presentation.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private val signUpActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //데이터를 받아서 할 일이 들어가는 칸
            if (it.resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    binding.etLoginId.setText(it.data?.extras?.getString("userId"))
                    binding.etLoginPw.setText(it.data?.extras?.getString("userPw"))
                }
            }

        }


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
                loginCommunicateServer()
            }
        }
    }

    private fun startHomeActivity(){
        //HomeActivity로 이동
        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun loginCommunicateServer(){
        //서버로 보낼 로그인 데이터 생성
        val requestLoginData = RequestLoginData(
            id = binding.etLoginId.text.toString(),
           password = binding.etLoginPw.text.toString()
        )

        val call: Call<ResponseLoginData> = ServiceCreator.loginService.postLogin((requestLoginData))

        call.enqueue(object: Callback<ResponseLoginData> {
            override fun onResponse(
                call: Call<ResponseLoginData>,
                response: Response<ResponseLoginData>
            ) {
                if(response.isSuccessful){
                    val data = response.body()?.data
                    Toast.makeText(this@SignInActivity, "로그인 성공 \n userNickname : "+data?.user_nickname, Toast.LENGTH_SHORT).show()

                    startHomeActivity()
                }else{
                    //에러났을 때 코드
                    Toast.makeText(this@SignInActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                Log.d("NetworkTestSignIn","error:$t")
            }
        })
    }

    private fun signUpClickEvent() {
        binding.tvSignUp.setOnClickListener {

            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            signUpActivityLauncher.launch(intent)

        }
    }


    companion object {
        const val CURRENT_ACTIVITY = "SignInActivity"
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