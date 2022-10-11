package pradipkhatri.example.mytutorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var loginBtn:Button
    lateinit var signupBtn:Button
    private lateinit var auth: FirebaseAuth
    lateinit var emailEditText: EditText
    lateinit var passwordEditText : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn = findViewById(R.id.loginToApp)
        // Initialize Firebase Auth
        auth = Firebase.auth
        signupBtn=findViewById(R.id.signupbtn)
        emailEditText=findViewById(R.id.signinusernameedittext)
        passwordEditText=findViewById(R.id.signinpasswordedittext)

    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            reload();
//        }
//    }
//

//    private fun reload() {
//        startActivity(Intent(this,StudentActivity::class.java))
//
//    }

    fun onLoginClicked(view: View) {

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("signin", "signInWithEmail:success")
                    val user = auth.currentUser
                     startActivity(Intent(this,StudentActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("signinfail", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    fun signUpNewUser(view: View) {

    startActivity(Intent(this,SignUpActivity::class.java))
    }
}