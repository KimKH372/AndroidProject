package com.example.howlstargram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.howlstargram.MainActivity
import com.example.howlstargram.R
import com.example.howlstargram.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth // 회원가입 관리
    lateinit var binding : ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java] // 액티비티와 생명주기 맞춤
        binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        auth = FirebaseAuth.getInstance()
        setObserve()
    }

    fun setObserve(){ // 값이 변환될 때 액티비티가 구현되도록
        loginViewModel.showInputNumberActivity.observe(this){
            if(it){
                finish()
                startActivity(Intent(this,InputNumberActivity::class.java))
                //로그인 성공 시 InputNumberActivity로
            }
        }
        loginViewModel.showFindIdActivity.observe(this){
            if(it){
                startActivity(Intent(this,FindIdActivity::class.java))
            }
        }
        loginViewModel.showMainActivity.observe(this){ // 메인화면으로 넘어가는 옵저브
            if (it){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }


    fun findId(){
        println("findId")
      loginViewModel.showFindIdActivity.value = true
    }

    // 구글 로그인 성공하면 결과값 받는 함수
    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->

        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)

        loginViewModel.firebaseAuthWithGoogle(account.idToken) // 로그인한 사용자 정보 암호화
    }
}