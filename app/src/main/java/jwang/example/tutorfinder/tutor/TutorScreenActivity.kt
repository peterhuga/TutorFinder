package jwang.example.tutorfinder.tutor

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.R.*
import jwang.example.tutorfinder.tutor.StudentRequestActivity.Companion.requestedStudents
import java.lang.System.exit


class TutorScreenActivity : AppCompatActivity() {

    var deletedId = 0
    lateinit var adapter: MyStudentsRvAdapter
    lateinit var recyclerView:RecyclerView

    //Dummy data for populating UI
    companion object {
            const val STUDENT_ID = "student id"
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
//        adapter.onItemClick = {
//            val intent = Intent(this, MyStudentProfileActivity::class.java)
//            intent.putExtra(MyStudentsRvAdapter.STUDENT_ID, it.id)
//            startActivity(intent)
//        }

        supportActionBar?.title = "Tutor Portal"

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

    override fun onBackPressed() {
        super.onBackPressed()

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.action_bar -> {startActivity(Intent(this, EditTutorProfileActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                return true}
            id.action_settings -> {startActivity(Intent(this, StudentRequestActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                return true}
        }
        return super.onOptionsItemSelected(item)
    }
}