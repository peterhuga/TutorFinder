package com.example.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
    }

    fun onFilterTutorBtnPressed(view: View) {
        startActivity(Intent(this,FilterTutorActivity::class.java))
    }
    fun onEditProfileclicked(view: View) {
        startActivity((Intent(this, EditStudentProfileActivity::class.java)))

    }
}