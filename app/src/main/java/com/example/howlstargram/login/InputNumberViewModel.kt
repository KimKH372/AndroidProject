package com.example.howlstargram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// 아이디 찾기 시 사용할 폰 번호와 아이디 매핑
data class FindIdModel(var id : String? = null, var phoneNumber: String? = null)

class InputNumberViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance() // 계정 관리
    var fireStore =FirebaseFirestore.getInstance() // 디비 관리
    var nextPage = MutableLiveData(false) // 정상적으로 데이터 입력하면 다음 페이지로
    var inputNumber = "" // 폰 번호 받는 변수

    // 폰 번호 저장하는 변수
    fun savePhoneNumber(){
        var findIdModel = FindIdModel(auth.currentUser?.email, inputNumber) // 해당 데이터 값 가져옴
        //디비에 저장
        fireStore.collection("findIds").document().set(findIdModel).addOnCompleteListener {
            if(it.isSuccessful){
                nextPage.value = true // 트루면 다음 페이지로 넘어감
                auth.currentUser?.sendEmailVerification() // 가입 된 메일 주소가 유효한지 인증 메일 전송
            }
        }
    }
}