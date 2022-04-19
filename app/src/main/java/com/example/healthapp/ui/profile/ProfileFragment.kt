package com.example.healthapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gdsc_hackathon.extensions.timer
import com.example.healthapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_reply.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var  name: TextView
    private lateinit var  email: TextView
    private lateinit var  age: TextView
    private lateinit var  progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_profile, container, false)
        name = rootView.findViewById(R.id.name)
        email = rootView.findViewById(R.id.emaill)
        age = rootView.findViewById(R.id.age)
        progressBar = rootView.findViewById(R.id.progress_bar)




        val user = FirebaseAuth.getInstance().currentUser
        timer(progressBar, 400)
        Firebase.firestore.collection("users").document(user!!.uid).get().addOnCompleteListener {
            val doc = it.result
            if(doc!=null) {
                name.text = doc.getString("name").toString()
                email.text = doc.getString("email").toString()
                age.text = doc.getLong("age").toString()


            }
        }
        return rootView
    }
}