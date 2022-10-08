package pradipkhatri.example.mytutorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    lateinit var loginBtn:Button
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