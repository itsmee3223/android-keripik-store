package com.silvinda.keripikstore.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.silvinda.keripikstore.R
import com.silvinda.keripikstore.databinding.ActivitySignPhotoScreenBinding
import com.silvinda.keripikstore.home.HomeScreenActivity
import com.silvinda.keripikstore.sign.User
import com.silvinda.keripikstore.utils.Preferences
import java.util.*

class SignUpPhotoscreenActivity : AppCompatActivity(), PermissionListener {
    private lateinit var binding: ActivitySignPhotoScreenBinding

    private var statusAdd:Boolean = false
    private lateinit var filePath: Uri

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences

    var user: User? = null

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignPhotoScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        user = intent.getParcelableExtra("data") as User?


        mFirebaseInstance = FirebaseDatabase.getInstance("https://keripik-store-cbf2a-default-rtdb.asia-southeast1.firebasedatabase.app")
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        binding.tvWelcome.text = "Selamat Datang\n"+user?.nama

        binding.ivAdd.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                binding.btnSave.visibility = View.INVISIBLE
                binding.ivAdd.setImageResource(R.drawable.ic_btn_upload)
                binding.ivProfile.setImageResource(R.drawable.user_pic)

            } else {

                ImagePicker.with(this)
                    .cameraOnly()	//User can only capture image using Camera
                    .start()
            }
        }

        binding.ivHome.setOnClickListener {

            finishAffinity()

            val intent = Intent(this@SignUpPhotoscreenActivity,
                HomeScreenActivity::class.java)
            startActivity(intent)
        }

        binding.btnSave.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this@SignUpPhotoscreenActivity, "Uploaded", Toast.LENGTH_SHORT).show()
                    ref.downloadUrl.addOnSuccessListener {
                        saveToFirebase(it.toString())
                    }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@SignUpPhotoscreenActivity, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }

        }
    }

    private fun saveToFirebase(url: String) {

        mFirebaseDatabase.child(user?.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                user?.url = url
                mFirebaseDatabase.child(user?.username!!).setValue(user)

                preferences.setValues("nama", user?.nama.toString())
                preferences.setValues("user", user?.username.toString())
                preferences.setValues("saldo", "")
                preferences.setValues("url", "")
                preferences.setValues("email", user?.email.toString())
                preferences.setValues("status", "1")
                preferences.setValues("url", url)

                finishAffinity()
                val intent = Intent(this@SignUpPhotoscreenActivity,
                    HomeScreenActivity::class.java)
                startActivity(intent)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpPhotoscreenActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })


    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {

        ImagePicker.with(this)
            .cameraOnly()	//User can only capture image using Camera
            .start()

    }

    override fun onPermissionRationaleShouldBeShown(
        permission: com.karumi.dexter.listener.PermissionRequest?,
        token: PermissionToken?
    ) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(this, "Anda tidak bisa menambahkan photo profile", Toast.LENGTH_LONG ).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? Klik tombol Upload Nanti aja", Toast.LENGTH_LONG ).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//
//            filePath = data.getData()!!
//
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform())
//                .into(iv_profile)
//
//            btn_save.visibility = View.VISIBLE
//            iv_add.setImageResource(R.drawable.ic_btn_delete)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                statusAdd = true
                filePath = data?.data!!

                Glide.with(this)
                    .load(filePath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.ivProfile)

                binding.btnSave.visibility = View.VISIBLE
                binding.ivAdd.setImageResource(R.drawable.ic_btn_delete)

            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}