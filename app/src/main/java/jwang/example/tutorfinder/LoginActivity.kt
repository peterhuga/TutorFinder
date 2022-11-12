package jwang.example.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.student.StudentDashboard
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class LoginActivity : AppCompatActivity() {
    lateinit var loginBtn: Button
    lateinit var signupText: TextView
    private lateinit var auth: FirebaseAuth
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    private val database = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn = findViewById(R.id.buttonLogin)
        signupText = findViewById(R.id.textViewHere)
        emailEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)

        signupText.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        checkRole(currentUser)
//    }

    fun checkRole(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            database.child("users").orderByKey().equalTo(currentUser.uid)
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {

                            //Get object from snapshot. Here is only one object in the children array.
                            var unknownUser: UnknownUser? = null
                            for (i in snapshot.children) {
                                unknownUser = i.getValue(UnknownUser::class.java)
                            }
                            when (unknownUser?.role) {
                                "student" -> startActivity(
                                    Intent(
                                        applicationContext,
                                        StudentDashboard::class.java
                                    )
                                )
                                "tutor" -> startActivity(
                                    Intent(
                                        applicationContext,
                                        TutorScreenActivity::class.java
                                    )
                                )
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Data is not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }


    fun onLoginClicked(view: View) {

        when {
            TextUtils.isEmpty(emailEditText.text.toString().trim {it <= ' '}) -> {
                Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(passwordEditText.text.toString().trim {it <= ' '}) -> {
                Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //Decide role

                val user:String = emailEditText.text.toString().trim {it <= ' '}
                val pass:String = passwordEditText.text.toString().trim {it <= ' '}
                // signs in current user
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(user,pass)
                // create a new user and password for registration
                //Firebase.auth.createUserWithEmailAndPassword(user,pass)
                    .addOnCompleteListener { task ->
                        Log.d("mytag", task.isSuccessful.toString())
                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            checkRole(firebaseUser)
                            finish()
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    fun onToTutorClicked(view: View) {
        startActivity(Intent(this,TutorScreenActivity::class.java))
    }



}
