package jwang.example.tutorfinder.tutor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import jwang.example.tutorfinder.R


class MyStudentProfileActivity : AppCompatActivity() {

    var studentId = 0
    lateinit var tvGrade: TextView
    lateinit var tvCourse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_student_profile)
        studentId = intent.getIntExtra(TutorScreenActivity.STUDENT_ID, 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Student Profile"
        tvCourse = findViewById(R.id.textViewCourse)
        tvGrade = findViewById(R.id.textViewGrade)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_student_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_bar -> {
                Log.d("Student", "action bar clicked")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alert")
                builder.setTitle("Are you sure to delete this student?")
                builder.setCancelable(false)
                builder.setPositiveButton("Confirm"){
                        dialog, which ->
                            val intent = Intent(this, TutorScreenActivity::class.java)
                            Log.d("Student", "RcvedID, $studentId")
                            intent.putExtra(TutorScreenActivity.STUDENT_ID, studentId)
                            finish()
                    }
                builder.setNegativeButton("Cancel"){
                        dialog, which -> dialog.cancel()
                }
                builder.show()
                return true}
        }
        return super.onOptionsItemSelected(item)
    }

    fun onButtonClick(view: View) {
        when(view.id){
            R.id.imageButtonGrade -> setDialog("Grade")
            R.id.imageButtonCourse -> setDialog("Course")
        }
    }

    private fun setDialog(title:String) {
        val editTextDialog = EditText(this)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setView(editTextDialog)
        builder.setCancelable(false)
        builder.setPositiveButton("SAVE"){
            dialog, which ->
            run {
                Log.d("Student", "save: $title")
                if (title == "Grade") {
                    tvGrade.text = editTextDialog.text.toString()
                }
                if (title == "Course") {
                    tvCourse.text = editTextDialog.text.toString()
                }
            }
        }
        builder.setNegativeButton("CANCEL"){
                dialog, which ->
            run {
                Log.d("Student",which.toString())
                dialog.cancel()
            }

        }
        builder.show()
    }
}