package jwang.example.tutorfinder.tutor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewDebug.IntToString
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import jwang.example.tutorfinder.R

class RequestedStudentProfileActivity : AppCompatActivity() , OnClickListener{

    lateinit var buttonAccept:ImageView
    lateinit var buttonReject:ImageView
    lateinit var requestedStudentsNameTextView: TextView
    lateinit var requestedStudentsAgeTextView: TextView
    lateinit var requestedStudentsGradeTextView: TextView

    var name: String = "Sampath"
    var age: String = "21"
    var grade: String = "12"
    var email: String = "sampath@email.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_student_profile)

        initializeFields()
        supportActionBar?.title = "Tutor Portal"

        var id: String = StudentRequestActivity.STUDENT_ID.toString()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_student_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_bar -> {
                Log.d("Student", "action bar clicked")

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    //type = "text/plain"
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email) )
                    putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                }
                val chooser = Intent.createChooser(intent, "Choose the application")
                startActivity(chooser)

                return true
            }
        }
            return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.imageViewRequestedStudentAccept -> {
                showAlert("Are you sure to accept this student?")
            }
            R.id.imageViewRequestedStudentReject -> {
                showAlert("Are you sure to reject this student?")
            }
        }
    }

    private fun initializeFields() {
        buttonAccept = findViewById(R.id.imageViewRequestedStudentAccept)
        buttonReject = findViewById(R.id.imageViewRequestedStudentReject)

        requestedStudentsNameTextView = findViewById(R.id.textViewRequestedStudentName)
        requestedStudentsAgeTextView = findViewById(R.id.textViewRequestedStudentAge)
        requestedStudentsGradeTextView = findViewById(R.id.textViewRequestedStudentGrade)


        buttonAccept.setOnClickListener(this)
        buttonReject.setOnClickListener(this)

        requestedStudentsNameTextView.text = name
        requestedStudentsAgeTextView.text = age
        requestedStudentsGradeTextView.text = grade
    }

    private fun showAlert(title: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setPositiveButton("Confirm"){
            //send student id back to parent to delete it
                dialog, which ->
            StudentRequestActivity.STUDENT_ID_DELETE = StudentRequestActivity.STUDENT_ID
            StudentRequestActivity.POSITION_DELETE = StudentRequestActivity.POSITION
            finish()
        }
        builder.setNegativeButton("Cancel"){
                dialog, which -> dialog.cancel()
        }
        builder.show()
    }
}