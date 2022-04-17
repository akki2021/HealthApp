package com.example.healthapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.healthapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.view.*
import kotlinx.android.synthetic.main.register.*

class Login: Fragment() {
    private lateinit var Email: String
    private lateinit var Password: String
    private lateinit var dialog:ProgressDialog
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkuser()
        dialog= ProgressDialog(activity)
        dialog.setTitle("Please Wait")
        dialog.setMessage("Logging In..")
        dialog.setCanceledOnTouchOutside(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.activity_log_in,container,false)
        v.username.editText?.doAfterTextChanged {
            v.username.error=null
        }
        v.password_text_input.editText?.doAfterTextChanged {
            v.password_text_input.error=null
        }
        v.register_pg.setOnClickListener {
            val sign= Signup()
            val transction:FragmentTransaction=fragmentManager!!.beginTransaction()
            transction.replace(R.id.frag,sign)
            transction.commit()
        }
        v.loginn.setOnClickListener {
            validate()
        }
        return v
    }
    private fun checkuser(){
        val firebaseUser= FirebaseAuth.getInstance().currentUser
        if(firebaseUser!= null){
            startActivity(Intent(activity,MainActivity::class.java))
            activity?.finish()

        }

    }
    private fun validate(){
        Email=username.editText?.text.toString()
        Password=password_text_input.editText?.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            username.error="Invalid Email format"
        }
        if(Password.length<8){
            password_text_input.error="Password must atleast 8 character"
        }
        else{
            firebaselogin()
        }
    }

    private fun firebaselogin() {
        dialog.show()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username.editText?.text.toString(),password_text_input.editText?.text.toString())
            .addOnSuccessListener {
                dialog.dismiss()
                val firebaseuser=FirebaseAuth.getInstance().currentUser
                val Email=firebaseuser!!.email
                Toast.makeText(activity,"Account Created with email $Email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity,MainActivity::class.java))
                activity?.finish()
            }
            .addOnFailureListener { e->
                dialog.dismiss()
                Toast.makeText(activity,"SignUp Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }


    }
}
