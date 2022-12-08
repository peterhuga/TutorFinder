package jwang.example.tutorfinder

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var tvForgetPassword: TextView
    private val database = Firebase.database.reference
    companion object{
 lateinit var themeBtn:Switch
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn = findViewById(R.id.buttonLogin)
        signupText = findViewById(R.id.textViewHere)
        emailEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        tvForgetPassword = findViewById(R.id.textViewForgetPassword)

        themeBtn = findViewById<Switch>(R.id.switch1)

        // set the switch to listen on checked change
        themeBtn.setOnCheckedChangeListener { _, isChecked ->

            // if the button is checked, i.e., towards the right or enabled
            // enable dark mode, change the text to disable dark mode
            // else keep the switch text to enable dark mode
            if (themeBtn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeBtn.text = "Disable dark mode"
                themeBtn.isChecked=true

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeBtn.text = "Enable dark mode"
                themeBtn.isChecked=false

            }
        }

        signupText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {

            val editTextDialog = EditText(this)
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Enter your email and click SUBMIT")
            builder.setView(editTextDialog)
            builder.setCancelable(false)
            builder.setPositiveButton("SUBMIT") { dialog, which ->
                run {
                    Firebase.auth
                        .sendPasswordResetEmail(editTextDialog.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("reset", "Email sent")
                            }
                        }
                    Toast.makeText(this, "Password reset link sent", Toast.LENGTH_SHORT).show()
                }
            }
                builder.setNegativeButton("CANCEL") { dialog, which ->
                    dialog.cancel()
                }
                builder.show()
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
            TextUtils.isEmpty(emailEditText.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(passwordEditText.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //Decide role

                val user: String = emailEditText.text.toString().trim { it <= ' ' }
                val pass: String = passwordEditText.text.toString().trim { it <= ' ' }
                // signs in current user
                FirebaseAuth.getInstance().signInWithEmailAndPassword(user, pass)
                    // create a new user and password for registration
                    //Firebase.auth.createUserWithEmailAndPassword(user,pass)
                    .addOnCompleteListener { task ->
                        Log.d("mytag", task.isSuccessful.toString())
                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            checkRole(firebaseUser)
                            finish()
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }
}
