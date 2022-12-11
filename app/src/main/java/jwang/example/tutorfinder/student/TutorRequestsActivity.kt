package jwang.example.tutorfinder.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.Student
import jwang.example.tutorfinder.tutor.StudentRequestsRvAdapter
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class TutorRequestsActivity : AppCompatActivity() {

    val requestedTutors: MutableList<Tutor> = mutableListOf()

    lateinit var adapter: MyTutorsRvAdapter
    lateinit var recyclerView: RecyclerView
    private val database = Firebase.database.reference

    private var currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    var requestedTutorsUids = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_requests)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTutorRequests)
        adapter = MyTutorsRvAdapter(this, requestedTutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        supportActionBar?.title = "Student Portal"

        if (TutorScreenActivity.currentUser != null) {

            database.child("users/${currentUserId}/tutors_requested")
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            //Log.d("test", "Snapshot")

                            for (i in snapshot.children) {
                                if (!requestedTutorsUids.contains(i.key)) {
                                    i.key?.let { requestedTutorsUids.add(it) }
                                }
                            }
                            fetchRequestedTutors()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("myTag", "postComments:onCancelled", error.toException())
                        Toast.makeText(
                            applicationContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    fun fetchRequestedTutors() {

        //Log.d("test", "Hit here")

        for (uid in requestedTutorsUids) {
            database.child("users").orderByKey().equalTo(uid).addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    if(snapshot.exists()){
//                        Log.d("myTag", "snapcount: ${snapshot.childrenCount}")

                    //Get object from snapshot. Here is only one object in the children array.
                    //TODO: Student and Tutor classes should inherit UnknownUser class
                    for (i in snapshot.children){
//                            Log.d("myTag", "uid: ${i.key}")
                        var tutor = i.getValue(Tutor::class.java)
                        tutor?.let { it.id = uid

                            if (!requestedTutors.contains(tutor)){
                                requestedTutors.add(it)

                            }

//                                Log.d("myTag", "st id: ${student.id}")
//                                Log.d("myTag", "st count: ${students.count()}")
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
//                    } else{
//                        Toast.makeText(applicationContext, "Data is not found", Toast.LENGTH_SHORT).show()
//                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}