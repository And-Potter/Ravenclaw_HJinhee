package com.example.sopt_1.presentation.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sopt_1.data.FollowingUserInfo
import com.example.sopt_1.data.RepositoryInfo
import com.example.sopt_1.databinding.ActivityHomeBinding
import com.example.sopt_1.databinding.FragmentFollowingListBinding
import com.example.sopt_1.presentation.signup.SignUpActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var repositoryListAdapter: RepositoryListAdapter


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

        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        repositoryListAdapter =
            RepositoryListAdapter()

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.listRepository.adapter = repositoryListAdapter

        repositoryListAdapter.userList.addAll(
            listOf<RepositoryInfo>(
                RepositoryInfo(
                    repoName = "And-Potter/Ravenclaw_HJinhee",
                    repoInfo = "진희의 솝트 레포지터리",
                    repoLanguage = "Kotlin"
                ),
                RepositoryInfo(
                    repoName = "레포지터리 이름이 엄청 길 때 ... 표시되도록 하는거 보여주려고 이렇게 길게 쎄보는 중입니다아",
                    repoInfo = "레포지터리 설명이 엄청 길 때 ... 표시되도록 하는거 보여주려고 이렇게 길게 쎄보는 중입니다아",
                    repoLanguage = "Java"
                )
            )
        )

        repositoryListAdapter.notifyDataSetChanged()

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