package com.example.healthapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.gdsc_hackathon.extensions.openEmailApp
import com.example.gdsc_hackathon.extensions.showSnackBar
import com.example.gdsc_hackathon.extensions.showSnackBarWithAction
import com.example.healthapp.R
import com.example.healthapp.data.Prefs
import com.example.healthapp.data.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register.view.*
import kotlin.properties.Delegates

class Signup: Fragment() {
    private lateinit var Email: String
    private var Age by Delegates.notNull<Int>()
    private lateinit var Username : String
    private lateinit var Name : String
    private lateinit var Password : String
    private lateinit var ConfirmPassword : String
    private lateinit var dialog:ProgressDialog
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var prefs: Prefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog= ProgressDialog(activity)
        dialog.setTitle("Please Wait")
        dialog.setMessage("Creating Account .. ")
        dialog.setCanceledOnTouchOutside(false)
        mAuth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        prefs = Prefs(requireContext())
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
        v.username.editText?.doAfterTextChanged {
            v.username.error=null
        }
        v.name.editText?.doAfterTextChanged {
            v.name.error=null
        }
        v.ageLayout.editText?.doAfterTextChanged {
            v.age.error=null
        }
        v.password.editText?.doAfterTextChanged {
            v.password.error=null
        }
        v.register_btn.setOnClickListener {
            validate_data()
        }
//        v.usernameText.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // Do Nothing
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                username.error = null
//                Log.d("Anam", "getting")
//                if(checkIfUsernameExits(s.toString())){
//                    Log.d("Anam", "done")
//                    username.error = "Username already taken"
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
        return v
    }
    private fun  validate_data(){
        Email=email.editText?.text.toString()
        Username=username.editText?.text.toString()
        Age= Integer.parseInt(ageLayout.editText?.text.toString())
        Name=name.editText?.text.toString()
        Password=password.editText?.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.error="Invalid Email"
            
        }
        if(Username.isEmpty()){
            username.error="Enter username"
        }
        if(Name.isEmpty()){
            name.error="Enter Name"
        }
        if(Age < 1 || Age > 110){
            ageLayout.error="Invalid age"
        }
        if(Password.length<8){
            password.error="Password must atleast 8 character"
        }
        if(ConfirmPassword != Password){
            confirmPassword.error="Password must match"
        }

        else{
            firebaseSign()
        }
    }

//    private fun checkIfUsernameExits(username: String): Boolean {
//        val ref = FirebaseFirestore.getInstance().collection("users")
//        var bool = false
//        ref.whereEqualTo("username", username).get().addOnCompleteListener{
//            val result = it.result
//            Log.d("Anam", it.result.query.toString())
//            if(!result.isEmpty) {
//                bool = true
//            }
//        }.addOnFailureListener{
//            Log.d("Anam", it.toString())
//        }
//        return bool
//    }

    private fun firebaseSign() {

        mAuth.createUserWithEmailAndPassword(email.editText?.text.toString(),password.editText?.text.toString())
            .addOnSuccessListener {

                val firebaseUser= FirebaseAuth.getInstance().currentUser
                val user = User(Username, Name, Email, Age)
                if (firebaseUser != null) {
                    Firebase.firestore.collection("users").document(firebaseUser.uid)
                        .set(user)
                        .addOnCompleteListener { it ->
                            if (it.isSuccessful) {
                                Log.w("LOOK", "USER CREATED ON FIREBASE")
                                firebaseUser.sendEmailVerification().addOnCompleteListener {
//                                    prefs.status = 1
//                                    prefs.username = Username
//                                    prefs.age = Age
//                                    prefs.name = Name
//                                    prefs.email = Email
                                    startActivity(Intent(activity, RegisterActivity::class.java))
                                    activity?.finish()
                                }
                            }
                        }.addOnFailureListener {
                            Log.e("error", it.toString())
                        }
                }
            }
    }

}
