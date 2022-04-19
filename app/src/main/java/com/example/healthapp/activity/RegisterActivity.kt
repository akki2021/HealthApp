package com.example.healthapp.activity



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.healthapp.R
import com.example.healthapp.data.Prefs


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        val prefs = Prefs(this)
        if(prefs.status != -1){
            startActivity(Intent(this,MainActivity::class.java))
            this.finish()
        }
        else{
            val log=Login()
            val fm:FragmentManager=supportFragmentManager
            fm.beginTransaction().add(R.id.frag,log).commit()
        }
    }
}
