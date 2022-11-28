package jwang.example.tutorfinder.tutor

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.R.*

class TutorScreenActivity : AppCompatActivity() {

    var deletedId = 0
    lateinit var adapter: MyStudentsRvAdapter
    lateinit var recyclerView:RecyclerView
    lateinit var tvStudentAmount: TextView

    //Dummy data for populating UI
    companion object {
        const val STUDENT_ID = "student id"

        var nameTutor: String = ""
        var ageTutor: String = ""
        var addressTutor: String = ""
        var phoneTutor: String = ""
        var educationTutor: String = ""
        var emailTutor: String = ""
        var experienceTutor: String = ""

        var students: MutableList<Student> = mutableListOf(
            Student(101, "Sampath", 21, "sampath@email.com"),
            Student(102, "Jianwei", 22, "jianwei@email.com"),
            Student(103,"Sunny", 19, "Sunny@email.com"),
            Student(104, "Sampath", 23, "sampath@email.com"),
            Student(105, "Jianwei", 24, "jianwei@email.com"),
            Student(106,"Sunny", 25, "Sunny@email.com")
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_tutor_screen)
        recyclerView = findViewById<RecyclerView>(id.recyclerViewMyStudents)
        adapter = MyStudentsRvAdapter(this, students)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        tvStudentAmount = findViewById(R.id.textViewStudentAmount)
        tvStudentAmount.text = "You have ${students.size} students"
//        adapter.onItemClick = {
//            val intent = Intent(this, MyStudentProfileActivity::class.java)
//            intent.putExtra(MyStudentsRvAdapter.STUDENT_ID, it.id)
//            startActivity(intent)
//        }

        supportActionBar?.title = "Tutor Portal"
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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
                val position = viewHolder.adapterPosition
                students.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                tvStudentAmount.text = "You have ${students.size} students"
                Snackbar.make(recyclerView, "${deletedStudent.name} deleted" , Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        students.add(position, deletedStudent)
                        adapter.notifyItemInserted(position)
                        tvStudentAmount.text = "You have ${students.size} students"
                    }.show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            if(requestCode==1){
                if (data != null) {
                    val deleteId = data.getIntExtra(STUDENT_ID,0)
                    val position = data.getIntExtra("position", -1)
                    Log.d("Student", "DltID, $deleteId")
                    val students2 = students.filter { student -> student.id != deleteId } as MutableList<Student>
                    students.clear()
                    students.addAll(students2)
                    Log.d("Student", "size: ${students.size}")
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_tutor_screen, menu)
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
        }
        return super.onOptionsItemSelected(item)
    }
}