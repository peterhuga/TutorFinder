package jwang.example.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
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

//    companion object MySelectBtn {
//
//        var stuSelect: Boolean = false
//        var tuSelect: Boolean = false
//
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn = findViewById(R.id.loginToApp)
    }
//        studentSelectBtn = findViewById(R.id.studentSelectBtn)
//        tutorSelectBtn = findViewById(R.id.tutorSelectBtn)
//        // Initialize Firebase Auth
//        auth = Firebase.auth
//        signupBtn = findViewById(R.id.signupbtn)
//        emailEditText = findViewById(R.id.signinusernameedittext)
//        passwordEditText = findViewById(R.id.signinpasswordedittext)


    //}
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            startActivity(Intent(this, StudentActivity::class.java))
//        }
//    }


    fun onLoginClicked(view: View) {



//        val email = emailEditText.text.toString()
//        val password = passwordEditText.text.toString()
//        if (email.isNotEmpty() || password.isNotEmpty()) {
//            if (studentSelectBtn.isPressed) {
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("signin", "signInWithEmail:success")
                            startActivity(Intent(this, StudentDashboard::class.java))
//finish()

                     //   }
                   // }

//
//            } else if (tuSelect == true) {
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("signin", "signInWithEmail:success")
//
//                            startActivity(Intent(this, TutorScreenActivity::class.java))
//
//
//                        }
//                    }
//            }
//        } else {
//            Toast.makeText(
//                baseContext, "Please enter valid email and password",
//                Toast.LENGTH_SHORT
//            ).show()
//        }


    }

    fun onToTutorClicked(view: View) {
        startActivity(Intent(this,TutorScreenActivity::class.java))
    }


//    fun signUpNewUser(view: View) {
//
//        startActivity(Intent(this, SignUpActivity::class.java))
//    }

    fun onToTutorClicked(view: View) {
        startActivity(Intent(this,TutorScreenActivity::class.java))
    }
}
