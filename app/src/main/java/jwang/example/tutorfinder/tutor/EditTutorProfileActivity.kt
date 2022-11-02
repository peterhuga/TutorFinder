package jwang.example.tutorfinder.tutor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import jwang.example.tutorfinder.R

class EditTutorProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittutor_profile)
    }

    fun onSAveButtonClick(view: View) {
        finish()
    }
}