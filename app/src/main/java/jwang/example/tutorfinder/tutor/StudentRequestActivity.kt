package jwang.example.tutorfinder.tutor

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import jwang.example.tutorfinder.R
import kotlin.math.log

class StudentRequestActivity : AppCompatActivity() {

    //Dummy data for populating UI
    companion object {

        var STUDENT_ID: Int = 0
        var STUDENT_ID_DELETE: Int = 0
        var POSITION: Int = 0
        var POSITION_DELETE = 0

    }

    val requestedStudents: MutableList<Student> = mutableListOf(
//        Student(101, "Sampath", 21, "sampath@email.com"),
//        Student(102, "Jianwei", 22, "jianwei@email.com"),
//        Student(103,"Sunny", 19, "Sunny@email.com"),
//        Student(104,"Pradip", 20, "Pradip@email.com"),
//        Student(105, "Sampath1", 21, "sampath1@email.com"),
//        Student(106, "Jianwei1", 22, "jianwei1@email.com"),
//        Student(107,"Sunny1", 19, "Sunny1@email.com"),
//        Student(108,"Pradip1", 20, "Pradip1@email.com")
    )

    lateinit var adapter: StudentRequestsRvAdapter
    lateinit var recyclerView: RecyclerView
    private val database = Firebase.database.reference

