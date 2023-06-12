package com.example.howlstargram.login

import android.app.Application
import android.view.View
import androidx.browser.trusted.Token
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.howlstargram.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var auth = FirebaseAuth.getInstance()
    var id : MutableLiveData<String> = MutableLiveData("howl")
    var password : MutableLiveData<String> = MutableLiveData("")

    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false) // 로그인 성공 시 로그인 InputNumberActivity로
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false) // 클릭 시 비번찾기로
    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    val context = getApplication<Application>().applicationContext

    var googleSignInClient : GoogleSignInClient

    init {
        // 옵션 값 설정
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // 토큰 보유자만 로그인 가능
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context,gso)
    }

    fun loginWithSignupEmail(){
        println("Email")
        auth.createUserWithEmailAndPassword(id.value.toString(),password.value.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                showInputNumberActivity.value = true
            }else{
                // 아이디가 있을 경우(회원가입x)
                loginEmail()
            }
        }

    }

    // 이메일 인증자만 메인화면으로
    fun loginEmail(){
        auth.signInWithEmailAndPassword(id.value.toString(),password.value.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                if (it.result.user?.isEmailVerified == true){
                    showMainActivity.value = true // 인증 유저는 메인으로
                }else{
                    showInputNumberActivity.value = true // 미인증 유저는 여기로 넘어감
                }
                }
            }
    }

    fun loginGoogle(view : View){
        var i = googleSignInClient.signInIntent
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)
        // 로그인 액티비티 googleLoginResult 변수와 연결
    }

    // 암호화 풀어주는 함수
    fun firebaseAuthWithGoogle(idToken: String?){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            // 아이디, 비번 입력 없이 바로 로그인 처리 가능
            if (it.isSuccessful){
                if (it.result.user?.isEmailVerified == true){
                    showMainActivity.value = true // 인증 유저는 메인으로
                }else{
                    showInputNumberActivity.value = true // 미인증 유저는 여기로 넘어감
                }
            }
        }
    }


}