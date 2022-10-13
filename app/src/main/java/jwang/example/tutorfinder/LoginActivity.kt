package jwang.example.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class LoginActivity : AppCompatActivity() {
    lateinit var fir:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.buttonToTutorScreen).setOnClickListener{
            startActivity(Intent(this, TutorScreenActivity::class.java))
        }
    }
}