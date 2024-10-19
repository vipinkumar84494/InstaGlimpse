package com.vk.instaglimpse

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.vk.instaglimpse.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUpLink.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {
            signInUser()
        }


    }

    private fun signInUser() {
            val email = binding.etEmail.text.toString()
            val password = binding.etPass.text.toString()

            when {
                email.isEmpty() -> Toast.makeText(
                    this,
                    "Email is required",
                    Toast.LENGTH_SHORT
                ).show()
                password.isEmpty() -> Toast.makeText(
                    this,
                    "Password is required",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {
                    val progressDialog = ProgressDialog(this@SignInActivity)
                    progressDialog.setCancelable(false)
                    progressDialog.setTitle("Sign In")
                    progressDialog.setMessage("Please wait...")
                    progressDialog.show()
                    val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                            task ->
                        if (task.isSuccessful){
                            progressDialog.dismiss()
                            val intent = Intent(this,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(
                                this,
                                task.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
                }

            }

    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}