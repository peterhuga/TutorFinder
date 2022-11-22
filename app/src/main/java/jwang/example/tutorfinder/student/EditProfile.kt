package jwang.example.tutorfinder.student

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import jwang.example.tutorfinder.R

class EditProfile : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_GET=1
    lateinit var galleryBtn: Button

    lateinit var cameraBtn: Button
    lateinit var locationForPhotos: Uri
    lateinit var myProfileImage:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        myProfileImage=findViewById(R.id.myProfilePicture)
        myProfileImage.isClickable = true
        cameraBtn=findViewById(R.id.cameraBtn)
        galleryBtn=findViewById(R.id.galleryBtn)
        cameraBtn.isVisible=false
        galleryBtn.isVisible=false
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

        //val myProfile =  Student(studentName.text.toString(), studentAge.text.toString(), studentAddress.text.toString(), studentCourse.text.toString(), studentGender.text.toString(), studentEmail.text.toString(), studentGrade.text.toString(), studentPhone.text.toString())
        Log.d("mytag", "databaseCheck")
        //  myRef.setValue(myProfile)
        //database.goOffline()

        //finish()

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