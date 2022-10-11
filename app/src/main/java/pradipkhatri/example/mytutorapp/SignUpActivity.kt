package pradipkhatri.example.mytutorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var emailEditText: EditText
    lateinit var passwordEditText : EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth

        emailEditText=findViewById(R.id.signinusernameedittext)
        passwordEditText=findViewById(R.id.signinpasswordedittext)
    }

    fun signupNewUserBtn(view: View) {



    }

}