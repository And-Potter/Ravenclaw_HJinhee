package com.example.sopt_1.presentation.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sopt_1.databinding.ActivityHomeBinding
import com.example.sopt_1.presentation.signup.SignUpActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val userInfoActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //데이터를 받아서 할 일이 들어가는 칸

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(CURRENT_ACTIVITY, "Called onStart")


        initMoreButtonClickEvent()


    }

    private fun initMoreButtonClickEvent() {
        binding.btnHomeMore.setOnClickListener{
            val intent = Intent(this@HomeActivity, UserInfoActivity::class.java)
            userInfoActivityLauncher.launch(intent)
        }

    }
    companion object {
        const val CURRENT_ACTIVITY = "HomeActivity"
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