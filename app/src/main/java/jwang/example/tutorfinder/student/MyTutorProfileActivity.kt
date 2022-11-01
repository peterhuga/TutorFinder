package jwang.example.tutorfinder.student

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import jwang.example.tutorfinder.R

class MyTutorProfileActivity : AppCompatActivity() {
    lateinit var myTutorname: TextView
    lateinit var myTutorage: TextView
    lateinit var myTutoremail: TextView
    lateinit var myTutorexperience: TextView
    lateinit var myTutordegree: TextView
    lateinit var myTutorphone: TextView
    lateinit var myTutoraddress: TextView
    lateinit var myTutorgender: TextView
    lateinit var myTutorgrade: TextView
    lateinit var myTutorphoneredirect: ImageButton
    lateinit var myTutorsmsredirect: ImageButton
    lateinit var myTutoremailredirect: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tutor_profile)
        myTutorname = findViewById(R.id.myTutorNameTextView)
        myTutorage = findViewById(R.id.myTutorAgeTextView)
        myTutoremail = findViewById(R.id.myTutorEmailTextView)
        myTutorexperience = findViewById(R.id.myTutorExperienceTextView)
        myTutordegree = findViewById(R.id.myTutordegreeTextView)
        myTutorphone = findViewById(R.id.myTutordegreeTextView)
        myTutoraddress = findViewById(R.id.myTutorAddressTextView)
        myTutorgender = findViewById(R.id.myTutorGenderTextView)
        myTutorgrade = findViewById(R.id.myTutorGradeTextView)
        myTutorphone = findViewById(R.id.myTutorPhoneTextView)
        myTutorphoneredirect = findViewById(R.id.phoneImageView)
        myTutorsmsredirect = findViewById(R.id.smsImageView)
        myTutoremailredirect = findViewById(R.id.emailImageView)
    }

    fun onRedirectBtnclick(view: View) {
        var phoneNumber = StudentDashboard.tutors[1].phone
        var addresses = StudentDashboard.tutors[1].email
        print(phoneNumber)
        when (view.id) {
            R.id.phoneImageView -> {
                //Log.d("phonenumber",phoneNumber)

                val intent = Intent(Intent.ACTION_DIAL).apply {

                    data = Uri.parse("tel:$phoneNumber")
                }
                   if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                }

            }
            R.id.smsImageView -> {
                val smsIntent = Intent(Intent.ACTION_SENDTO)
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT)
                smsIntent.type = "vnd.android-dir/mms-sms"
                smsIntent.data = Uri.parse("sms:$phoneNumber")
                startActivity(smsIntent)

            }
            R.id.emailImageView -> {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:") // only email apps should handle this
                    putExtra(Intent.EXTRA_EMAIL, addresses)
                   // putExtra(Intent.EXTRA_SUBJECT, "hello")
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }


            }
        }
    }

}
