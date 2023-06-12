package com.example.howlstargram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import androidx.databinding.DataBindingUtil
import com.example.howlstargram.databinding.ActivityMainBinding
import com.example.howlstargram.fragment.AlarmFragment
import com.example.howlstargram.fragment.DetailViewFragment
import com.example.howlstargram.fragment.GridFragment
import com.example.howlstargram.fragment.UserFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home ->{
                    var f = DetailViewFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()

                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.action_search ->{
                    var f = GridFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.action_favorite_alarm ->{
                    var f = AlarmFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()
                    return@setOnNavigationItemSelectedListener  true
            }
                R.id.action_account ->{
                    var f = UserFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()
                    return@setOnNavigationItemSelectedListener  true
                }
             }
            return@setOnNavigationItemSelectedListener false
        }
    }
}