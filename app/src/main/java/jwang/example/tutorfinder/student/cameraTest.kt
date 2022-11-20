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
import jwang.example.tutorfinder.R

class cameraTest : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var imageBtn: Button
    lateinit var myPic: ImageView
    lateinit var  locationForPhotos: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_test)
        imageBtn=findViewById(R.id.button)
        myPic=findViewById(R.id.imageView)
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
            myPic.setImageBitmap(thumbnail)
            // Do other work with full size photo saved in locationForPhotos

        }
    }

    fun takePic(view: View) {
        dispatchTakePictureIntent()
    }
}