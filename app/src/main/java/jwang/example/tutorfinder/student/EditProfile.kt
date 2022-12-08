package jwang.example.tutorfinder.student

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.R

class EditProfile : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_GET=1
    lateinit var galleryBtn: Button
companion object{
     lateinit var myIdentity:Student
}

    lateinit var cameraBtn: Button
    lateinit var locationForPhotos: Uri
    lateinit var myProfileImage:ImageView


    private val database = Firebase.database.reference

    private var currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    lateinit var myName: EditText
    lateinit var myEmail: EditText
    lateinit var myPhone: EditText
    lateinit var myAddress: EditText
    lateinit var myAge: EditText
    lateinit var myGrade: EditText
    lateinit var myGender: EditText
    lateinit var myCourse: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        myAddress=findViewById(R.id.studentAddressEditText)
        myAge=findViewById(R.id.studentAgeEditText)
        myGrade=findViewById(R.id.studentGradeEditText)
        myGender=findViewById(R.id.studentGenderEditText)
        myCourse=findViewById(R.id.studentCourseEditText)
        myName=findViewById(R.id.studentNameEditText)
        myEmail=findViewById(R.id.studentEmailEditText)
        myPhone=findViewById(R.id.studentPhoneEditText)
        myProfileImage=findViewById(R.id.myProfilePicture)
        myProfileImage.isClickable = true
        cameraBtn=findViewById(R.id.cameraBtn)
        galleryBtn=findViewById(R.id.galleryBtn)
        cameraBtn.isVisible=false
        galleryBtn.isVisible=false

        database.child("users/${currentUserId}").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val displayName = dataSnapshot.child("name").getValue(String::class.java)
                    val displayAge = dataSnapshot.child("age").getValue(String::class.java)
                    val displayAddress = dataSnapshot.child("address").getValue(String::class.java)
                    val displayGrade = dataSnapshot.child("grade").getValue(String::class.java)
                    val displayCourse = dataSnapshot.child("course").getValue(String::class.java)
                    val displayGender = dataSnapshot.child("gender").getValue(String::class.java)
                    val displayEmail = dataSnapshot.child("email").getValue(String::class.java)
                    val displayPhone = dataSnapshot.child("phone").getValue(String::class.java)

                    // TODO: handle the post
                    myName.setText(displayName)
                    myAddress.setText(displayAddress)
                    myPhone.setText(displayPhone)
                    myEmail.setText(displayEmail)
                    myGender.setText(displayGender)
                    myAge.setText(displayAge)
                    myCourse.setText(displayCourse)
                    myGrade.setText(displayGrade)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
    fun onPictureEvent(view: View) {
        when (view.id) {

            R.id.myProfilePicture -> {
                cameraBtn.isVisible = true
               // galleryBtn.isVisible=true
            }
            R.id.cameraBtn -> {

                dispatchTakePictureIntent()
                cameraBtn.isVisible = false
               // galleryBtn.isVisible=false

            }
            R.id.galleryBtn-> {
                selectImage()
                cameraBtn.isVisible=false
               // galleryBtn.isVisible=false
            }
            }
    }
    fun onSaveClicked(view: View) {
        myIdentity = Student(myName.text.toString(),myAge.text.toString(),myAddress.text.toString(),myCourse.text.toString(),myGender.text.toString(),myEmail.text.toString(),
        myGrade.text.toString(),myPhone.text.toString())
        database.child("users/${currentUserId}/name").setValue(myName.text.toString())
        database.child("users/${currentUserId}/age").setValue(myAge.text.toString())
        database.child("users/${currentUserId}/phone").setValue(myPhone.text.toString())
        database.child("users/${currentUserId}/email").setValue(myEmail.text.toString())
        database.child("users/${currentUserId}/address").setValue(myAddress.text.toString())
        database.child("users/${currentUserId}/course").setValue(myCourse.text.toString())
        database.child("users/${currentUserId}/grade").setValue(myGrade.text.toString())
        database.child("users/${currentUserId}/gender").setValue(myGender.text.toString())
        //  myRef.setValue(myProfile)
        //database.goOffline()
        Toast.makeText(this,"Profile updated successfully!", Toast.LENGTH_SHORT).show()

        finish()

        //database.goOffline()


    }
    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        Log.d("gallery","successful")
        // if (intent.resolveActivity(packageManager) != null) {
        startActivityForResult(intent, REQUEST_IMAGE_GET)
        // }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
            myProfileImage.setImageBitmap(thumbnail)
            //locationForPhotos   = data?.data!!
            //myProfileImage.setImageURI(locationForPhotos)
        }
    }
}