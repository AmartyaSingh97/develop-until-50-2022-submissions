package com.example.todolistapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

class Login : Fragment() {

    //Declare variables
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var mProgressBar: ProgressBar? = null
    private var mLogin : Button? = null
    private var mNavHostFragment: NavHostFragment? = null
    private var fAuth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get Instances
        mEmail = view?.findViewById(R.id.EmailAddress)
        mPassword = view?.findViewById(R.id.Password)
        mProgressBar = view?.findViewById(R.id.progressBar)
        mLogin = view?.findViewById(R.id.LoginButton)

        //Get Firebase Instance
        fAuth = FirebaseAuth.getInstance()
        //Get NavHostFragment Instance
        mNavHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.login) as NavHostFragment?

        //Set Login Button On Click Listener
        mLogin?.setOnClickListener(View.OnClickListener {
            val email = mEmail?.text.toString().trim()
            val password = mPassword?.text.toString().trim()

            if (email.isEmpty()) {
                mEmail?.error = "Email is Required."
                return@OnClickListener
            }

            if (password.isEmpty()) {
                mPassword?.error = "Password is Required."
                return@OnClickListener
            }

            if (password.length < 6) {
                mPassword?.error = "Password Must be >= 6 Characters"
                return@OnClickListener
            }

            mProgressBar?.visibility = View.VISIBLE

            // authenticate the user
            fAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // Sign in success, go to the user profile fragment
                    Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                    mNavHostFragment?.navController?.navigate(R.id.userProfile)
                } else {

                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Error ! " + task.exception?.message, Toast.LENGTH_SHORT).show()
                    mProgressBar?.visibility = View.GONE
                }
            }
        })

    }

}