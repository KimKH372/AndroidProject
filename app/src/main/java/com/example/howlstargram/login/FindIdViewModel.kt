package com.example.howlstargram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FindIdViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance() // 계정
    var firestore = FirebaseFirestore.getInstance() // 디비
    var id = ""
    var phoneNumber = ""
    var toastMessage = MutableLiveData("")

    fun findMyId(){
        firestore.collection("findIds").whereEqualTo("phoneNumber", phoneNumber).get().addOnCompleteListener {
            println(it.result.documents)
            if(it.isSuccessful && it.result.documents.size > 0){ // 값이 하나 이상 나오면
                var findIdModel = it.result.documents.first().toObject(FindIdModel::class.java)
                // first -> 값을 배열로 가저오기 위해 사용. 첫번째 값을 가져와서 캐스팅, 중복 방지
                toastMessage.value = "당신의 아이디는 " + findIdModel?.id
            }else{
                toastMessage.value = "정보가 정확하지 않습니다."
            }
        }  // 디비 검색, 입력한 번호가 맞으면 값을 가져옴
    }
    fun findMyPassword(){
        auth.sendPasswordResetEmail(id).addOnCompleteListener {
            if (it.isSuccessful){
                toastMessage.value = "비밀번호를 초기화 했습니다."
            }else{
                toastMessage.value = "정보가 정확하지 않습니다."
            }
        }
    }
}