package jwang.example.tutorfinder

import android.content.ContentValues.TAG
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
import jwang.example.tutorfinder.student.StudentDashboard
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class LoginActivity : AppCompatActivity() {
    lateinit var loginBtn: Button
    lateinit var signupBtn: Button
    private lateinit var auth: FirebaseAuth
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var studentSelectBtn: Button
    lateinit var tutorSelectBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn = findViewById(R.id.loginToApp)
        auth = Firebase.auth
        emailEditText=findViewById(R.id.signinusernameedittext)
        passwordEditText=findViewById(R.id.signinpasswordedittext)

    }
    fun onLoginClicked(view: View) {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(this, StudentDashboard::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
//        val email = emailEditText.text.toString()
//        val password = passwordEditText.text.toString()
//        if (email.isNotBlank() || password.isNotBlank()) {
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("signin", "signInWithEmail:success")
                            //finish()
//
//                       }
//                        else{
//                           // Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                            Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
//                        }
//                    }
//        } else {
//            Toast.makeText(
//                baseContext, "Please enter valid email and password",
//                Toast.LENGTH_SHORT
//            ).show()
//        }




    fun onToTutorClicked(view: View) {
        startActivity(Intent(this,TutorScreenActivity::class.java))
    }


    fun signUpNewUser(view: View) {

        startActivity(Intent(this, SignUpActivity::class.java))
    }


}