    var deleteNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_request)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStudentRequests)
        adapter = StudentRequestsRvAdapter(this, requestedStudents)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        supportActionBar?.title = "Tutor Portal"

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val rejectedStudent: Student =
                            requestedStudents[viewHolder.adapterPosition]
                        val position = viewHolder.adapterPosition
                        requestedStudents.removeAt(viewHolder.adapterPosition)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)

                        //Log.d("accept", "Reject")

                        // update the tutors requested students node by removing the students rejected
                        val databaseReferenceRemove: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/students_requests/${rejectedStudent.id}")
                        databaseReferenceRemove.removeValue()

                        // update the students requested tutors node by removing the tutor rejected
                        val databaseReferenceRemoveForStudent: DatabaseReference = database.child("users/${rejectedStudent.id}/tutors_requested/${TutorScreenActivity.currentUser?.uid}")
                        databaseReferenceRemoveForStudent.removeValue()

                        Snackbar.make(
                            recyclerView,
                            "${rejectedStudent.name} rejected",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                requestedStudents.add(position, rejectedStudent)
                                adapter.notifyItemInserted(position)

                                // update the tutors requested students node by undoing the students rejected
                                val databaseReferenceUndoRejectedStudent: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/students_requests/${rejectedStudent.id}")
                                val undoRejectedStudent = HashMap<String, Any>()
                                undoRejectedStudent[rejectedStudent.id] = ""
                                databaseReferenceUndoRejectedStudent.updateChildren(undoRejectedStudent)

                                // update the students requested tutors node by undoing the tutors accepted
                                val databaseReferenceUndoRejectedTutor: DatabaseReference = database.child("users/${rejectedStudent.id}/tutors_requested/${TutorScreenActivity.currentUser?.uid}")
                                val undoRejectedTutor = HashMap<String, Any>()
                                undoRejectedTutor[rejectedStudent.id] = ""
                                databaseReferenceUndoRejectedTutor.updateChildren(undoRejectedTutor)

                            }.show()
                    }

                    ItemTouchHelper.RIGHT -> {
                        val acceptedStudent: Student =
                            requestedStudents[viewHolder.adapterPosition]
                        val position = viewHolder.adapterPosition
                        requestedStudents.removeAt(viewHolder.adapterPosition)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)

                        //Log.d("accept", "Accept")

                        // update the tutors current students node
                        val databaseReferenceAccept: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/current_students")
                        val updateStudent = HashMap<String, Any>()
                        updateStudent[acceptedStudent.id] = ""
                        databaseReferenceAccept.updateChildren(updateStudent)

                        // update the students accepted tutors node
                        val databaseReferenceAcceptForStudent: DatabaseReference = database.child("users/${acceptedStudent.id}/tutors_accepted")
                        val updateTutor = HashMap<String, Any>()
                        updateTutor[TutorScreenActivity.currentUser?.uid.toString()] = ""
                        databaseReferenceAcceptForStudent.updateChildren(updateTutor)

                        // update the tutors requested students node by removing the students accepted
                        val databaseReferenceRemove: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/students_requests/${acceptedStudent.id}")
                        databaseReferenceRemove.removeValue()

                        // update the students requested tutors node by removing the tutor accepted
                        val databaseReferenceRemoveTutor: DatabaseReference = database.child("users/${acceptedStudent.id}/tutors_requested/${TutorScreenActivity.currentUser?.uid}")
                        databaseReferenceRemoveTutor.removeValue()

                        Snackbar.make(
                            recyclerView,
                            "${acceptedStudent.name} accepted",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                requestedStudents.add(position, acceptedStudent)
                                adapter.notifyItemInserted(position)

                                // update the tutors requested students node by removing the students accepted
                                val databaseReferenceUndoAcceptedStudent: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/students_requests/${acceptedStudent.id}")
                                val undoAcceptedStudent = HashMap<String, Any>()
                                undoAcceptedStudent[acceptedStudent.id] = ""
                                databaseReferenceUndoAcceptedStudent.updateChildren(undoAcceptedStudent)

                                // update the students requested tutors node by removing the tutors accepted
                                val databaseReferenceUndoAcceptedTutor: DatabaseReference = database.child("users/${acceptedStudent.id}/tutors_requested/${TutorScreenActivity.currentUser?.uid}")
                                val undoAcceptedTutor = HashMap<String, Any>()
                                undoAcceptedStudent[acceptedStudent.id] = ""
                                databaseReferenceUndoAcceptedTutor.updateChildren(undoAcceptedTutor)

                                // update the tutors current students node by undoing the students accepted
                                val databaseReferenceUndoAcceptedStudentRemove: DatabaseReference = database.child("users/${TutorScreenActivity.currentUser?.uid}/current_students/${acceptedStudent.id}")
                                databaseReferenceUndoAcceptedStudentRemove.removeValue()

                                // update the students accepted tutors node by undoing the students accepted
                                val databaseReferenceUndoAcceptedStudentRemoveForStudent: DatabaseReference = database.child("users/${acceptedStudent.id}/tutors_accepted/${TutorScreenActivity.currentUser?.uid}")
                                databaseReferenceUndoAcceptedStudentRemoveForStudent.removeValue()


                            }.show()
                    }
                }
            }
        }).attachToRecyclerView(recyclerView)

        //Read tutor's students requests from firebase

        if (TutorScreenActivity.currentUser != null) {

            database.child("users/${TutorScreenActivity.currentUser?.uid}/students_requests")
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            //Log.d("SnapshotExists", "Snapshot")

                            for (i in snapshot.children) {
                                if (!TutorScreenActivity.requestStudentsUids.contains(i.key)) {
                                    i.key?.let { TutorScreenActivity.requestStudentsUids.add(it) }
                                }
                            }
                            fetchRequestedStudents()
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

    fun fetchRequestedStudents() {

        Log.d("Students", TutorScreenActivity.requestStudentsUids.toString())
        for (uid in TutorScreenActivity.requestStudentsUids) {
            database.child("users").orderByKey().equalTo(uid).addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    if(snapshot.exists()){
//                        Log.d("myTag", "snapcount: ${snapshot.childrenCount}")

                    //Get object from snapshot. Here is only one object in the children array.
                    //TODO: Student and Tutor classes should inherit UnknownUser class
                    for (i in snapshot.children){
//                            Log.d("myTag", "uid: ${i.key}")
                        var student = i.getValue(Student::class.java)
                        student?.let { it.id = uid

                            if (!requestedStudents.contains(student)){
                                requestedStudents.add(it)

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

    private fun loadRequests() {
        adapter.notifyItemRemoved(POSITION_DELETE - (deleteNumber - 1))
        deleteNumber += 1
    }

    override fun onResume() {
        loadRequests()

        super.onResume()
    }
}