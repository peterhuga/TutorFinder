package jwang.example.tutorfinder.tutor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import jwang.example.tutorfinder.R

class RequestedStudentProfileActivity : AppCompatActivity() , OnClickListener{

    lateinit var buttonAccept:ImageView
    lateinit var buttonReject:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_student_profile)

        buttonAccept = findViewById(R.id.imageViewRequestedStudentAccept)
        buttonReject = findViewById(R.id.imageViewRequestedStudentReject)

        buttonAccept.setOnClickListener(this)
        buttonReject.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.imageViewRequestedStudentAccept -> {
                finish()
            }
            R.id.imageViewRequestedStudentReject -> {
                finish()
            }
        }
    }
}