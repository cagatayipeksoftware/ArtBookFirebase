package com.cagatayipek.fragmentartlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.cagatayipek.fragmentartlist.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*

class login : Fragment() {
    val auth= Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signup.setOnClickListener {
            val mail=email.text.toString()

            val password=password.text.toString()

            auth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener {
                val action= loginDirections.actionLoginToArtbookfeed()

                Navigation.findNavController(view).navigate(action)

            }.addOnFailureListener {

                Toast.makeText(activity,it.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }
        signin.setOnClickListener {
            val email=email.text.toString()
            val password=password.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val action= loginDirections.actionLoginToArtbookfeed()
                Navigation.findNavController(view).navigate(action)
            }.addOnFailureListener {
                Toast.makeText(activity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signIn(view: View){
        val mail=email.text.toString()
        val pass=password.text.toString()
        auth.signInWithEmailAndPassword(mail,pass).addOnSuccessListener {  }
    }





}