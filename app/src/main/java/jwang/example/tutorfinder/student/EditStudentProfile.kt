package jwang.example.tutorfinder.student

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.R

class EditStudentProfile : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("student/studentDetails")

    lateinit var goBack: Button

    lateinit var studentName: EditText
    lateinit var studentAge: EditText
    lateinit var studentAddress: EditText
    lateinit var studentGrade: EditText
    lateinit var studentCourse: EditText
    lateinit var studentGender: EditText
    lateinit var studentEmail: EditText
    lateinit var studentPhone: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student_profile)
        studentName = findViewById(R.id.tutorNameEditText)
        studentAge = findViewById(R.id.tutorAgeEditText)
        studentAddress = findViewById(R.id.tutorAddressEditText)
        studentGrade = findViewById(R.id.tutorGradeEditText)
        studentCourse = findViewById(R.id.tutorCourseEditText)
        studentGender = findViewById(R.id.tutorGenderEditText)
        studentEmail = findViewById(R.id.tutorEmailEditText)
        studentPhone = findViewById(R.id.tutorPhoneEditText)

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
                    studentName.setText(displayName)
                    studentAge.setText(displayAge)
                    studentAddress.setText(displayAddress)
                    studentGrade.setText(displayGrade)
                    studentCourse.setText(displayCourse)
                    studentGender.setText(displayGender)
                    studentEmail.setText(displayEmail)
                    studentPhone.setText(displayPhone)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
    fun onSaveClicked(view: View) {

         val myProfile =  Student(studentName.text.toString(), studentAge.text.toString(), studentAddress.text.toString(), studentCourse.text.toString(), studentGender.text.toString(), studentEmail.text.toString(), studentGrade.text.toString(), studentPhone.text.toString())
        Log.d("mytag", "databaseCheck")
        myRef.setValue(myProfile)

        finish()

        //database.goOffline()


    }



}