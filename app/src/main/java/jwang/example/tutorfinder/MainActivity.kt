package jwang.example.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.student.StudentDashboard
import jwang.example.tutorfinder.tutor.TutorScreenActivity


class MainActivity : AppCompatActivity() {

    private val SPLASH_SCREEN = 2000L

    //Variables
    lateinit var topAnim: Animation
    lateinit var bottomAnim: Animation
    lateinit var logo: TextView
    lateinit var slogan: TextView
    lateinit var image: ImageView
    private val database = Firebase.database.reference

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

        Handler().postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            val uId = currentUser?.uid
            if ( currentUser != null) {
                database.child("users").orderByKey().equalTo(currentUser.uid).addValueEventListener(object:
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){

                            //Get object from snapshot. Here is only one object in the children array.

                            var unknownUser: UnknownUser? = null
                            for (i in snapshot.children){
                                unknownUser = i.getValue(UnknownUser::class.java)
                            }
                            when(unknownUser?.role){
                                "student" -> {startActivity(Intent(applicationContext, StudentDashboard::class.java))
                                    finish()}
                                "tutor" -> {startActivity(Intent(applicationContext, TutorScreenActivity::class.java))
                                    finish()}
                            }
                        } else{
                            Toast.makeText(applicationContext, "Data is not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            } else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_SCREEN)
    }
}