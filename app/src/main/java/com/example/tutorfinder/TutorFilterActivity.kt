package com.example.tutorfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TutorFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_filter)
    }

    fun onQueryDoneClicked(view: View) {
        finish()
    }
}