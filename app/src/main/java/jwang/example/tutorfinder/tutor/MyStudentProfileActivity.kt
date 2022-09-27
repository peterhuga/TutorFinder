package jwang.example.tutorfinder.tutor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import jwang.example.tutorfinder.R


class MyStudentProfileActivity : AppCompatActivity() {

    var studentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_student_profile)

        studentId = intent.getIntExtra(TutorScreenActivity.STUDENT_ID, 0)

    }
}