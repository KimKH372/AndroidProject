package com.example.howlstargram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.howlstargram.R
import com.example.howlstargram.databinding.ActivityInputNumberBinding

class InputNumberActivity : AppCompatActivity() {
    lateinit var binding: ActivityInputNumberBinding
    val inputNumberViewModel : InputNumberViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_number)
        binding.viewModel = inputNumberViewModel
        binding.lifecycleOwner = this // 생명주기 연결
        setObserve()
    }

    // 다음 페이지로 넘어가기 위한 함수
    fun setObserve(){
        inputNumberViewModel.nextPage.observe(this){
            if (it){
                finish()
                // 이메일 인증자만 로그인 가능하도록 해당 페이지로 넘김
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
    }
}