package com.silvinda.keripikstore.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.silvinda.keripikstore.databinding.ActivitySignInBinding
import com.silvinda.keripikstore.home.HomeScreenActivity
import com.silvinda.keripikstore.sign.User
import com.silvinda.keripikstore.sign.signup.SignUpActivity
import com.silvinda.keripikstore.utils.Preferences


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var iUsername: String
    private lateinit var iPassword: String

    private lateinit var mDatabse: DatabaseReference
    private lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mDatabse = FirebaseDatabase.getInstance("https://keripik-store-cbf2a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User")
        preferences = Preferences(this)

        preferences.setValues("onboarding", "1")
        if (preferences.getValues("status").equals("1")){
            finishAffinity()
            startActivity(Intent(this@SignInActivity, HomeScreenActivity::class.java))
        }

        binding.ivHome.setOnClickListener {
            iUsername = binding.edtUsername.text.toString()
            iPassword = binding.edtPassword.text.toString()

            if(iUsername == ""){
                binding.edtUsername.error = "Silhkan masukan Username Anda"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }
            if(iPassword == ""){
                binding.edtPassword.error = "Silahkan masukan Password Anda"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }
            val statusUsername = iUsername.indexOf(".")
            if(statusUsername >= 0){
                binding.edtUsername.error = "Silahkan masukan Username Anda tanpa '.'"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }
            pushLogin(iUsername, iPassword)
        }

        binding.btnDaftar.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabse.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if(user == null){
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()
                    return
                }
                if(user.password.equals(iPassword)){
                    Toast.makeText(this@SignInActivity, "Selamat Datang", Toast.LENGTH_LONG).show()

                    preferences.setValues("nama", user.nama.toString())
                    preferences.setValues("user", user.username.toString())
                    preferences.setValues("url", user.url.toString())
                    preferences.setValues("email", user.email.toString())
                    preferences.setValues("saldo", user.saldo.toString())
                    preferences.setValues("status", "1")

                    finishAffinity()

                    startActivity(Intent(this@SignInActivity, HomeScreenActivity::class.java))
                    return
                }
                Toast.makeText(this@SignInActivity, "Password Anda Salah!", Toast.LENGTH_LONG).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, ""+ error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
