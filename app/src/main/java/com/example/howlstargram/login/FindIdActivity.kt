package com.example.howlstargram.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.howlstargram.R
import com.example.howlstargram.databinding.ActivityFindIdBinding

class FindIdActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindIdBinding
    val findIdViewModel : FindIdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_id)
        binding.activity = this
        binding.viewModel = findIdViewModel
        binding.lifecycleOwner = this
        setObserve()
    }

    fun setObserve(){ // 토스트 메시지 출력
        findIdViewModel.toastMessage.observe(this){
            if (!it.isEmpty()){ // 결과값이 비어있지 않으면
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}