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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.healthapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register.view.*

class Signup: Fragment() {
    private lateinit var Email: String
    private lateinit var Mobile: String
    private lateinit var First_name : String
    private lateinit var Last_name : String
    private lateinit var Password : String
    private lateinit var dialog:ProgressDialog
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog= ProgressDialog(activity)
        dialog.setTitle("Please Wait")
        dialog.setMessage("Creating Account .. ")
        dialog.setCanceledOnTouchOutside(false)
        mAuth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.register,container,false)
        v.email.editText?.doAfterTextChanged {
            v.email.error=null
        }
        v.first_name.editText?.doAfterTextChanged {
            v.first_name.error=null
        }
        v.last_name.editText?.doAfterTextChanged {
            v.last_name.error=null
        }
        v.mobile.editText?.doAfterTextChanged {
            v.mobile.error=null
        }
        v.password.editText?.doAfterTextChanged {
            v.password.error=null
        }
        v.register_btn.setOnClickListener {
            validate_data()
        }
        return v
    }
    private fun  validate_data(){
        Email=email.editText?.text.toString()
        First_name=first_name.editText?.text.toString()
        Mobile=mobile.editText?.text.toString()
        Last_name=last_name.editText?.text.toString()
        Password=password.editText?.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.error="Invalid Email"
            
        }
        if(First_name.isEmpty()){
            first_name.error="Enter first name"
        }
        if(Last_name.isEmpty()){
            last_name.error="Enter last name"
        }
        if(Mobile.length!=10){
            mobile.error="Invalid number"
        }
        if(Password.length<8){
            password.error="Password must atleast 8 character"
        }

        else{
            firebaseSign()
        }
    }

    private fun firebaseSign() {

        mAuth.createUserWithEmailAndPassword(email.editText?.text.toString(),password.editText?.text.toString())
            .addOnSuccessListener {

                val firebaseuser=FirebaseAuth.getInstance().currentUser
                val Emailt=firebaseuser!!.email
                val map= HashMap<String, String>()
                map["First_name"]=first_name.editText?.text.toString()
                map["Last_name"]=last_name.editText?.text.toString()
                map["Email"]=email.editText?.text.toString()
                map["Mobile"]=mobile.editText?.text.toString()
                database.getReference("User").child(firebaseuser.uid)
                    .setValue(map).addOnCompleteListener { task1: Task<Void?> ->
                        Toast.makeText(activity,"Account Created with email $Emailt",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity,MainActivity::class.java))
                        activity?.finish()
                    }



            }
            .addOnFailureListener { e->
                dialog.dismiss()
                Toast.makeText(activity,"SignUp Failed due to ${e.message}",Toast.LENGTH_LONG).show()

            }

    }


}
