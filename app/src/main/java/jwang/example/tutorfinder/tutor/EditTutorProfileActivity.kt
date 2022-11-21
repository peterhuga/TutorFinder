package jwang.example.tutorfinder.tutor

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.LoginActivity
import jwang.example.tutorfinder.R

class EditTutorProfileActivity : AppCompatActivity() {

    lateinit var tutorsNameTextView: TextView
    lateinit var tutorsEmailTextView: TextView
    lateinit var tutorsPhoneTextView: TextView
    lateinit var tutorsAddressTextView: TextView
    lateinit var tutorsEducationTextView: TextView
    lateinit var tutorsAgeTextView: TextView
    lateinit var tutorsExperienceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittutor_profile)

        supportActionBar?.title = "Tutor Portal"

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

               if (tutorsNameTextView.text.isEmpty() || tutorsAgeTextView.text.isEmpty() || tutorsEmailTextView.text.isEmpty() || tutorsPhoneTextView.text.isEmpty()
                   || tutorsAddressTextView.text.isEmpty() || tutorsEducationTextView.text.isEmpty() || tutorsExperienceTextView.text.isEmpty()) {
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
        Toast.makeText(this,R.string.logged_out,Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,LoginActivity::class.java))
        //finishActivity(1)
        finish()
    }

    private fun onSAveButtonClick() {

        TutorScreenActivity.nameTutor = tutorsNameTextView.text.toString()
        TutorScreenActivity.emailTutor = tutorsEmailTextView.text.toString()
        TutorScreenActivity.phoneTutor = tutorsPhoneTextView.text.toString()
        TutorScreenActivity.addressTutor = tutorsAddressTextView.text.toString()
        TutorScreenActivity.educationTutor = tutorsEducationTextView.text.toString()
        TutorScreenActivity.ageTutor = tutorsAgeTextView.text.toString()
        TutorScreenActivity.experienceTutor = tutorsExperienceTextView.text.toString()

        Toast.makeText(this,"Profile updated successfully!", Toast.LENGTH_SHORT).show()

        finish()
    }

    private fun initializeFields() {
        tutorsNameTextView = findViewById(R.id.editTextTutorName)
        tutorsEmailTextView = findViewById(R.id.editTextTutorEmail)
        tutorsPhoneTextView = findViewById(R.id.editTextTutorPhone)
        tutorsAddressTextView = findViewById(R.id.editTextTutorAddress)
        tutorsEducationTextView = findViewById(R.id.editTextTutorEducation)
        tutorsAgeTextView = findViewById(R.id.editTextTutorAge)
        tutorsExperienceTextView = findViewById(R.id.editTextTutorExperience)


        tutorsNameTextView.text = TutorScreenActivity.nameTutor
        tutorsEmailTextView.text = TutorScreenActivity.emailTutor
        tutorsPhoneTextView.text = TutorScreenActivity.phoneTutor
        tutorsAddressTextView.text = TutorScreenActivity.addressTutor
        tutorsEducationTextView.text = TutorScreenActivity.educationTutor
        tutorsAgeTextView.text = TutorScreenActivity.ageTutor
        tutorsExperienceTextView.text = TutorScreenActivity.experienceTutor


    }
}