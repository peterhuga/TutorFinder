package jwang.example.tutorfinder.tutor

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
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

    lateinit var studentId: Student
   //var position = -1
    lateinit var tvGrade: TextView
    lateinit var tvCourse: TextView
    lateinit var tvPhoneNumber: TextView
    lateinit var tvEmail:TextView
    lateinit var tvName: TextView
    lateinit var tvAge: TextView
    lateinit var tvAddress:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_student_profile)

        studentId = intent.getSerializableExtra(TutorScreenActivity.STUDENT_ID) as Student
        //position = intent.getIntExtra("position", -1)
        Log.d("Student", "RcvedID, $${studentId.id}")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Student Profile"

        tvGrade = findViewById(R.id.textViewGrade)
        tvPhoneNumber = findViewById(R.id.textViewPhoneNumber)
        tvEmail = findViewById(R.id.textViewStudentEmail)
        tvName = findViewById(R.id.textViewStudentName)
        tvAge = findViewById(R.id.textViewStudentAge)
        tvAddress = findViewById(R.id.textViewStudentAddress)
        tvGrade.text = studentId.grade.toString()
        tvPhoneNumber.text = studentId.phone.toString()
        tvEmail.text = studentId.email
        tvName.text = studentId.name
        tvAddress.text = studentId.address
        tvAge.text = studentId.age.toString()


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

                builder.setTitle("Are you sure to delete this student?")
                builder.setCancelable(false)
                builder.setPositiveButton("Confirm"){
                    //send student id back to parent to delete it
                        dialog, which ->
                            val intent = Intent()

                            intent.putExtra(TutorScreenActivity.STUDENT_ID, studentId.id)
                            //intent.putExtra("position", position)
                            setResult(RESULT_OK, intent)
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
//            R.id.imageButtonGrade -> setDialog("Grade")
//            R.id.imageButtonCourse -> setDialog("Course")
            R.id.imageButtonSMS -> {
                Log.d("Student", "sms clicked")
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:")
                    putExtra("address", tvPhoneNumber.text)

                    }
                val chooser = Intent.createChooser(intent, "Choose the application")
                startActivity(chooser)
            }
            R.id.imageButtonEmail -> {

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(tvEmail.text.toString()) )
                    putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                }
                val chooser = Intent.createChooser(intent, "Choose the application")
                startActivity(chooser)
            }
            R.id.imageButtonPhone -> {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${tvPhoneNumber.text}")
                }
                val chooser = Intent.createChooser(intent, "Choose the application")
                startActivity(chooser)
            }

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