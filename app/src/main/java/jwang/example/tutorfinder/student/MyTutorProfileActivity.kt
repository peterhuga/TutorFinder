package jwang.example.tutorfinder.student

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.Student

class MyTutorProfileActivity : AppCompatActivity() {
    lateinit var myTutorName: TextView
    lateinit var myTutorAge: TextView
    lateinit var myTutorEmail: TextView
    lateinit var myTutorExperience: TextView
    lateinit var myTutorDegree: TextView
    lateinit var myTutorPhone: TextView
    lateinit var myTutorAddress: TextView
    lateinit var myTutorGrades: TextView
    lateinit var myTutorPhoneRedirect: ImageButton
    lateinit var myTutorSmsRedirect: ImageButton
    lateinit var myTutorEmailRedirect: ImageButton

    lateinit var tutorId: Tutor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tutor_profile)

        supportActionBar?.title = "Student Portal"

        tutorId = intent.getSerializableExtra("tutor_id") as Tutor

        initializeFields()

    }

    private fun initializeFields() {

        myTutorName = findViewById(R.id.textViewTutorName)
        myTutorAge = findViewById(R.id.textViewTutorAge)
        myTutorEmail = findViewById(R.id.textViewTutorEmail)
        myTutorExperience = findViewById(R.id.textViewTutorExperience)
        myTutorDegree = findViewById(R.id.textViewTutorEducation)
        myTutorPhone = findViewById(R.id.textViewTutorPhoneNumber)
        myTutorAddress = findViewById(R.id.textViewTutorAddress)
        myTutorGrades = findViewById(R.id.textViewTutorGrades)

        myTutorPhoneRedirect = findViewById(R.id.imageButtonPhone)
        myTutorSmsRedirect = findViewById(R.id.imageButtonSMS)
        myTutorEmailRedirect = findViewById(R.id.imageButtonEmail)

        myTutorName.text = tutorId.name
        myTutorAge.text = tutorId.age
        myTutorEmail.text = tutorId.email
        myTutorExperience.text = tutorId.experience + " years"
        myTutorDegree.text = tutorId.education
        myTutorPhone.text = tutorId.phone
        myTutorAddress.text = tutorId.address
        myTutorGrades.text = tutorId.grades
    }

    fun onButtonClick(view: View) {
        var phoneNumber = tutorId.phone
        var emailAddresses = tutorId.email
        print(phoneNumber)
        when (view.id) {
            R.id.imageButtonPhone -> {
                //Log.d("phonenumber",phoneNumber)

                val intent = Intent(Intent.ACTION_DIAL).apply {

                    data = Uri.parse("tel:$phoneNumber")
                }
                   if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                }

            }
            R.id.imageButtonSMS -> {
                val smsIntent = Intent(Intent.ACTION_SENDTO)
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT)
                smsIntent.type = "vnd.android-dir/mms-sms"
                smsIntent.data = Uri.parse("sms:$phoneNumber")
                startActivity(smsIntent)

            }
            R.id.imageButtonEmail -> {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:") // only email apps should handle this
                    putExtra(Intent.EXTRA_EMAIL, emailAddresses)
                   // putExtra(Intent.EXTRA_SUBJECT, "hello")
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }


            }
        }
    }

}
