package jwang.example.tutorfinder.tutor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import jwang.example.tutorfinder.R

class EditTutorProfileActivity : AppCompatActivity() {

    lateinit var tutorsNameTextView: TextView
    lateinit var tutorsEmailTextView: TextView
    lateinit var tutorsPhoneTextView: TextView
    lateinit var tutorsAddressTextView: TextView
    lateinit var tutorsEducationTextView: TextView
    lateinit var tutorsAgeTextView: TextView
    lateinit var tutorsExperienceTextView: TextView

    var name: String = "Sampath"
    var age: String = "28"
    var address: String = "79 Main st."
    var phone: String = "Sampath"
    var education: String = "Graduate Level"
    var email: String = "sampath@email.com"
    var experience: String = "5 years"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittutor_profile)

        supportActionBar?.title = "Tutor Portal"

        initializeFields()
    }

    fun onSAveButtonClick(view: View) {

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

        tutorsNameTextView.text = name
        tutorsEmailTextView.text = email
        tutorsPhoneTextView.text = phone
        tutorsAddressTextView.text = address
        tutorsEducationTextView.text = education
        tutorsAgeTextView.text = age
        tutorsExperienceTextView.text = experience

    }
}