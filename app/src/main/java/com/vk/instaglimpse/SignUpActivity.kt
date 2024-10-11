package com.vk.instaglimpse

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.vk.instaglimpse.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignInLink.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {

        val userName = binding.etUsername.text.toString()
        val fullName = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPass.text.toString()

        when {
            userName.isEmpty() -> Toast.makeText(
                this,
                "Username is required",
                Toast.LENGTH_SHORT
            ).show()
            fullName.isEmpty() -> Toast.makeText(
                this,
                "Name is required",
                Toast.LENGTH_SHORT
            ).show()
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
                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setCancelable(false)
                progressDialog.setTitle("Sign Up")
                progressDialog.setMessage("Please wait...")
                progressDialog.show()
                val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    task ->
                     if (task.isSuccessful){

                         saveUserInfo(fullName,userName,email,progressDialog)
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

    private fun saveUserInfo(
        fullName: String,
        userName: String,
        email: String,
        progressDialog: ProgressDialog
    ) {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserId
        userMap["fullname"] = fullName
        userMap["username"] = userName
        userMap["email"] = email
        userMap["bio"] = "Hey, I am using InstaGlimpse"
        userMap["image"] = "gs://instaglimpse-5f10d.appspot.com/Default images/profile.png"

        userRef.child(currentUserId).setValue(userMap).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Account has been created successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else {
                Toast.makeText(
                    this,
                    task.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
        }
    }
}