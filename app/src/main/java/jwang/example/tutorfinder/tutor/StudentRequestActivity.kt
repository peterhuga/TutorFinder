package jwang.example.tutorfinder.tutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import jwang.example.tutorfinder.R

class StudentRequestActivity : AppCompatActivity() {

    //Dummy data for populating UI
    companion object {
        val requestedStudents: ArrayList<Student> = arrayListOf(
            Student(101, "Sampath", 21, "sampath@email.com"),
            Student(102, "Jianwei", 22, "jianwei@email.com"),
            Student(103,"Sunny", 19, "Sunny@email.com")
        )


        var STUDENT_ID: Int = 0

    }
    lateinit var adapter: StudentRequestsRvAdapter
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_request)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStudentRequests)
        adapter = StudentRequestsRvAdapter( this,requestedStudents)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

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

                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        val rejectedStudent: Student =
                            requestedStudents[viewHolder.adapterPosition]
                        val position = viewHolder.adapterPosition
                        requestedStudents.removeAt(viewHolder.adapterPosition)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                        Snackbar.make(recyclerView, "${rejectedStudent.name} rejected" , Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                requestedStudents.add(position, rejectedStudent)
                                adapter.notifyItemInserted(position)
                            }.show()
                    }

                    ItemTouchHelper.RIGHT -> {
                        val acceptedStudent: Student =
                            requestedStudents[viewHolder.adapterPosition]
                        val position = viewHolder.adapterPosition
                        requestedStudents.removeAt(viewHolder.adapterPosition)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                        Snackbar.make(recyclerView, "${acceptedStudent.name} accepted" , Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                requestedStudents.add(position, acceptedStudent)
                                adapter.notifyItemInserted(position)
                            }.show()
                    }
                }
            }
        }).attachToRecyclerView(recyclerView)
    }
}