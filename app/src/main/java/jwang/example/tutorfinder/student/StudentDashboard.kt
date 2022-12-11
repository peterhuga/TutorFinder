package jwang.example.tutorfinder.student

import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.LoginActivity
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.EditTutorProfileActivity
import jwang.example.tutorfinder.tutor.StudentRequestActivity
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class StudentDashboard : AppCompatActivity() {
    lateinit var adapter: MyTutorsRvAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var filterTutorNavigateBtn:Button
    lateinit var requestCountTextView: TextView

    var requestCount = 0

    var acceptedTutorsUids = mutableListOf<String>()
    var requestTutorsUids = mutableListOf<String>()

    private val database = Firebase.database.reference
    private val currentUser = FirebaseAuth.getInstance().currentUser

    companion object {
        const val TUTOR_ID = "tutor id"
        var tutors: MutableList<Tutor> = mutableListOf()
        var firstTimeOpenedStudent: Boolean = true

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        supportActionBar?.title = "Student Portal"

        filterTutorNavigateBtn = findViewById(R.id.filterTutorActivityBtn)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyTutor)
        adapter = MyTutorsRvAdapter(this, tutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        //createNotificationChannel()

        if (currentUser != null) {
            database.child("users/${currentUser.uid}/tutors_accepted").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    tutors.clear()
                    Log.d("myTag", "final: ${tutors.size}")

                    for (i in snapshot.children) {
                        if (!acceptedTutorsUids.contains(i.key)) {
                            i.key?.let { acceptedTutorsUids.add(it) }
                        }
                    }

                    fetchAcceptedTutors()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("myTag", "postComments:onCancelled", error.toException())
                    Toast.makeText(
                        applicationContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            database.child("users/${currentUser.uid}/tutors_requested").addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    requestTutorsUids.clear()
                    for (i in snapshot.children){
                        if (!requestTutorsUids.contains(i.key)) {
                            i.key?.let { requestTutorsUids.add(it) }
                        }
                    }

                    requestCount = requestTutorsUids.size

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("myTag", "postComments:onCancelled", error.toException())
                    Toast.makeText(applicationContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun onFilterBtnPressed(view: View) {
        when(view.id){
            R.id.filterTutorActivityBtn ->{
                startActivity(Intent(this, TutorFilterActivity::class.java))

            }
        }
    }

    fun fetchAcceptedTutors() {


        for (uid in acceptedTutorsUids) {

            database.child("users").orderByKey().equalTo(uid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for (i in snapshot.children) {

                        var tutor = i.getValue(Tutor::class.java)

                        tutor?.let { it.id = uid

                            if (!tutors.contains(tutor)) {
                                tutors.add(it)
                            }
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                    Log.d("myTag", "notify2: ${tutors.size}")

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_tutor_screen, menu)
        /*Add badge number to bell icon to show the number of requests
        *custom_action_item_layout
        *add layout to menu layout
        * implement the textview (badge) in this activity, two var declared at the top
         */
        val menuItem = menu?.findItem(R.id.action_settings)
        val actionView = menuItem?.actionView
        if (actionView != null) {
            requestCountTextView = actionView.findViewById(R.id.cart_badge)
            actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
        }

        requestCountTextView.text = requestCount.toString()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bar -> {startActivity(Intent(this, EditStudentProfile::class.
            java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                return true}
            // Need to add another activity
            R.id.action_settings -> {startActivity(Intent(this, TutorRequestsActivity::
            class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                return true}
            R.id.action_dark_mode -> {
                if (TutorScreenActivity.isDarkModeEnabled) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    TutorScreenActivity.isDarkModeEnabled = false
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    TutorScreenActivity.isDarkModeEnabled = true
                }

            }
            R.id.action_log_out -> {logOut()}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        Firebase.auth.signOut()
        Toast.makeText(this,R.string.logged_out,Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))

        finish()
    }

//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channnel_desc)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel("0", name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

    // If profile not updated, will be directed to the profile activity
    override fun onStart() {
        database.child("users/${currentUser?.uid}/name").addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()){

                    //Log.d("IfExists", snapshot.toString())
                    startActivity(
                        Intent(
                            applicationContext,
                            EditStudentProfile::class.java
                        )
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        super.onStart()
    }
}