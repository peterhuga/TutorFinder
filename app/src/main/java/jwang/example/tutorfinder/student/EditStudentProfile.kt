package jwang.example.tutorfinder.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.LoginActivity
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class EditStudentProfile : AppCompatActivity() {

    lateinit var studentsNameTextView: TextView
    lateinit var studentsEmailTextView: TextView
    lateinit var studentsPhoneTextView: TextView
    lateinit var studentsAddressTextView: TextView
    lateinit var studentsGradeTextView: TextView
    lateinit var studentsAgeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student_profile)

        supportActionBar?.title = "Student Portal"

        initializeFields()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_bar -> {
                Log.d("Student", "action bar clicked")

                if (studentsNameTextView.text.isEmpty() || studentsEmailTextView.text.isEmpty() || studentsPhoneTextView.text.isEmpty()
                    || studentsAddressTextView.text.isEmpty() || studentsGradeTextView.text.isEmpty() || studentsAgeTextView.text.isEmpty()) {
                    Toast.makeText(this,"Fill the every field!", Toast.LENGTH_SHORT).show()
                } else {
                    onSAveButtonClick()
                }

                return true
            }

            R.id.log_out -> {
                logOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        Firebase.auth.signOut()
        Toast.makeText(this,R.string.logged_out, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun onSAveButtonClick() {

        // ToDo: Save the students profile on firebase database

        Toast.makeText(this,"Profile updated successfully!", Toast.LENGTH_SHORT).show()

        finish()
    }

    private fun initializeFields() {
        studentsNameTextView = findViewById(R.id.editTextStudentName)
        studentsEmailTextView = findViewById(R.id.editTextStudentEmail)
        studentsPhoneTextView = findViewById(R.id.editTextStudentPhone)
        studentsAddressTextView = findViewById(R.id.editTextStudentAddress)
        studentsGradeTextView = findViewById(R.id.editTextStudentGrade)
        studentsAgeTextView = findViewById(R.id.editTextStudentAge)
    }

}