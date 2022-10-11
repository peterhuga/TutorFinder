package com.example.tutorfinder

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

class EditStudentProfileActivity : AppCompatActivity() {

    val database=Firebase.database
    val myRef = database.getReference("student/studentDetails")

    lateinit var goBack: Button

    lateinit var studentName: EditText
    lateinit var studentAge: EditText
    lateinit var studentAddress: EditText
    lateinit var studentCourse: EditText
    lateinit var studentGender: EditText
    lateinit var studentEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student_profile)
        studentName=findViewById(R.id.studenteditname)
        studentAge=findViewById(R.id.studenteditage)
        studentAddress=findViewById(R.id.studentEditAddress)
        studentCourse=findViewById(R.id.studentEditCourse)
        studentGender=findViewById(R.id.studentEditGender)
        studentEmail=findViewById(R.id.studentEditEmail)

    // goBack=findViewById(R.id.goBackViaEditProfile)



    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (postSnapshot in dataSnapshot.children) {
                val displayName = dataSnapshot.child("name").getValue(String::class.java)
                val displayAge = dataSnapshot.child("age").getValue(String::class.java)
                val displayAddress = dataSnapshot.child("address").getValue(String::class.java)
                //val displayGrade = dataSnapshot.child("grade").getValue(String::class.java)
                val displayCourse = dataSnapshot.child("course").getValue(String::class.java)
                val displayGender = dataSnapshot.child("gender").getValue(String::class.java)
                val displayEmail = dataSnapshot.child("email").getValue(String::class.java)
                //val displayPhone = dataSnapshot.child("phone").getValue(String::class.java)

                // TODO: handle the post
                studentName.setText(displayName)
                studentAddress.setText(displayAddress)
                studentEmail.setText(displayEmail)
                studentGender.setText(displayGender)
                studentAge.setText(displayAge)
                studentCourse.setText(displayCourse)

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w("DatabaseError", "loadPost:onCancelled", databaseError.toException())
            // ...
        }
    })
    //database.goOffline()


    }
    fun onSaveClicked(view: View) {
        //myRef.setValue("yes")

        val student =  Student(studentName.text.toString(), studentAge.text.toString(), studentGender.text.toString(), studentAddress.text.toString(),  studentCourse.text.toString(), studentEmail.text.toString())
        Log.d("mytag", "databaseCheck")

        myRef.setValue(student)
        //database.goOffline()


    }

    fun onClickBack(view: View) {
        finish()


    }
}