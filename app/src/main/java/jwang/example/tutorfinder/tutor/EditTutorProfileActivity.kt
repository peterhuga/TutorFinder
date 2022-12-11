package jwang.example.tutorfinder.tutor

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
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

class EditTutorProfileActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_GET=1
    lateinit var galleryBtn: Button
    companion object{
        lateinit var myIdentity:Student
    }

    lateinit var cameraBtn: Button
    lateinit var locationForPhotos: Uri
    lateinit var myProfileImage: ImageView

    lateinit var tutorsNameTextView: TextView
    lateinit var tutorsEmailTextView: TextView
    lateinit var tutorsPhoneTextView: TextView
    lateinit var tutorsAddressTextView: TextView
    lateinit var tutorsEducationSpinner: Spinner
    lateinit var tutorsAgeTextView: TextView
    lateinit var tutorsExperienceSpinner: Spinner
    lateinit var tutorsGradesTextView: TextView

    private val database = Firebase.database.reference

    private var currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittutor_profile)

        supportActionBar?.title = "Tutor Portal"

        initializeFields()

        val educationSpinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.academicDegreeSpinner,
            android.R.layout.simple_spinner_item
        )
        educationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tutorsEducationSpinner.adapter = educationSpinnerAdapter


        val experienceSpinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.ExpereienceSpinner,
            android.R.layout.simple_spinner_item
        )
        experienceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tutorsExperienceSpinner.adapter = experienceSpinnerAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_bar -> {
                Log.d("Student", "action bar clicked")

                if (tutorsNameTextView.text.isEmpty() || tutorsAgeTextView.text.isEmpty() || tutorsEmailTextView.text.isEmpty() || tutorsPhoneTextView.text.isEmpty()
                    || tutorsAddressTextView.text.isEmpty() || tutorsEducationSpinner.selectedItem.equals("--No Selection--") || tutorsExperienceSpinner.selectedItem.equals("--No Selection--")
                    || tutorsGradesTextView.text.isEmpty()) {
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
        Toast.makeText(this,R.string.logged_out,Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,LoginActivity::class.java))

        //finishActivity(1)

        finish()
    }

    private fun onSAveButtonClick() {

        database.child("users/${currentUserId}/name").setValue(tutorsNameTextView.text.toString())
        database.child("users/${currentUserId}/age").setValue(tutorsAgeTextView.text.toString().toInt())
        database.child("users/${currentUserId}/phone").setValue(tutorsPhoneTextView.text.toString())
        database.child("users/${currentUserId}/email").setValue(tutorsEmailTextView.text.toString())
        database.child("users/${currentUserId}/address").setValue(tutorsAddressTextView.text.toString())
        database.child("users/${currentUserId}/education").setValue(tutorsEducationSpinner.selectedItem.toString())
        database.child("users/${currentUserId}/experience").setValue(tutorsExperienceSpinner.selectedItem.toString())
        database.child("users/${currentUserId}/grades").setValue(tutorsGradesTextView.text.toString())

//        database.child("users/${TutorScreenActivity.currentUser?.uid}/firstName").setValue(tutorsNameTextView.text.toString())
//        database.child("users/${TutorScreenActivity.currentUser?.uid}/age").setValue(tutorsAgeTextView.text.toString())
//        database.child("users/${TutorScreenActivity.currentUser?.uid}/phone").setValue(tutorsPhoneTextView.text.toString())
//        database.child("users/${TutorScreenActivity.currentUser?.uid}/email").setValue(tutorsEmailTextView.text.toString())
//        database.child("users/${TutorScreenActivity.currentUser?.uid}/address").setValue(tutorsAddressTextView.text.toString())
//        database.child("users/${TutorScreenActivity.currentUser?.uid}/education").setValue(tutorsEducationTextView.text.toString())
//        database.child("users/${TutorScreenActivity.currentUser?.uid}/experience").setValue(tutorsExperienceTextView.text.toString())
//        database.child("users/${TutorScreenActivity.currentUser?.uid}/grades").setValue(tutorsGradesTextView.text.toString())

//        TutorScreenActivity.nameTutor = tutorsNameTextView.text.toString()
//        TutorScreenActivity.emailTutor = tutorsEmailTextView.text.toString()
//        TutorScreenActivity.phoneTutor = tutorsPhoneTextView.text.toString()
//        TutorScreenActivity.addressTutor = tutorsAddressTextView.text.toString()
//        TutorScreenActivity.educationTutor = tutorsEducationTextView.text.toString()
//        TutorScreenActivity.ageTutor = tutorsAgeTextView.text.toString()
//        TutorScreenActivity.experienceTutor = tutorsExperienceTextView.text.toString()

        Toast.makeText(this,"Profile updated successfully!", Toast.LENGTH_SHORT).show()

        finish()
    }

    private fun initializeFields() {
        tutorsNameTextView = findViewById(R.id.editTextTutorName)
        tutorsEmailTextView = findViewById(R.id.editTextTutorEmail)
        tutorsPhoneTextView = findViewById(R.id.editTextTutorPhone)
        tutorsAddressTextView = findViewById(R.id.editTextTutorAddress)
        tutorsEducationSpinner = findViewById(R.id.spinnerTutorEducation)
        tutorsAgeTextView = findViewById(R.id.editTextTutorAge)
        tutorsExperienceSpinner = findViewById(R.id.spinnerTutorExperience)
        tutorsGradesTextView = findViewById(R.id.editTextTutorGrades)


//        tutorsNameTextView.text = TutorScreenActivity.nameTutor
//        tutorsEmailTextView.text = TutorScreenActivity.emailTutor
//        tutorsPhoneTextView.text = TutorScreenActivity.phoneTutor
//        tutorsAddressTextView.text = TutorScreenActivity.addressTutor
//        tutorsEducationTextView.text = TutorScreenActivity.educationTutor
//        tutorsAgeTextView.text = TutorScreenActivity.ageTutor
//        tutorsExperienceTextView.text = TutorScreenActivity.experienceTutor

        database.child("users/${currentUserId}").addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("name").exists()){

                    //Log.d("IfExists", snapshot.toString())
                    var educationFromDatabase: String = snapshot.child("education").value.toString()
                    var experienceFromDatabase: String = snapshot.child("experience").value.toString()

                    var selectionEducation: Int = 0
                    var selectionExperience: Int = 0

                    when (educationFromDatabase) {
                        "Masters" -> selectionEducation = 1
                        "Bachelors" -> selectionEducation = 2
                        "PhD" -> selectionEducation = 3
                        "Diploma" -> selectionEducation = 4
                        "Post Graduate" -> selectionEducation = 5
                    }

                    when (experienceFromDatabase) {
                        "1 to 5" -> selectionExperience = 1
                        "6 to 10" -> selectionExperience = 2
                        "More than 10" -> selectionExperience = 3
                    }


                    tutorsNameTextView.text = snapshot.child("name").value.toString()
                    tutorsEmailTextView.text = snapshot.child("email").value.toString()
                    tutorsPhoneTextView.text = snapshot.child("phone").value.toString()
                    tutorsAddressTextView.text = snapshot.child("address").value.toString()
                    tutorsEducationSpinner.setSelection(selectionEducation)
                    tutorsAgeTextView.text = snapshot.child("age").value.toString()
                    tutorsExperienceSpinner.setSelection(selectionExperience)
                    tutorsGradesTextView.text = snapshot.child("grades").value.toString()
                } else {
                    tutorsEmailTextView.text = snapshot.child("email").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
    fun onPictureEvent(view: View) {
        when (view.id) {

            R.id.imageViewStudentProfileImage -> {
                cameraBtn.isVisible = true
                // galleryBtn.isVisible=true
            }
            R.id.cameraBtn -> {

                dispatchTakePictureIntent()
                cameraBtn.isVisible = false
                // galleryBtn.isVisible=false

            }
            R.id.galleryBtn-> {
                selectImage()
                cameraBtn.isVisible=false
                // galleryBtn.isVisible=false
            }
        }
    }

    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        Log.d("gallery","successful")
        // if (intent.resolveActivity(packageManager) != null) {
        startActivityForResult(intent, REQUEST_IMAGE_GET)
        // }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
            myProfileImage.setImageBitmap(thumbnail)
            //locationForPhotos   = data?.data!!
            //myProfileImage.setImageURI(locationForPhotos)
        }
    }
}