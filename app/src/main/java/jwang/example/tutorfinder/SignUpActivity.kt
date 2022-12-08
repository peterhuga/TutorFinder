package jwang.example.tutorfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.student.Tutor
import jwang.example.tutorfinder.tutor.Student

class SignUpActivity : AppCompatActivity() {

    lateinit var emailEditText: EditText
    lateinit var passwordEditText : EditText
    lateinit var checkStudent: CheckBox
    lateinit var checkTutor: CheckBox
    lateinit var passwordConfirm: EditText

    lateinit var signUpButton: Button
    private lateinit var database: DatabaseReference
    var role = "student"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        signUpButton = findViewById(R.id.newUserSignupBtn)
        emailEditText=findViewById(R.id.signinusernameedittext)
        passwordEditText=findViewById(R.id.signinpasswordedittext)
        checkStudent = findViewById(R.id.checkBoxStudent)
        checkTutor = findViewById(R.id.checkBoxTutor)
        passwordConfirm = findViewById(R.id.editTextConfirm)
        database = Firebase.database.reference

        checkTutor.setOnCheckedChangeListener{
                _, isChecked -> checkStudent.isEnabled = !isChecked
        }

        checkStudent.setOnCheckedChangeListener{
                _, isChecked -> checkTutor.isEnabled = !isChecked
        }


        signUpButton.setOnClickListener {



            //Log.d("mytag", "key, $key")
            when {
                TextUtils.isEmpty(emailEditText.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(passwordEditText.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show()
                }
                passwordConfirm.text.toString() != passwordEditText.text.toString() -> {
                    Toast.makeText(this,"Passwords are not same", Toast.LENGTH_LONG).show()
                }
                !checkStudent.isChecked && !checkTutor.isChecked -> {
                    Toast.makeText(this, "Please select a role", Toast.LENGTH_LONG).show()
                }

                else -> {
                    //Decide role
                    when(true){
                        checkStudent.isChecked -> role = "student"
                        checkTutor.isChecked -> role = "tutor"
                        else -> (print("No role"))
                    }
                    val user:String = emailEditText.text.toString().trim {it <= ' '}
                    val pass:String = passwordEditText.text.toString().trim {it <= ' '}
                    Log.d("mytag", user + pass)
                    // signs in current user
//                    FirebaseAuth.getInstance().signInWithEmailAndPassword(user,pass)
                    // create a new user and password for registration
                    Firebase.auth.createUserWithEmailAndPassword(user,pass)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> {
                                    task ->
                                Log.d("mytag", task.isSuccessful.toString())
                                if(task.isSuccessful) {
                                    
                                    //fetch needed info from the newly created user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    //val firebaseUser: FirebaseUser = Firebase.auth.currentUser!!
                                    val uId = firebaseUser.uid
                                    val email = firebaseUser.email
                                    Toast.makeText(this,R.string.welcome_user, Toast.LENGTH_SHORT).show()

                                    //Based on the role variable value, user's node is created in student or tutor node
                                    val ref = database.child("users")

                                    //TODO: why does when statement not work?
//                                    var newUser = when(role){
//                                                    "student" -> {Student(email!!, role)
//                                                    Log.d("myTag", "user")}
//                                                    "tutor" -> Tutor(email!!, role)
//                                                    else -> Student(email!!, role)
//                                                }
                                    var newUser = if (role == "student")
                                        SignInStudent(email!!, role)
                                        else
                                        SignInTutor(email!!, role)
                                    ref.child(uId).setValue(newUser)
                                    //finish()
                                    onBackPressed()
                                }
                                else {
                                    Toast.makeText(this,R.string.problem, Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                }
            }
        }
    }
}