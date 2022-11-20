package jwang.example.tutorfinder.student

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import jwang.example.tutorfinder.R

class EditProfileStudent : AppCompatActivity() {
    lateinit var  myUri: Uri
    val REQUEST_IMAGE_GET = 1
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var myProfilePicture: ImageView
    lateinit var cameraBtn: Button
    lateinit var galleryBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_student)
        myProfilePicture = findViewById(R.id.myProfilePicture)
        myProfilePicture.isClickable = true
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
            myProfilePicture.setImageBitmap(thumbnail)
            // Do other work with full size photo saved in locationForPhotos

        }
    }

    fun onPictureEvent(view: View) {
        when(view.id){
            R.id.myProfilePicture->{
                cameraBtn.isVisible=true

            }
            R.id.cameraBtn->{
                dispatchTakePictureIntent()
            }
        }
    }
}