package com.example.healthapp.activity



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.healthapp.R


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        val log=Login()
        val fm:FragmentManager=supportFragmentManager
        fm.beginTransaction().add(R.id.frag,log).commit()
    }
}
