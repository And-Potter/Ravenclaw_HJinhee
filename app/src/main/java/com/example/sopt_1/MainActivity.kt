package com.example.sopt_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sopt_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtonClickEvent()
        replaceActivity()


    }
    private fun replaceActivity(){
        binding.btnReplace.setOnClickListener{
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun initButtonClickEvent(){
        binding.btnLogin.setOnClickListener{

            val userId = binding.etId.text
            if(userId.isNullOrBlank()){
                Toast.makeText(this@MainActivity, "id를 입력해주세요",Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this@MainActivity, "안녕하세요",Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}