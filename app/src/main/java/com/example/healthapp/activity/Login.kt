package com.example.healthapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.healthapp.R
import com.example.healthapp.data.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.view.*
import kotlinx.android.synthetic.main.register.*

class Login: Fragment() {
    private lateinit var Email: String
    private lateinit var Password: String
    private lateinit var dialog:ProgressDialog
    private lateinit var mAuth:FirebaseAuth
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(requireContext())
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
        v.emailLogin.editText?.doAfterTextChanged {
            v.emailLogin.error=null
        }
        v.password_text_input.editText?.doAfterTextChanged {
            v.password_text_input.error=null
        }
        v.register_pg.setOnClickListener {
            val sign= Signup()
            val transction:FragmentTransaction=requireFragmentManager().beginTransaction()
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
            prefs.status = 1
            prefs.email = firebaseUser.email
            startActivity(Intent(activity,MainActivity::class.java))
            activity?.finish()

        }

    }
    private fun validate(){
        Email= emailLogin.editText?.text.toString()
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
        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailLogin.editText?.text.toString(),password_text_input.editText?.text.toString())
            .addOnSuccessListener {
                dialog.dismiss()
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    Firebase.firestore.collection("users").document(user.uid).get()
                        .addOnCompleteListener { t ->

                            val doc = t.result
                            if (doc != null && !doc.exists()) {
                                Toast.makeText(
                                    requireContext(),
                                    "User Does Not Exist. Please Signup",
                                    Toast.LENGTH_LONG
                                ).show()
                                FirebaseAuth.getInstance().signOut()
            //                                        googleSignInClient.signOut()
                                return@addOnCompleteListener
                            } else if (doc != null && doc.exists()) {
                                prefs.username = doc.getString("username").toString()
                                prefs.email = doc.getString("email").toString()
                                prefs.name = doc.getString("name").toString()
                                prefs.age = Integer.parseInt(doc.get("age").toString())
                                prefs.status = 1
                                startActivity(Intent(activity,MainActivity::class.java))
                                activity?.finish()
                            }
                        }
                }
            }
            .addOnFailureListener { e->
                dialog.dismiss()
                Toast.makeText(activity,"SignUp Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }


    }
}
