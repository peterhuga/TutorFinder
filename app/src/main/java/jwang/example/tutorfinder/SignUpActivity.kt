package jwang.example.tutorfinder

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

class SignUpActivity : AppCompatActivity() {

    lateinit var emailEditText: EditText
    lateinit var passwordEditText : EditText
    private lateinit var auth: FirebaseAuth
    lateinit var MytutorBtn:Button
    lateinit var MyStudentBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        MytutorBtn=findViewById(R.id.TutorBtn)
        MyStudentBtn=findViewById(R.id.studentBtn)
        MytutorBtn.isSelected=false
        MyStudentBtn.isSelected=false
        auth = Firebase.auth

        emailEditText=findViewById(R.id.signinusernameedittext)
        passwordEditText=findViewById(R.id.signinpasswordedittext)
    }

    fun signupNewUserBtn(view: View) {


        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if(email.isNotBlank() || password.isNotBlank()) {

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signup", "createUserWithEmail:success")
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signupfail", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }





        }




        else {
            Toast.makeText(baseContext, "Email and password cannot be blank",
                Toast.LENGTH_SHORT).show()
        }

    }

}