package com.silvinda.keripikstore.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.silvinda.keripikstore.databinding.ActivitySignUpBinding
import com.silvinda.keripikstore.sign.User
import com.silvinda.keripikstore.sign.signin.SignInActivity
import com.silvinda.keripikstore.utils.Preferences

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mDatabse: DatabaseReference
    private lateinit var preferences: Preferences


    private lateinit var sUsername: String
    private lateinit var sPassword: String
    private lateinit var sNama: String
    private lateinit var sEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mDatabse = FirebaseDatabase.getInstance("https://keripik-store-cbf2a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User")
        preferences = Preferences(this)

        binding.ivBack.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        }

        binding.btnLanjut.setOnClickListener {
            sUsername = binding.edtUsername.text.toString()
            sPassword = binding.edtPass.text.toString()
            sNama = binding.edtNama.text.toString()
            sEmail = binding.edtEmail.text.toString()

            if(sUsername == "") {
                binding.edtUsername.error = "Silahkan isi Username"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }
            if(sPassword == "") {
                binding.edtPass.error = "Silahkan isi Password"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }
            if(sNama == "") {
                binding.edtNama.error = "Silahkan isi Nama"
                binding.edtNama.requestFocus()
                return@setOnClickListener
            }
            if(sEmail == "") {
                binding.edtEmail.error = "Silahkan isi Email"
                binding.edtNama.requestFocus()
                return@setOnClickListener
            }

            val statusUsername = sUsername.indexOf(".")
            if(statusUsername >= 0) {
                binding.edtUsername.error = "Masukan Username tanpa."
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }

            saveUser(sUsername, sPassword, sNama, sEmail)
        }
    }

    private fun saveUser(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.nama = sNama
        user.password = sPassword

        writeData(sUsername, user)
    }

    private fun writeData(sUsername: String, iUser: User) {
        mDatabse.child(sUsername).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val user = data.getValue(User::class.java)
                if(user != null) {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()
                    return
                }

                mDatabse.child(sUsername).setValue(iUser)
                preferences.setValues("nama", iUser.nama.toString())
                preferences.setValues("user", iUser.username.toString())
                preferences.setValues("saldo", "")
                preferences.setValues("url", "")
                preferences.setValues("email", iUser.email.toString())
                preferences.setValues("status", "1")

                finishAffinity()
                startActivity(Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra("data", iUser))
            }

            override fun onCancelled(err: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+err.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}