package com.example.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.tutorfinder.R

class LoginActivity : AppCompatActivity() {
    lateinit var loginBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn = findViewById(R.id.loginToApp)

    }

    fun onLoginClicked(view: View) {
        startActivity(Intent(this, StudentActivity::class.java))
        finish()

    }
}