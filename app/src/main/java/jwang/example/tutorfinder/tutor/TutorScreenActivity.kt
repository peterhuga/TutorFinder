package jwang.example.tutorfinder.tutor

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.ClipData.Item
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
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
import jwang.example.tutorfinder.R.id
import jwang.example.tutorfinder.R.layout


class TutorScreenActivity : AppCompatActivity() {

    var deletedId = 0
    lateinit var adapter: MyStudentsRvAdapter
    lateinit var recyclerView:RecyclerView
    lateinit var tvStudentAmount: TextView
    lateinit var requestCountTextView: TextView
    var requestCount = 0

    //Dummy data for populating UI
    companion object {
        const val STUDENT_ID = "student id"

//        var nameTutor: String = ""
//        var ageTutor: String = ""
//        var addressTutor: String = ""
//        var phoneTutor: String = ""
//        var educationTutor: String = ""
//        var emailTutor: String = ""
//        var experienceTutor: String = ""
        private val database = Firebase.database.reference
        val currentUser = FirebaseAuth.getInstance().currentUser

        var acceptedStudentsUids = mutableListOf<String>()
        var requestStudentsUids = mutableListOf<String>()

        var isDarkModeEnabled: Boolean = false

        var students: MutableList<Student> = mutableListOf(
//            Student(101, "Sampath", 21, "sampath@email.com"),
//            Student(102, "Jianwei", 22, "jianwei@email.com"),
//            Student(103,"Sunny", 19, "Sunny@email.com"),
//            Student(104, "Sampath", 23, "sampath@email.com"),
//            Student(105, "Jianwei", 24, "jianwei@email.com"),
//            Student(106,"Sunny", 25, "Sunny@email.com")
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_tutor_screen)

        recyclerView = findViewById<RecyclerView>(id.recyclerViewMyStudents)
        tvStudentAmount = findViewById(R.id.textViewStudentAmount)
        adapter = MyStudentsRvAdapter(this, students)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


//        adapter.onItemClick = {
//            val intent = Intent(this, MyStudentProfileActivity::class.java)
//            intent.putExtra(MyStudentsRvAdapter.STUDENT_ID, it.id)
//            startActivity(intent)
//        }

        supportActionBar?.title = "Tutor Portal"
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedStudent: Student =
                    students[viewHolder.adapterPosition]

                val builder = AlertDialog.Builder(viewHolder.itemView.context)

                builder.setTitle("Are you sure to delete this student?")
                builder.setCancelable(false)
                builder.setPositiveButton("Confirm"){
                    //send student id back to parent to delete it
                        dialog, which ->
                    students.removeAt(viewHolder.adapterPosition)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    Log.d("myTag", "notify1: ${students.size}")
                    acceptedStudentsUids.clear()

                    tvStudentAmount.text = "You have ${students.size} students"
                    database.child("users/${currentUser?.uid}/current_students").child(deletedStudent.id).removeValue()
                    database.child("users/${deletedStudent.id}/tutors_accepted/${currentUser?.uid}").removeValue()
                }
                builder.setNegativeButton("Cancel"){
                        dialog, which ->
                    recyclerView.adapter = adapter
                    dialog.cancel()
                }
                builder.show()




            }
        }).attachToRecyclerView(recyclerView)

        //Read tutor's data from firebase

        if ( currentUser != null) {
            database.child("users/${currentUser.uid}/current_students").addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    students.clear()
                    Log.d("myTag", "final: ${students.size}")

                    for (i in snapshot.children){
                        if (!acceptedStudentsUids.contains(i.key)) {
                            i.key?.let { acceptedStudentsUids.add(it) }
                        }
                    }
                    fetchAcceptedStudents()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("myTag", "postComments:onCancelled", error.toException())
                    Toast.makeText(applicationContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show()
                }
            })

            database.child("users/${currentUser.uid}/students_requests").addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    requestStudentsUids.clear()
                    for (i in snapshot.children){
                        if (!requestStudentsUids.contains(i.key)) {
                            i.key?.let { requestStudentsUids.add(it) }
                        }
                    }

                    requestCount = requestStudentsUids.size

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("myTag", "postComments:onCancelled", error.toException())
                    Toast.makeText(applicationContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            if(requestCode==1){
                if (data != null) {
                    val deleteId = data.getStringExtra(STUDENT_ID)

                    //val position = data.getIntExtra("position", -1)
                    Log.d("Student", "DltID, $deleteId")
                    val students2 = students.filter { student -> student.id != deleteId } as MutableList<Student>
                    students.clear()
                    students.addAll(students2)
                    Log.d("Student", "size: ${students.size}")
                    adapter.notifyDataSetChanged()
                    tvStudentAmount.text = "You have ${students.size} student(s)"
                    acceptedStudentsUids.clear()
                    if (deleteId != null) {
                        database.child("users/${currentUser?.uid}/current_students").child(deleteId).removeValue()
                        database.child("users/${deleteId}/tutors_accepted/${currentUser?.uid}").removeValue()
                    }


                }
            }
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
            id.action_bar -> {startActivity(Intent(this, EditTutorProfileActivity::class.
            java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                return true}
            id.action_settings -> {startActivity(Intent(this, StudentRequestActivity::
            class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                return true}
            id.action_dark_mode -> {
                if (isDarkModeEnabled) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    isDarkModeEnabled = false
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    isDarkModeEnabled = true
                }

            }
            id.action_log_out -> {logOut()}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        Log.d("test", currentUser.toString())
        Firebase.auth.signOut()
        //currentUser = null
        Toast.makeText(this,R.string.logged_out,Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,LoginActivity::class.java))

        Log.d("test", currentUser.toString())

        finish()
    }

    fun fetchAcceptedStudents() {


        for (uid in acceptedStudentsUids) {

            database.child("users").orderByKey().equalTo(uid).addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children){
                        var student = i.getValue(Student::class.java)
                        student?.let { it.id = uid

                            if (!students.contains(student)){
                                students.add(it)

                            }
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                    Log.d("myTag", "notify2: ${students.size}")
                    tvStudentAmount.text = "You have ${students.size} student(s)"

                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }


    }


    // If profile not updated, will be directed to the profile activity
    override fun onStart() {
        database.child("users/${currentUser?.uid}/name").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()){

                    //Log.d("IfExists", snapshot.toString())
                    startActivity(
                        Intent(
                            applicationContext,
                            EditTutorProfileActivity::class.java
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