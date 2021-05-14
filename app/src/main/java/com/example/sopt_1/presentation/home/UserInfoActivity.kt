package com.example.sopt_1.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sopt_1.R
import com.example.sopt_1.databinding.ActivityUserInfoBinding

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val followingListFragment = FollowingListFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_user_info, followingListFragment)
        transaction.commit()
    }
}