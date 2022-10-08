package jwang.example.tutorfinder.tutor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import jwang.example.tutorfinder.R



class EditProfileActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }

    fun onSAveButtonClick(view: View) {
        Toast.makeText(this,"Profile Saved!", Toast.LENGTH_LONG).show()
        finish()

    }
}