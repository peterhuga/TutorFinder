package jwang.example.tutorfinder.student

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.LoginActivity
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class EditStudentProfile : AppCompatActivity() {

    lateinit var studentsNameEditText: TextView
    lateinit var studentsEmailEditText: TextView
    lateinit var studentsPhoneEditText: TextView
    lateinit var studentsAddressEditText: TextView
    lateinit var studentsGradeEditText: TextView
    lateinit var studentsAgeEditText: TextView

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_GET=1

    lateinit var cameraBtn: Button
    lateinit var galleryBtn: Button

    private val database = Firebase.database.reference

    private var currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student_profile)

        supportActionBar?.title = "Student Portal"

        initializeFields()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_bar -> {
                Log.d("Student", "action bar clicked")

                if (studentsNameEditText.text.isEmpty() || studentsEmailEditText.text.isEmpty() || studentsPhoneEditText.text.isEmpty()
                    || studentsAddressEditText.text.isEmpty() || studentsGradeEditText.text.isEmpty() || studentsAgeEditText.text.isEmpty()) {
                    Toast.makeText(this,"Fill the every field!", Toast.LENGTH_SHORT).show()
                } else {
                    onSAveButtonClick()
                }

                return true
            }

            R.id.log_out -> {
                logOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        Firebase.auth.signOut()
        Toast.makeText(this,R.string.logged_out, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun onSAveButtonClick() {

        database.child("users/${currentUserId}/name").setValue(studentsNameEditText.text.toString())
        database.child("users/${currentUserId}/age").setValue(studentsAgeEditText.text.toString())
        database.child("users/${currentUserId}/phone").setValue(studentsPhoneEditText.text.toString())
        database.child("users/${currentUserId}/email").setValue(studentsEmailEditText.text.toString())
        database.child("users/${currentUserId}/address").setValue(studentsAddressEditText.text.toString())
        database.child("users/${currentUserId}/grade").setValue(studentsGradeEditText.text.toString())

        Toast.makeText(this,"Profile updated successfully!", Toast.LENGTH_SHORT).show()

        finish()
    }

    private fun initializeFields() {
        studentsNameEditText = findViewById(R.id.editTextStudentName)
        studentsEmailEditText = findViewById(R.id.editTextStudentEmail)
        studentsPhoneEditText = findViewById(R.id.editTextStudentPhone)
        studentsAddressEditText = findViewById(R.id.editTextStudentAddress)
        studentsGradeEditText = findViewById(R.id.editTextStudentGrade)
        studentsAgeEditText = findViewById(R.id.editTextStudentAge)

        database.child("users/${currentUserId}").addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("name").exists()){

                    //Log.d("IfExists", snapshot.toString())

                    studentsNameEditText.text = snapshot.child("name").value.toString()
                    studentsEmailEditText.text = snapshot.child("email").value.toString()
                    studentsPhoneEditText.text = snapshot.child("phone").value.toString()
                    studentsAddressEditText.text = snapshot.child("address").value.toString()
                    studentsAgeEditText.text = snapshot.child("age").value.toString()
                    studentsGradeEditText.text = snapshot.child("grade").value.toString()
                } else {
                    studentsEmailEditText.text = snapshot.child("email").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun onPictureEvent(view: View) {
        when (view.id) {

            R.id.imageViewStudentProfileImage -> {
                cameraBtn.isVisible = true
                galleryBtn.isVisible=true
            }
            R.id.cameraBtn -> {

                dispatchTakePictureIntent()
                cameraBtn.isVisible = false
                galleryBtn.isVisible=false

            }
            R.id.galleryBtn-> {
                selectImage()
                cameraBtn.isVisible=false
                galleryBtn.isVisible=false
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        Log.d("gallery","successful")
        // if (intent.resolveActivity(packageManager) != null) {
        startActivityForResult(intent, REQUEST_IMAGE_GET)
        // }
    }

}