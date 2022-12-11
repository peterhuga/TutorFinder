package jwang.example.tutorfinder.student

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.Student
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class TutorFilterActivity : AppCompatActivity() {
    lateinit var adapter: FilteredTutorRv
    lateinit var myObj:String
    lateinit var recyclerView: RecyclerView
    //lateinit var gradeSpinner: Spinner
    lateinit var degreeSpinner: Spinner
    lateinit var experienceSpinner: Spinner


    var tutorListFromDatabase: MutableList<Tutor> = mutableListOf()

    private val database = Firebase.database.reference
    val currentUser = FirebaseAuth.getInstance().currentUser

    //Jianwei
    var filteredTutors: MutableList<Tutor> = mutableListOf()
    var finalFilteredTutors: MutableList<Tutor> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_filter)

        supportActionBar?.title = "Student Portal"

        degreeSpinner=findViewById(R.id.academicDegreeSpinner)
        //gradeSpinner=findViewById(R.id.gradeSpinner)
        experienceSpinner=findViewById(R.id.experienceSpinner)

        val degreespinneradapter = ArrayAdapter.createFromResource(
            this,
            R.array.academicDegreeSpinner,
            android.R.layout.simple_spinner_item
        )
        degreespinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        degreeSpinner.adapter = degreespinneradapter
        // degreeSpinner.onItemSelectedListener = this


//        val gradepinneradapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.gradeSpinner,
//            android.R.layout.simple_spinner_item
//        )
//        gradepinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        gradeSpinner.adapter = gradepinneradapter
//        // gradeSpinner.onItemSelectedListener = this


        val experiencepinneradapter = ArrayAdapter.createFromResource(
            this,
            R.array.ExpereienceSpinner,
            android.R.layout.simple_spinner_item
        )
        experiencepinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        experienceSpinner.adapter = experiencepinneradapter

        //gradeSpinner.onItemSelectedListener = this

        recyclerView = findViewById<RecyclerView>(R.id.filturedTutorsRv)

        //Jianwei
        adapter = FilteredTutorRv(this, finalFilteredTutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val requestedTutor: Tutor =
                    finalFilteredTutors[viewHolder.adapterPosition]

                val builder = AlertDialog.Builder(viewHolder.itemView.context)

                builder.setTitle("Are you sure to send a request?")
                builder.setCancelable(false)
                builder.setPositiveButton("Confirm"){
                    //send student id back to parent to delete it
                        dialog, which ->
                    finalFilteredTutors.removeAt(viewHolder.adapterPosition)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)

                    val databaseReferenceUpdateRequestsStudent: DatabaseReference = database.child("users/${currentUser?.uid}/tutors_requested")
                    val updateTutor = HashMap<String, Any>()
                    updateTutor[requestedTutor.id] = ""
                    databaseReferenceUpdateRequestsStudent.updateChildren(updateTutor)

                    val databaseReferenceUpdateRequestsTutor: DatabaseReference = database.child("users/${requestedTutor.id}/students_requests")
                    val updateTutorRequests = HashMap<String, Any>()
                    updateTutorRequests[currentUser?.uid.toString()] = ""
                    databaseReferenceUpdateRequestsTutor.updateChildren(updateTutorRequests)

                    //finish()
                }
                builder.setNegativeButton("Cancel"){
                        dialog, which ->
                    recyclerView.adapter = adapter
                    dialog.cancel()
                }
                builder.show()

            }
        }).attachToRecyclerView(recyclerView)

    }


    fun onFilterClicked(view: View) {

        finalFilteredTutors.clear()

        database.child("users").orderByChild("role").equalTo("tutor")
            .addValueEventListener(object :
            ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for (i in snapshot.children) {

                        var tutor = i.getValue(Tutor::class.java)

                        tutor?.let { it.id = i.key.toString()

                            if (!filteredTutors.contains(tutor)) {
                                filteredTutors.add(it)

                            }
                        }

                    }

                }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        for (tutor in filteredTutors) {
            Log.d("test", experienceSpinner.selectedItem.toString())
            //Log.d("test", gradeSpinner.selectedItem.toString())
            //Log.d("test", degreeSpinner.selectedItem.toString())
            if (tutor.education == degreeSpinner.selectedItem && tutor.experience == experienceSpinner.selectedItem) {
                tutor?.let { it.id = tutor.id

                    if (!finalFilteredTutors.contains(tutor)) {
                        finalFilteredTutors.add(it)

                    }
                }
            }
        }

        recyclerView.adapter?.notifyDataSetChanged()

    }
}