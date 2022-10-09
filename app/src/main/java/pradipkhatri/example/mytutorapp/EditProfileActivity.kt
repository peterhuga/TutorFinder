package pradipkhatri.example.mytutorapp

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {
    val database=Firebase.database
    val myRef = database.getReference("tutor/tutorDetails")

    lateinit var goBack:Button

    lateinit var tutorName:EditText
    lateinit var tutorAge:EditText
    lateinit var tutorAddress:EditText
    lateinit var tutorGrade:EditText
    lateinit var tutorCourse:EditText
    lateinit var tutorGender:EditText
    lateinit var tutorEmail:EditText
    lateinit var tutorPhone:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        tutorName=findViewById(R.id.tutorNameEditText)
        tutorAge=findViewById(R.id.tutorAgeEditText)
        tutorAddress=findViewById(R.id.tutorAddressEditText)
        tutorGrade=findViewById(R.id.tutorGradeEditText)
        tutorCourse=findViewById(R.id.tutorCourseEditText)
        tutorGender=findViewById(R.id.tutorGenderEditText)
        tutorEmail=findViewById(R.id.tutorEmailEditText)
        tutorPhone=findViewById(R.id.tutorPhoneEditText)

       // goBack=findViewById(R.id.goBackViaEditProfile)



        // My top posts by number of stars
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                   val displayName = dataSnapshot.child("name").getValue(String::class.java)
                    val displayAge = dataSnapshot.child("age").getValue(String::class.java)
                    val displayAddress = dataSnapshot.child("address").getValue(String::class.java)
                    val displayGrade = dataSnapshot.child("grade").getValue(String::class.java)
                    val displayCourse = dataSnapshot.child("course").getValue(String::class.java)
                    val displayGender = dataSnapshot.child("gender").getValue(String::class.java)
                    val displayEmail = dataSnapshot.child("email").getValue(String::class.java)
                    val displayPhone = dataSnapshot.child("phone").getValue(String::class.java)

                    // TODO: handle the post
                    tutorName.setText(displayName)
                    tutorAddress.setText(displayAddress)
                    tutorPhone.setText(displayPhone)
                    tutorEmail.setText(displayEmail)
                    tutorGender.setText(displayGender)
                    tutorAge.setText(displayAge)
                    tutorCourse.setText(displayCourse)
                    tutorGrade.setText(displayGrade)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        database.goOffline()

    }

    fun onSaveClicked(view: View) {
      //myRef.setValue("yes")

        val tutor =  Tutor(tutorName.text.toString(), tutorAge.text.toString(), tutorGender.text.toString(), tutorAddress.text.toString(), tutorPhone.text.toString(), tutorGrade.text.toString(), tutorCourse.text.toString(), tutorEmail.text.toString())
       Log.d("mytag", "databaseCheck")

        myRef.setValue(tutor)
        database.goOffline()


    }

     fun onClickBack(view: View) {
                finish()


    }
}