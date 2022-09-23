package jwang.example.tutorfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //Variables
    lateinit var topAnim: Animation
    lateinit var bottomAnim: Animation
    lateinit var logo: TextView
    lateinit var slogan: TextView
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        //Animations
        topAnim = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_slide_in_top)
        bottomAnim = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_slide_in_bottom)
        image = findViewById(R.id.imageView)
        logo = findViewById(R.id.textViewAppName)
        slogan = findViewById(R.id.textViewSubTitle)

        image.animation = topAnim
        logo.animation = bottomAnim
        slogan.animation = bottomAnim

    }
}