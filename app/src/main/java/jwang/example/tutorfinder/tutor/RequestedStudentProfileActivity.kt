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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.R
import java.util.Objects

class RequestedStudentProfileActivity : AppCompatActivity() , OnClickListener{

    lateinit var buttonAccept:ImageView
    lateinit var buttonReject:ImageView
    lateinit var requestedStudentsNameTextView: TextView
    lateinit var requestedStudentsAgeTextView: TextView
    lateinit var requestedStudentsGradeTextView: TextView

    lateinit var studentId: Student

    private val database = Firebase.database.reference

    var name: String = "Sampath"
    var age: String = "21"
    var grade: String = "12"
    var email: String = "sampath@email.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_student_profile)

        studentId = intent.getSerializableExtra("student_id") as Student

        initializeFields()
        supportActionBar?.title = "Tutor Portal"

        //var id: String = StudentRequestActivity.STUDENT_ID.toString()

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
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(studentId.email) )
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

        requestedStudentsNameTextView.text = studentId.name
        requestedStudentsAgeTextView.text = studentId.age.toString()
        requestedStudentsGradeTextView.text = studentId.grade.toString()
    }

    private fun showAlert(title: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setPositiveButton("Confirm"){
            //send student id back to parent to delete it
                dialog, which ->
            if (title == "Are you sure to accept this student?") {
                //Log.d("accept", "Accept")

                // update the tutors current students node
                val databaseReferenceAccept: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/current_students")
                val updateStudent = HashMap<String, Any>()
                updateStudent[studentId.id] = ""
                databaseReferenceAccept.updateChildren(updateStudent)

                // update the students accepted tutors node
                val databaseReferenceAcceptForStudent: DatabaseReference = database.child("users/${studentId.id}/tutors_accepted")
                val updateTutor = HashMap<String, Any>()
                updateTutor[TutorScreenActivity.currentUser?.uid.toString()] = ""
                databaseReferenceAcceptForStudent.updateChildren(updateTutor)

                // update the tutors requested students node by removing the students accepted
                val databaseReferenceRemove: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/students_requests/${studentId.id}")
                databaseReferenceRemove.removeValue()

                // update the students requested tutors node by removing the tutor accepted
                val databaseReferenceRemoveTutor: DatabaseReference = database.child("users/${studentId.id}/tutors_requested/${TutorScreenActivity.currentUser?.uid}")
                databaseReferenceRemoveTutor.removeValue()

            } else {
                //Log.d("accept", "Reject")

                // update the tutors requested students node by removing the students rejected
                val databaseReferenceRemove: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/students_requests/${studentId.id}")
                databaseReferenceRemove.removeValue()

                // update the tutors requested tutors node by removing the tutor rejected
                val databaseReferenceRemoveForStudent: DatabaseReference = database.child("users/${studentId.id}/tutors_requested/${TutorScreenActivity.currentUser?.uid}")
                databaseReferenceRemoveForStudent.removeValue()
            }
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