package jwang.example.tutorfinder.tutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import jwang.example.tutorfinder.R

class TutorScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_screen)
    }

    fun onButtonClick(view: View) {
        when(view.id) {
            R.id.buttonEditProfile -> startActivity(Intent(this, EditProfileActivity::class.java))
            R.id.buttonShowStudentRequest -> startActivity(Intent(this, StudentRequestActivity::class.java))
        }
    }
}