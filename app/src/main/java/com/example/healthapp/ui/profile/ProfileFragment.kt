package com.example.healthapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var database: DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val user=FirebaseAuth.getInstance().currentUser
//        database=FirebaseDatabase.getInstance().getReference("User")
//        if(user!=null){
//            database.child(user.uid).addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    val map = snapshot.value as Map<String,String>?
//                    val f_name=map!!["First_name"]
//                    val l_name=map!!["Last_name"]
//                    val emailm=map!!["Email"]
//                    val Mobile=map!!["Mobile"]
//                    val nam_e=f_name +" "+ l_name
//                    name.text = nam_e
//                    emaill.text=emailm
//                    contact.text = Mobile
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//            })
//        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}