package jwang.example.tutorfinder.student

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.R

class EditStudentProfile : AppCompatActivity() {
   // val database = Firebase.database
    //val myRef = database.getReference("student/studentDetails")
   lateinit var  myUri: Uri
    val REQUEST_IMAGE_GET = 1
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var goBack: Button
    lateinit var myProfilePicture:ImageView
    lateinit var cameraBtn:Button
    lateinit var galleryBtn:Button

    lateinit var studentName: EditText
    lateinit var studentAge: EditText
    lateinit var studentAddress: EditText
    lateinit var studentGrade: EditText
    lateinit var studentCourse: EditText
    lateinit var studentGender: EditText
    lateinit var studentEmail: EditText
    lateinit var studentPhone: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student_profile)
        studentName = findViewById(R.id.tutorNameEditText)
        studentAge = findViewById(R.id.tutorAgeEditText)
        studentAddress = findViewById(R.id.tutorAddressEditText)
        studentGrade = findViewById(R.id.tutorGradeEditText)
        studentCourse = findViewById(R.id.tutorCourseEditText)
        studentGender = findViewById(R.id.tutorGenderEditText)
        studentEmail = findViewById(R.id.tutorEmailEditText)
        studentPhone = findViewById(R.id.tutorPhoneEditText)
        myProfilePicture = findViewById(R.id.myProfilePicture)
        myProfilePicture.isClickable = true
        cameraBtn=findViewById(R.id.cameraBtn)
        galleryBtn=findViewById(R.id.galleryBtn)
        cameraBtn.isVisible=false
        galleryBtn.isVisible=false

    //        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (postSnapshot in dataSnapshot.children) {
//                    val displayName = dataSnapshot.child("name").getValue(String::class.java)
//                    val displayAge = dataSnapshot.child("age").getValue(String::class.java)
//                    val displayAddress = dataSnapshot.child("address").getValue(String::class.java)
//                    val displayGrade = dataSnapshot.child("grade").getValue(String::class.java)
//                    val displayCourse = dataSnapshot.child("course").getValue(String::class.java)
//                    val displayGender = dataSnapshot.child("gender").getValue(String::class.java)
//                    val displayEmail = dataSnapshot.child("email").getValue(String::class.java)
//                    val displayPhone = dataSnapshot.child("phone").getValue(String::class.java)
//
//                    // TODO: handle the post
//                    studentName.setText(displayName)
//                    studentAge.setText(displayAge)
//                    studentAddress.setText(displayAddress)
//                    studentGrade.setText(displayGrade)
//                    studentCourse.setText(displayCourse)
//                    studentGender.setText(displayGender)
//                    studentEmail.setText(displayEmail)
//                    studentPhone.setText(displayPhone)
//
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
//                // ...
//            }
//        })
    }

//    fun selectImage() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "image/*"
//        }
//        Log.d("gallery","successful")
//        // if (intent.resolveActivity(packageManager) != null) {
//        startActivityForResult(intent, REQUEST_IMAGE_GET)
//        // }
//    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
           // myPic.setImageBitmap(thumbnail)
            // Do other work with full size photo saved in locationForPhotos

        }
    } // myUri = data.data!!
            //findViewById<ImageView>(R.id.imageView).setImageBitmap(thumbnail)

            //findViewById<ImageView>(R.id.imageView).setImageURI(myUri)


            // Do work with photo saved at fullPhotoUri





    fun onSaveClicked(view: View) {

         //val myProfile =  Student(studentName.text.toString(), studentAge.text.toString(), studentAddress.text.toString(), studentCourse.text.toString(), studentGender.text.toString(), studentEmail.text.toString(), studentGrade.text.toString(), studentPhone.text.toString())
        Log.d("mytag", "databaseCheck")
      //  myRef.setValue(myProfile)
        //database.goOffline()

        finish()

        //database.goOffline()


    }

    fun onPictureEvent(view: View) {
        when(view.id){
            R.id.myProfilePicture->{
                cameraBtn.isVisible=true
                galleryBtn.isVisible=true
            }
            R.id.cameraBtn->{
                dispatchTakePictureIntent()
            }
            R.id.galleryBtn->{
               // selectImage()
            }
        }
    }


}